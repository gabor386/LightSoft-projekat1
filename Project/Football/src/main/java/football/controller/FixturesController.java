package football.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.repository.AwayteamRepo;

import football.repository.CountryRepo;
import football.repository.FixtureRepo;
import football.repository.HometeamRepo;
import football.repository.LeagueRepo;
import football.repository.RoundRepo;
import football.repository.ScoreRepo;
import football.repository.TeamRepo;
import model.Awayteam;
import model.Fixture;
import model.Hometeam;
import model.League;
import model.Round;
import model.Score;
import model.Team;


@RestController
@RequestMapping(value="/fixtureController")
public class FixturesController {
	
	
	private Param param=new Param();
	
	@Autowired
	FixtureRepo fixtureRepo;
	
	@Autowired
	ScoreRepo scoreRepo;
	
	@Autowired
	RoundRepo roundRepo;
	
	@Autowired
	LeagueRepo leagueRepo;
	
	@Autowired
	HometeamRepo homeTeamRepo;
	
	@Autowired
	AwayteamRepo awayTeamRepo;
	
	@Autowired
	TeamRepo teamRepo;
	
	
	@Autowired
   CountryRepo cr;
	
	

	
	@RequestMapping(value="/saveFixture", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
	public String saveFixture() {
		
		List<Fixture> fixtures  = apiFixtures();
			fixtureRepo.saveAll(fixtures);
		
		return "Uspesno";
		
	}
	
	
	
	
	public List<Fixture> apiFixtures() {
		
		List<Fixture> retFixture = new ArrayList<Fixture>();
		

		try {

			HttpResponse<String> response = Unirest.get(param.getAdd()+"/fixtures/league/357")
					.header("x-rapidapi-host", param.getH1())
					.header("x-rapidapi-key", param.getH2()).asString();
			
			
			
			String json = response.getBody();
			
			JSONParser parse = new JSONParser();
			
			JSONObject o = (JSONObject)parse.parse(json);
			
			JSONObject o1 =  (JSONObject) o.get("api");
				
			JSONArray n1 = (JSONArray) o1.get("fixtures");
			
			for(int i=0;i<n1.size();i++){
				
				JSONObject o2 = (JSONObject)n1.get(i);
				
				Fixture fixture = new Fixture();
				

				//System.out.println( "Cao da li radis " +  o2.get("fixture_id"));
			
				
				  Long idLongFixture =  (Long) o2.get("fixture_id");
				  Integer idFixture = idLongFixture.intValue();
				  fixture.setIdFixtures(idFixture);
				  
				  
				   Long eventTimeStampLong =  (Long) o2.get("event_timestamp");
				   Integer eventTimeStamp = eventTimeStampLong.intValue();
				   fixture.setEventTimeStamp(eventTimeStamp);
				  	
				 

				   Long firstHalfStartLong =  (Long) o2.get("firstHalfStart");
				   Integer firstHalfStart  = firstHalfStartLong.intValue();
				   fixture.setFristHalfStart(firstHalfStart);
				   

				   Long secondtHalfStartLong = (Long) o2.get("secondHalfStart");
				   Integer secondHalfStart  = secondtHalfStartLong.intValue();
				   fixture.setSecondHalfStart(secondHalfStart);
				   
				   Long elapsedLong = (Long) o2.get("elapsed");
				   Integer elapsed  = elapsedLong.intValue();
				   fixture.setElapsed(elapsed);
				   
				   Long goalsHomeTeamLong = (Long) o2.get("goalsHomeTeam"); 
				   Integer goalsHomeTeam  =goalsHomeTeamLong.intValue();
				   fixture.setGoalsHomeTeam(goalsHomeTeam);
				   
				   Long goalsAwayTeamLong = (Long) o2.get("goalsAwayTeam"); 
				   Integer goalsAwayTeam  = goalsAwayTeamLong.intValue();
				   fixture.setGoalsAwayTeam(goalsAwayTeam);
				    
				   
				   
				   fixture.setEventDate((String) o2.get("event_date"));
				   fixture.setStatus((String) o2.get("status"));
				   fixture.setStatusShort((String) o2.get("statusShort"));
				   fixture.setVenue((String) o2.get("venue"));
				   fixture.setReferee((String) o2.get("referee"));
				
				
//			  String pomRound = (String) o2.get("round");
//			  
//		
//			 
//			 Long idLeagueLong = (Long) o2.get("league_id"); 
//			 Integer idLeague  =  idLeagueLong.intValue();
//			  
//
//			  League league = leagueRepo.getOne(idLeague);
//			  List<Round> rounds = roundRepo.findByLeague(league);
//			  
//			  for(Round r : rounds) {
//				  if(r.getReguralSeason().equals(pomRound)) {
//					  fixture.setRound(r);
//				  }
//			  }
//			  
//			 
//			  
////			  JSONObject o3 =  (JSONObject) o2.get("homeTeam");
////			  JSONObject o4 =  (JSONObject) o2.get("awayTeam");
//			  JSONObject o5 =   (JSONObject) o2.get("score");
//			  
//			  Score score = new Score();
//			  score.setHalfTime((String) o5.get("halftime"));
//			  score.setFullTime((String) o5.get("fulltime"));
//			  score.setExtraTime((String) o5.get("extratime"));
//			  score.setPenalty((String) o5.get("penalty"));
//			  
//			  scoreRepo.save(score);
//			  fixture.setScore(score);
//			  
			
//			  
//			  Hometeam homeTeam = new Hometeam();
//			  Awayteam awayTeam = new Awayteam();
//			  Team team = new Team();
//			  
//			  Long idHomeTeamLong = (Long) o3.get("team_id"); 
//			  Integer idHomeTeam  = idHomeTeamLong != null ? idHomeTeamLong.intValue() : null;
//			  team.setIdTeam(idHomeTeam);
//			  
//			  teamRepo.save(team);
//			  
//			  homeTeam.setTeam(team);
//			  homeTeamRepo.save(homeTeam);
//			  
//			  Long idAwayTeamLong = (Long) o4.get("team_id"); 
//			  Integer idAwayTeam  = idAwayTeamLong != null ? idAwayTeamLong.intValue() : null;
//			  team.setIdTeam(idAwayTeam);
//			  
//			  teamRepo.save(team);
//			  
//			  awayTeam.setTeam(team);
//			  awayTeamRepo.save(awayTeam);
//			 
//			  fixture.setHometeam(homeTeam);
//			  fixture.setAwayteam(awayTeam);
			 
			  
			  
			  retFixture.add(fixture);
				
			}
			
			
		} catch (UnirestException | ParseException  e1) {
			e1.printStackTrace();
		}
		return retFixture;
	}

}
