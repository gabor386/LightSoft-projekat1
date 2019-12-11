package football.controller;

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

import football.repository.FixtureRepo;
import football.repository.LineupRepo;
import football.repository.PlayerRepo;
import football.repository.StartxiRepo;
import football.repository.SubstituteRepo;
import football.repository.TeamRepo;
import football.repository.TeamplayerRepo;
import model.Fixture;
import model.Lineup;
import model.Player;
import model.Startxi;
import model.Substitute;
import model.Teamplayer;

@RestController
@RequestMapping(value = "/LineUpController")
public class LineUpController {

	private Param param = new Param();

	@Autowired
	SubstituteRepo substituteRepo;

	@Autowired
	StartxiRepo startxiRepo;

	@Autowired
	TeamRepo teamRepo;

	@Autowired
	LineupRepo lineUpRepo;

	@Autowired
	FixtureRepo fixtureRepo;

	@Autowired
	PlayerRepo playerRepo;

	@Autowired
	TeamplayerRepo teamplayerRepo;

	@RequestMapping(value = "/saveLineUp", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public void apiFixtures() {

		List<Fixture> fixtures = fixtureRepo.findAll();

		for (Fixture f : fixtures) {

			List<Lineup> lineUpDelete = lineUpRepo.findByFixture(f);
			
			for(Lineup l : lineUpDelete) {
				lineUpRepo.delete(l);
			}
			
			
			
			HttpResponse<String> response = null;
			try {
				response = Unirest.get(param.getAdd() + "/lineups/" + f.getIdFixtures())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
			} catch (UnirestException e1) {
				e1.printStackTrace();
			}

			String json = response.getBody();

			JSONParser parse = new JSONParser();
			JSONObject o;

			try {

				o = (JSONObject) parse.parse(json);
				JSONObject o1 = (JSONObject) o.get("api");

				JSONObject o2 = (JSONObject) o1.get("lineUps");

				// Tim1 ++++++++++++++
				
				JSONObject tim1 = (JSONObject) o2.get(f.getHometeam().getTeam().getTeamName());
				Lineup lineup = new Lineup();

				String formacija = (String) tim1.get("formation");
				lineup.setFormation(formacija);

				lineup.setTeam(f.getHometeam().getTeam());

				lineup.setFixture(f);

				lineup = lineUpRepo.save(lineup);

				// Tim 2 ++++++++++++++
				
				JSONObject tim2 = (JSONObject) o2.get(f.getAwayteam().getTeam().getTeamName());
				lineup = new Lineup();

				formacija = (String) tim2.get("formation");
				lineup.setFormation(formacija);

				lineup.setTeam(f.getAwayteam().getTeam());

				lineup.setFixture(f);

				lineup = lineUpRepo.save(lineup);

				// Tim 1 +++++++++++
				JSONArray n = (JSONArray) tim1.get("startXI");

				for (int i = 0; i < n.size(); i++) {

					JSONObject objekatUNizu = (JSONObject) n.get(i);

					Startxi startxi = new Startxi();

					Object playerIdLong = objekatUNizu.get("player_id");
					Integer playerId = playerIdLong instanceof Long ? ((Long) playerIdLong).intValue() : 0;

					Player player = playerRepo.getOne(playerId);
					List<Teamplayer> pomList = teamplayerRepo.findByPlayer(player);

					for (Teamplayer tp : pomList) {

						if (tp.getSeason().getSeason() == f.getRound().getLeague().getSeasonBean().getSeason()) {
							startxi.setTeamplayer(tp);
						}

					}
					startxi.setLineup(lineup);

					startxi = startxiRepo.save(startxi);

				}
				// Izmene tim 1 +++++++++++
				JSONArray n1 = (JSONArray) tim1.get("substitutes");

				for (int i = 0; i < n1.size(); i++) {

					JSONObject objekat = (JSONObject) n1.get(i);

					Substitute substitutes = new Substitute();

					Object playerIdLong = objekat.get("player_id");
					Integer playerId = playerIdLong instanceof Long ? ((Long) playerIdLong).intValue() : 0;

					Player player = playerRepo.getOne(playerId);
					List<Teamplayer> pomList2 = teamplayerRepo.findByPlayer(player);

					for (Teamplayer tp : pomList2) {

						if (tp.getSeason().getSeason() == f.getRound().getLeague().getSeasonBean().getSeason()) {
							substitutes.setTeamplayer(tp);
						}
					}
					substitutes.setLineup(lineup);
					substitutes = substituteRepo.save(substitutes);
				}

				// Tim 2 +++++++++++++++++

				JSONArray n2 = (JSONArray) tim2.get("startXI");

				for (int i = 0; i < n2.size(); i++) {
					
					JSONObject objekatUNizu = (JSONObject) n2.get(i);

					Startxi startxi = new Startxi();

					Object playerIdLong = objekatUNizu.get("player_id");
					Integer playerId = playerIdLong instanceof Long ? ((Long) playerIdLong).intValue() : 0;

					Player player = playerRepo.getOne(playerId);
					List<Teamplayer> pomList = teamplayerRepo.findByPlayer(player);

					for (Teamplayer tp : pomList) {

						if (tp.getSeason().getSeason() == f.getRound().getLeague().getSeasonBean().getSeason()) {
							startxi.setTeamplayer(tp);
						}

					}
					startxi.setLineup(lineup);

					startxi = startxiRepo.save(startxi);

				}
				
				// Izmene tim 2 +++++++++++++
				JSONArray n3 = (JSONArray) tim2.get("substitutes");

				for (int i = 0; i < n3.size(); i++) {

					JSONObject objekat = (JSONObject) n3.get(i);

					Substitute substitutes = new Substitute();

					Object playerIdLong = objekat.get("player_id");
					Integer playerId = playerIdLong instanceof Long ? ((Long) playerIdLong).intValue() : 0;

					Player player = playerRepo.getOne(playerId);
					List<Teamplayer> pomList2 = teamplayerRepo.findByPlayer(player);

					for (Teamplayer tp : pomList2) {

						if (tp.getSeason().getSeason() == f.getRound().getLeague().getSeasonBean().getSeason()) {
							substitutes.setTeamplayer(tp);
						}
					}
					substitutes.setLineup(lineup);
					substitutes = substituteRepo.save(substitutes);
				}

			} catch (ParseException e) {

				e.printStackTrace();
			}
		}

	}

}
