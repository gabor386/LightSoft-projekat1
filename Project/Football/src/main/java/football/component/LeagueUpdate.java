package football.component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.controller.Param;
import football.repository.CountryRepo;
import football.repository.LeagueRepo;
import football.repository.RoundRepo;
import football.repository.SeasonRepo;
import football.repository.TeamRepo;
import football.repository.TeamplayerRepo;
import football.repository.TopscorerRepo;
import model.Country;
import model.League;
import model.Round;
import model.Season;
import model.Team;
import model.TeamPlayer;
import model.TopScorer; 

@Component
public class LeagueUpdate {

	Param param = new Param();

	@Autowired
	SeasonRepo sr;
	
	@Autowired
	LeagueRepo lr;
	
	@Autowired
	CountryRepo cr;
	
	@Autowired
	RoundRepo rr;
	
	@Autowired
	TopscorerRepo topScorrerRepo;
	
	@Autowired
	TeamRepo teamRepo; 
	
	@Autowired
	TeamplayerRepo teamplayerRepo; 
	
	
	
	//@Scheduled(cron = "0 0/5 * * * ?")
	public void update () {
		int y = getYear();
		Season s = getSeason(y);
		List<League> ls = apiLeague(s);
		System.out.println(ls.toString());
		System.out.println("Zavrsio sam update");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();  
		   System.out.println(dtf.format(now));  
		   
		   ExecutorService executorService = Executors.newFixedThreadPool(2);
		   
		   //Kreira posebnu novu nitttt
		   executorService.execute(()-> {
			   apiRaund(ls);
		   });
		   
		   executorService.execute(()-> {
			   apiTopScorrer(ls);
		   });
		   
		
	
		//kada se odradi tabela	
	} 

	
	
	public int getYear() {
			Date d = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			int i = Integer.parseInt(formatter.format(d));
			
			System.out.println("Obradjujem godinu " + i);
		
		return i ;
	}
	

	public Season getSeason(int s) {
		
		Season season = sr.getOne(s);
		return season;
		
	}
	
	

	public List<League> apiLeague(Season se) {

		String json = null;
		List<League> ls = new ArrayList<League>();
		

		try {
			HttpResponse<String> response = Unirest.get(param.getAdd() + "/leagues/season/" + se.getSeason() )
					.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
			json = response.getBody();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (json != null) {
			System.out.println("Updateing league");
			JSONParser parse = new JSONParser();
			JSONObject o;
			try {
				o = (JSONObject) parse.parse(json);

				JSONObject o1 = (JSONObject) o.get("api");

				Long result = (Long) o1.get("results");
				Integer br = result.intValue();

				JSONArray leagues = (JSONArray) o1.get("leagues");
				for (int i = 0; i < result; i++) {
					JSONObject league = (JSONObject) leagues.get(i);
					League l = new League();
					Long id = (Long) league.get("league_id");
					l.setIdLeague(id.intValue());
					String name = (String) league.get("name");
					l.setName(name);
					String country = (String) league.get("country");
					Country c = cr.findByName(country);
					l.setCountry(c);
					l.setSeasonBean(se);
					String start = (String) league.get("season_start");
					l.setSeasonStart(start);
					String end = (String) league.get("season_end");
					l.setSeasonEnd(end);
					String logo = (String) league.get("logo");
					l.setLogo(logo);
					String type = (String) league.get("type");
					l.setType(type);

					
				    l =lr.save(l);
				    ls.add(l);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		json = null;
		return ls;
	}
	
	
	public void apiRaund(List<League> leagues) {

	
		for (League l : leagues) {
			if (l != null) {
				String json = null;

				try {
					HttpResponse<String> response = Unirest.get(param.getAdd() + "/fixtures/rounds/" + l.getIdLeague())
							.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2())
							.asString();
					json = response.getBody();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (json != null) {
					List<Round> runde = rr.findByLeague(l);
					JsonFactory factory = new JsonFactory();
					try {
						JsonParser parser = factory.createParser(json);
						while (!parser.isClosed()) {
							JsonToken jsonToken = parser.nextToken();
							if (JsonToken.FIELD_NAME.equals(jsonToken)) {
								String fieldName = parser.getCurrentName();
								if ("results".equals(fieldName)) {
									jsonToken = parser.nextToken();
									int br = parser.getIntValue();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									for (int i = 0; i < br; i++) {
										jsonToken = parser.nextToken();
										Round r = new Round();
										r.setReguralSeason(parser.getValueAsString());
										
										r.setReguralSeason(r.getReguralSeason().replaceAll("_", " "));
										
										if (!hasRound(runde, r)) {
											r.setLeague(l);
											rr.save(r);
										}
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				json=null;
			}
		}
	}

	private boolean hasRound(List<Round> rounds, Round round) {
		for (Round r : rounds) {
			if (r.getReguralSeason().equals(round.getReguralSeason())) {
				return true;
			}
		}
		rounds.add(round);
		return false;
	}
	
	//dodavanje topScorrer
		public void apiTopScorrer(List<League>league) {

			List<TopScorer> listaTopScorrera = new ArrayList<TopScorer>();

			String json = "";

			for (League l : league) {
				HttpResponse<String> response;
				try {
					response = Unirest.get(param.getAdd() + "/topscorers/" + l.getIdLeague())
							.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
					json = response.getBody();

				} catch (UnirestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					JSONParser parse = new JSONParser();
					JSONObject o;

					o = (JSONObject) parse.parse(json);

					JSONObject o1 = (JSONObject) o.get("api");

					Long result = (Long) o1.get("results");

					JSONArray nizPlayers = (JSONArray) o1.get("topscorers");
					for (int i = 0; i < nizPlayers.size(); i++) {
						JSONObject objectPlayers = (JSONObject) nizPlayers.get(i);

						TopScorer topScorer=new TopScorer();
						
						//player
						Long playerIdPom=(Long)objectPlayers.get("player_id");
						Integer playerId=playerIdPom.intValue();
						
						//league
						topScorer.setLeague(l);
						
						//team
						Long teamPom=(Long) objectPlayers.get("team_id");
						Team team=teamRepo.getOne(teamPom.intValue());
						
						List<TeamPlayer> teamPlayerList=teamplayerRepo.findByTeam(team);
						
						for(TeamPlayer teamP: teamPlayerList) {
							if(teamP.getPlayer().getIdPlayer() == playerId) {
								topScorer.setTeamPlayer(teamP);
							}
							
						}
						
						topScorrerRepo.save(topScorer);
						
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				System.out.println(json);
			}

		
		}
	
	
	
}
