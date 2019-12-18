package football.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import football.repository.BookmakerRepo;
import football.repository.CountryRepo;
import football.repository.LabelRepo;
import football.repository.PlayerRepo;
import football.repository.SeasonRepo;
import football.repository.TeamRepo;
import football.repository.TeaminRepo;
import football.repository.TeamoutRepo;
import football.repository.TeamplayerRepo;
import football.repository.TransferRepo;
import model.Bookmaker;
import model.Country;
import model.Label;
import model.Player;
import model.Season;
import model.Team;
import model.TeamIn;
import model.TeamOut;
import model.TeamPlayer;
import model.Transfer;

@Component
public class Timer3Months {

	private Param param = new Param();
	@Autowired
	CountryRepo cr;
	@Autowired
	SeasonRepo sr;
	@Autowired
	PlayerRepo pr;
	@Autowired
	TeamplayerRepo tpr;
	@Autowired
	TeamRepo tr;
	@Autowired
	TransferRepo transr;
	@Autowired
	BookmakerRepo bkr;
	@Autowired
	LabelRepo lr;
	
	@Autowired
	TeaminRepo tir;
	@Autowired
	TeamoutRepo tor;

	@Scheduled(cron = "0 0 0 1 1,4,7,10 ?")
	public void update() {
		ExecutorService executor = Executors.newFixedThreadPool(6);

		executor.execute(() -> {
			apiCountry();
		});
		executor.execute(() -> {
			apiSeason();
		});
		executor.execute(() -> {
			Season s=getSeason();
			apiSquad(s);
		});
		executor.execute(() -> {
			apiTransfer();
		});
		executor.execute(() -> {
			apiBookmaker();
		});
		executor.execute(() -> {
			apiLabel();
		});
	}
	
	public Season getSeason() {
		Date d= new Date();
		int y =d.getYear()+1900;
		Season s = sr.getOne(y);
		return s;
	}

	public void apiCountry() {
		String json = null;
		try {
			HttpResponse<String> response = Unirest.get(param.getAdd() + "/countries")
					.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
			json = response.getBody();

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (json != null) {

			List<Country> cs = cr.findAll();

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
							jsonToken = parser.nextToken();
							for (int i = 0; i < br; i++) {
								Country c = new Country();
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								c.setName(parser.getValueAsString());
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								c.setCode(parser.getValueAsString());
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								c.setFlag(parser.getValueAsString());
								if (!hasCountry(c, cs)) {
									cr.save(c);
								}
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
							}
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean hasCountry(Country country, List<Country> cs) {
		for (Country c : cs) {
			if (c.getName().equals(country.getName()))
				return true;
		}
		cs.add(country);
		return false;
	}

	public void apiSeason() {

		String json = null;
		try {
			HttpResponse<String> response = Unirest.get(param.getAdd() + "/seasons")
					.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
			json = response.getBody();

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (json != null) {

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
								Season s = new Season();
								jsonToken = parser.nextToken();
								s.setSeason(parser.getIntValue());
								int st = s.getSeason() + 1;
								System.out.println(parser.getIntValue());
								System.out.println(parser.getIntValue());
								s.setSeasonText(s.getSeason() + "-" + st);
								System.out.println(s);
								System.out.println(s.getSeason());
								System.out.println(s.getSeasonText());
								s = sr.save(s);
								System.out.println(s.getSeason());
							}
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<Player> apiSquad(Season s) {
		List<Team> teams = tr.findAll();
		String json;

		List<Player> listSquadPlayer = new ArrayList<Player>();

		for (Team t : teams) {

			try {

				HttpResponse<String> response = Unirest
						.get(param.getAdd() + "/players/squad/" + t.getIdTeam() + "/" + s.getSeason())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
				json = response.getBody();

				/*
				 * HttpResponse<String> response =
				 * Unirest.get(param.getAdd()+"/players/squad/1/2018")
				 * .header("x-rapidapi-host", param.getH1()) .header("x-rapidapi-key",
				 * param.getH2()).asString(); json=response.getBody();
				 */
				System.out.println(json);

				JSONParser parse = new JSONParser();
				JSONObject o = (JSONObject) parse.parse(json);
				JSONObject o1 = (JSONObject) o.get("api");

				Long result = (Long) o1.get("results");
				Integer intigerFixturs = result.intValue();

				JSONArray nizPlayers = (JSONArray) o1.get("players");

				for (int i = 0; i < result; i++) {
					JSONObject objectPlayers = (JSONObject) nizPlayers.get(i);

					Player player = new Player();
					// player Id
					Long playerIdPom = (Long) objectPlayers.get("player_id");
					Integer playerId = playerIdPom.intValue();
					player.setIdPlayer(playerId);

					// player Name
					player.setPlayerName((String) objectPlayers.get("player_name"));

					// player First Name
					player.setFristName((String) objectPlayers.get("firstname"));

					// player Last Name
					player.setLastName((String) objectPlayers.get("lastname"));

					// number

					// position
					player.setPosition((String) objectPlayers.get("position"));

					// age
					Long playerAgePom = (Long) objectPlayers.get("age");
					Integer playerAge = playerAgePom.intValue();
					player.setAge(playerAge);

					// birth date
					player.setBrithDate((String) objectPlayers.get("birth_date"));

					// birth place
					player.setBrithPlace((String) objectPlayers.get("birth_place"));

					// birth country
					player.setBrithCountry((String) objectPlayers.get("birth_country"));

					// nationality
					player.setNationality((String) objectPlayers.get("nationality"));

					// heigth
					String heigthPom = (String) objectPlayers.get("height");
					if (heigthPom != null && heigthPom != "") {
						player.setHeight(heigthPom);
					}

					// weigth
					String weigthPom = (String) objectPlayers.get("weight");
					if (weigthPom != null && weigthPom != "") {
						player.setWeight(weigthPom);
					}

					// save u bazi

					pr.save(player);
					List<TeamPlayer> tpb = tpr.findByPlayerAndTeamAndSeason(player, t, s);
					TeamPlayer tp = new TeamPlayer();

					tp.setPlayer(player);
					tp.setSeason(s);

					tp.setTeam(t);
					if (tpb.size() == 0) {
						tpr.save(tp);
					}

					listSquadPlayer.add(player);

				}

			} catch (UnirestException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listSquadPlayer;
	}

	public void apiTransfer() {
		String json = null;
		List<Player> players = pr.findAll();
		List<Transfer> transferi = transr.findAll();

		for (Player p : players) {
			try {
				HttpResponse<String> response = Unirest.get(param.getAdd() + "/transfers/player/" + p.getIdPlayer())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
				json = response.getBody();

			} catch (UnirestException e) {
				e.printStackTrace();
			}

			if (json != null) {
				JSONParser parse = new JSONParser();
				JSONObject o;
				try {
					o = (JSONObject) parse.parse(json);
					JSONObject api = (JSONObject) o.get("api");
					JSONArray transfers = (JSONArray) api.get("transfers");
					for (int i = 0; i < transfers.size(); i++) {
						JSONObject transfer = (JSONObject) transfers.get(i);
						Transfer t = new Transfer();
						Long lu = (Long) transfer.get("lastUpdate");
						t.setLastUpdate(lu.intValue() + "");
						t.setType((String) transfer.get("type"));
						TeamIn ti = new TeamIn();
						JSONObject tio = (JSONObject) transfer.get("team_in");
						Long idTeamIn = (Long) tio.get("team_id");
						if (idTeamIn!=null) {
						Team teamIn = tr.getOne(idTeamIn.intValue());
						ti.setTeam(teamIn);
						ti = tir.save(ti);
						t.setTeamIn(ti);
						}
						TeamOut to = new TeamOut();
						JSONObject too = (JSONObject) transfer.get("team_out");
						Object idTeamOut = (Object) too.get("team_id");
						if (idTeamOut instanceof Long) {
							Long idTeamOutL = (Long) idTeamOut;
							Team teamOut = tr.getOne(idTeamOutL.intValue());
							to.setTeam(teamOut);
							to = tor.save(to);
							t.setTeamOut(to);
						}
						
						t.setPlayer(p);
						t.setTransferDate((String) transfer.get("transfer_date"));
						saveTransfer(transferi, t);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			json = null;
		}
	}

	public void saveTransfer(List<Transfer> transferi, Transfer t) {
		for (Transfer trans : transferi) {
			if (trans.getPlayer().getIdPlayer() == t.getPlayer().getIdPlayer()
					&& trans.getTeamIn().getIdTeamIn() == t.getTeamIn().getIdTeamIn()
					&& trans.getTransferDate().equals(t.getTransferDate())) {
				trans.setLastUpdate(t.getLastUpdate());
				transr.save(trans);
				return;
			}
		}
		t = transr.save(t);
		transferi.add(t);
	}
	
	public void apiBookmaker() {
		String json = null;

		try {
			HttpResponse<String> response = Unirest.get(param.getAdd() + "/odds/bookmakers/")
					.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
			json = response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (json != null) {
			List<Bookmaker> bms=bkr.findAll();
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
								Bookmaker b = new Bookmaker();
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								b.setIdBookmaker(parser.getIntValue());
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								b.setName(parser.getValueAsString());
								jsonToken = parser.nextToken();
								if (!bms.contains(b)) {
									bms.add(b);
									bkr.save(b);
								}
								
							}
						}
					}
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	
	public void apiLabel() {
		String json = null;

		try {
			HttpResponse<String> response = Unirest.get(param.getAdd() + "/odds/labels/")
					.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
			json = response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (json != null) {
			List<Label> labels=lr.findAll();
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
								Label l = new Label();
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								l.setIdLabel(parser.getIntValue());
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								l.setLabel(parser.getValueAsString());
								jsonToken = parser.nextToken();
								if (!labels.contains(l)) {
									labels.add(l);
									lr.save(l);
								}
								
							}
						}
					}
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
