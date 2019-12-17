package football.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.repository.FixtureRepo;
import football.repository.LastfivestatRepo;
import football.repository.PredictionRepo;
import football.repository.TeamRepo;
import model.Fixture;
import model.LastFiveStat;
import model.Prediction;
import model.Team;

import java.util.*;

@RestController
public class LastFiveStatisicsController {

	@Autowired
	PredictionRepo pr;

	@Autowired
	FixtureRepo fr;

	@Autowired
	TeamRepo tr;

	@Autowired
	LastfivestatRepo lfr;

	private Param param = new Param();

	@RequestMapping(value = "LastFiveStatistics")
	public List<LastFiveStat> apiLastFiveStatistics() {
		List<LastFiveStat> listaLastFiveStat = new ArrayList<LastFiveStat>();

		List<Fixture> listaFixutres = fr.findAll();

		String json = "";

		HttpResponse<String> response;

		for (Fixture f : listaFixutres) {

			try {
				response = Unirest.get(param.getAdd() + "/predictions/" + f.getIdFixtures())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
				json = response.getBody();
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(json);

			try {
				JSONParser parse = new JSONParser();
				JSONObject o;

				o = (JSONObject) parse.parse(json);

				JSONObject o1 = (JSONObject) o.get("api");

				Long result = (Long) o1.get("results");

				JSONArray nizPlayers = (JSONArray) o1.get("predictions");

				for (int i = 0; i < result; i++) {
					JSONObject objectPrediction = (JSONObject) nizPlayers.get(i);

					LastFiveStat lastFiveStatistics = new LastFiveStat();

					JSONObject team = (JSONObject) objectPrediction.get("teams");

					JSONObject home = (JSONObject) team.get("home");

					// team_id
					Long teamIdPom = (Long) home.get("team_id");
					Integer teamId = teamIdPom.intValue();

					JSONObject lastFiveMatches = (JSONObject) home.get("last_5_matches");

					lastFiveStatistics.setForme((String) lastFiveMatches.get("forme"));

					lastFiveStatistics.setAtt((String) lastFiveMatches.get("att"));

					lastFiveStatistics.setDef((String) lastFiveMatches.get("def"));

					// Goals
					Long goalsPom = (Long) lastFiveMatches.get("goals");
					Integer goals = goalsPom.intValue();
					lastFiveStatistics.setGoals(goals);

					// Goals avg
					Long goalsAVGPom = (Long) lastFiveMatches.get("goals_avg");
					Integer goalsAVG = goalsAVGPom.intValue();
					lastFiveStatistics.setGoalsAvg(goalsAVG);

					// goals Against
					Long goalsAgainstPom = (Long) lastFiveMatches.get("goals_against");
					Integer goalsAgainst = goalsAgainstPom.intValue();
					lastFiveStatistics.setGoalsAgainst(goalsAgainst);

					// goals against avg
					Long goalsAgainstAVGPom = (Long) lastFiveMatches.get("goals_against_avg");
					Integer goalsAgainstAVG = goalsAgainstAVGPom.intValue();
					lastFiveStatistics.setGoalsAgainstAvg(goalsAgainstAVG);

					// team_id

					Team teamPom = tr.getOne(teamId);
					if (teamPom != null) {
						lastFiveStatistics.setTeam(teamPom);
					}

					lfr.save(lastFiveStatistics);

					listaLastFiveStat.add(lastFiveStatistics);
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		return listaLastFiveStat;

	}

}
