package football.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.repository.LeagueRepo;
import football.repository.StandingRepo;
import football.repository.StandingstatsRepo;
import football.repository.TeamRepo;
import model.League;
import model.Stading;
import model.StandingStat;
import model.Team;

@RestController
@RequestMapping(value = "/standingController")
public class StandingController {

	private Param param = new Param();

	@Autowired
	LeagueRepo leagueRepo;

	@Autowired
	StandingRepo standingRepo;

	@Autowired
	TeamRepo teamRepo;

	@Autowired
	StandingstatsRepo standingStatsRepo;

	@RequestMapping(value = "/saveStanding", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public void apiStandings() {

		List<League> leagues = leagueRepo.findAll();

		for (League l : leagues) {

			List<Stading> standingDelete = standingRepo.findByLeague(l);

			for (Stading s : standingDelete) {
				List<StandingStat> standingStat = standingStatsRepo.findByStading(s);
				for(StandingStat ssd : standingStat) {
					standingStatsRepo.delete(ssd);
				}
				standingRepo.delete(s);
			}

			String json = null;

			try {
				HttpResponse<String> response = Unirest.get(param.getAdd() + "/leagueTable/" + l.getIdLeague())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
				json = response.getBody();
			} catch (UnirestException e1) {
				e1.printStackTrace();
			}

			if (json != null) {
				JSONParser parse = new JSONParser();
				JSONObject o;

				try {

					o = (JSONObject) parse.parse(json);
					JSONObject o1 = (JSONObject) o.get("api");

					JSONArray n = (JSONArray) o1.get("standings");

					for (int i = 0; i < n.size(); i++) {

						JSONArray n1 = (JSONArray) n.get(i);
						
						for (int j = 0; j < n1.size(); j++) {
							
						

							JSONObject o2 = (JSONObject) n1.get(j);
							Stading standing = new Stading();

							Object rank = o2.get("rank");
							//standing.setRank(rank instanceof Long ? ((Long) rank).intValue() : 0);
							//visak
							
							String group = (String) o2.get("group");
							standing.setGroup(group);

							String forme = (String) o2.get("forme");
							standing.setForm(forme);

							String description = (String) o2.get("description");
							standing.setDescription(description);

							Object teamId = o2.get("team_id");
							Team t = teamRepo.getOne(teamId instanceof Long ? ((Long) teamId).intValue() : 0);

							//Stading deleteStading = standingRepo.findByLeagueAndTeam(l, t);
							//if (deleteStading != null) {
							//	standingRepo.delete(deleteStading);
							//}

							standing.setTeam(t);
							
							standing.setLeague(l);

							Object goalsDiff = o2.get("goalsDiff");
							standing.setGoalsDiff(goalsDiff instanceof Long ? ((Long) goalsDiff).intValue() : 0);

							Object points = o2.get("points");
							standing.setPoints(points instanceof Long ? ((Long) points).intValue() : 0);

							String lastUpdate = (String) o2.get("lastUpdate");
							standing.setLastUpdate(lastUpdate);

							standing = standingRepo.save(standing);

							JSONObject o3 = (JSONObject) o2.get("all");
							StandingStat standingStatAll = new StandingStat();
							StandingStat standingStatHome = new StandingStat();
							StandingStat standingStatAway = new StandingStat();

							standingStatAll.setStading(standing);
							
							

							// ALL
							standingStatAll.setStandingStatName("all");

							Object matchesPlayed =  o3.get("matchesPlayed");
							standingStatAll.setMatchesPlayed(matchesPlayed instanceof Long ? ((Long) matchesPlayed).intValue() : 0);

							Object win = o3.get("win");
							standingStatAll.setWin(win instanceof Long ? ((Long) win).intValue() : 0);

							Object draw = o3.get("draw");
							standingStatAll.setDraw(draw instanceof Long ? ((Long) draw).intValue() : 0);

							Object lose = o3.get("lose");
							standingStatAll.setLose(lose instanceof Long ? ((Long) lose).intValue() : 0);

							Object goalsFor = o3.get("goalsFor");
							standingStatAll.setGoalsFor(goalsFor instanceof Long ? ((Long) goalsFor).intValue() : 0);

							Object goalsAgainst = o3.get("goalsAgainst");
							standingStatAll.setGoalsAgainst(goalsAgainst instanceof Long ? ((Long) goalsAgainst).intValue() : 0);
							
							standingStatsRepo.save(standingStatAll);
							
							JSONObject o4 = (JSONObject) o2.get("home");

							
							standingStatHome.setStading(standing);
							// HOME
							standingStatHome.setStandingStatName("home");

							matchesPlayed = (Long) o4.get("matchesPlayed");
							standingStatHome.setMatchesPlayed(matchesPlayed instanceof Long ? ((Long) matchesPlayed).intValue() : 0);

							win = (Long) o4.get("win");
							standingStatHome.setWin(win instanceof Long ? ((Long) win).intValue() : 0);

							draw = (Long) o4.get("draw");
							standingStatHome.setDraw(draw instanceof Long ? ((Long) draw).intValue() : 0);

							lose = (Long) o4.get("lose");
							standingStatHome.setLose(lose instanceof Long ? ((Long) lose).intValue() : 0);

							goalsFor = (Long) o4.get("goalsFor");
							standingStatHome.setGoalsFor(goalsFor instanceof Long ? ((Long) goalsFor).intValue() : 0);

							goalsAgainst = (Long) o4.get("goalsAgainst");
							standingStatHome.setGoalsAgainst(goalsAgainst instanceof Long ? ((Long) goalsAgainst).intValue() : 0);
							
							standingStatsRepo.save(standingStatHome);
							
							JSONObject o5 = (JSONObject) o2.get("away");

							standingStatAway.setStading(standing);
							// AWAY
							standingStatAway.setStandingStatName("away");

							matchesPlayed = (Long) o5.get("matchesPlayed");
							standingStatAway.setMatchesPlayed(matchesPlayed instanceof Long ? ((Long) matchesPlayed).intValue() : 0);

							win = (Long) o5.get("win");
							standingStatAway.setWin(win instanceof Long ? ((Long) win).intValue() : 0);

							draw = (Long) o5.get("draw");
							standingStatAway.setDraw(draw instanceof Long ? ((Long) draw).intValue() : 0);

							lose = (Long) o5.get("lose");
							standingStatAway.setLose(lose instanceof Long ? ((Long) lose).intValue() : 0);

							goalsFor = (Long) o5.get("goalsFor");
							standingStatAway.setGoalsFor(goalsFor instanceof Long ? ((Long) goalsFor).intValue() : 0);

							goalsAgainst = (Long) o5.get("goalsAgainst");
							standingStatAway.setGoalsAgainst(goalsAgainst instanceof Long ? ((Long) goalsAgainst).intValue() : 0);
							
							standingStatsRepo.save(standingStatAway);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
