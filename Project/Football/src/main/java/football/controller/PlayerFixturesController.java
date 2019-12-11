package football.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.repository.FixtureRepo;
import football.repository.PlayerRepo;
import football.repository.PlayerfixstatRepo;
import football.repository.TeamRepo;
import football.repository.TeamplayerRepo;
import model.Fixture;
import model.Player;
import model.PlayerFixStat;
import model.Team;
import model.TeamPlayer;

import java.util.*;

@RestController
public class PlayerFixturesController {
	@Autowired
	PlayerRepo pr;

	@Autowired
	FixtureRepo fr;

	@Autowired
	TeamRepo tr;

	@Autowired
	TeamplayerRepo tpr;

	@Autowired
	PlayerfixstatRepo pfr;

	private Param param = new Param();

	@RequestMapping(value = "playerFixtursStatisic")
	public List<PlayerFixStat> getApiPlayerFixStat() {
		// pfr.deleteAll();
		List<PlayerFixStat> listPlayerFixStat = new ArrayList<PlayerFixStat>();

		String json = "";

		HttpResponse<String> response;

		List<Fixture> fixtures = fr.findAll();

		for (Fixture f : fixtures) {
			try {
				response = Unirest.get(param.getAdd() + "/players/fixture/" + f.getIdFixtures())
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

				JSONArray nizPlayers = (JSONArray) o1.get("players");

				for (int i = 0; i < result; i++) {
					JSONObject objectPlayers = (JSONObject) nizPlayers.get(i);
					PlayerFixStat playerFixStatisic = new PlayerFixStat();

					// player Id
					Long playerIdPom = (Long) objectPlayers.get("player_id");
					Integer playerId = playerIdPom.intValue();

					playerFixStatisic.setFixture(f);

					// rating
					playerFixStatisic.setRating((String) objectPlayers.get("rating"));

					// captain
					playerFixStatisic.setCaptain((String) objectPlayers.get("captain"));

					// substitute
					playerFixStatisic.setSubstitute((String) objectPlayers.get("substitute"));

					// offsides
					playerFixStatisic.setOffsides((String) objectPlayers.get("offsides"));

					// shots
					JSONObject shots = (JSONObject) objectPlayers.get("shots");

					Long shotsTotalpom = (Long) shots.get("total");
					Integer shotsTotal = shotsTotalpom.intValue();
					playerFixStatisic.setShotsTotal(shotsTotal);

					Long shotsOnPom = (Long) shots.get("on");
					Integer shotsOn = shotsOnPom.intValue();
					playerFixStatisic.setShotsOn(shotsOn);

					// goals
					JSONObject goals = (JSONObject) objectPlayers.get("goals");

					Long goalsTotalpom = (Long) goals.get("total");
					Integer goalsTotal = goalsTotalpom.intValue();
					playerFixStatisic.setGoalsTotal(goalsTotal);

					Long goalsConcededpom = (Long) goals.get("conceded");
					Integer goalsConceded = goalsConcededpom.intValue();
					playerFixStatisic.setGoalsConceded(goalsConceded);

					Long goalsAssistsPom = (Long) goals.get("assists");
					Integer goalsAssists = goalsAssistsPom.intValue();
					playerFixStatisic.setGoalsAssists(goalsAssists);

					// passes
					JSONObject passes = (JSONObject) objectPlayers.get("passes");

					Long passesTotalPom = (Long) passes.get("total");
					Integer passesTotal = passesTotalPom.intValue();
					playerFixStatisic.setPassesTotal(passesTotal);

					Long passesKeyPom = (Long) passes.get("key");
					Integer passesKey = passesKeyPom.intValue();
					playerFixStatisic.setPassesKey(passesKey);

					Long passesAccuracyPom = (Long) passes.get("accuracy");
					Integer passesAccuracy = passesAccuracyPom.intValue();
					playerFixStatisic.setPassesAccuracy(passesAccuracy);

					// tackles
					JSONObject tackles = (JSONObject) objectPlayers.get("tackles");

					Long tacklesTotalPom = (Long) tackles.get("total");
					Integer tacklesTotal = tacklesTotalPom.intValue();
					playerFixStatisic.setTacklesTotal(tacklesTotal);

					Long tacklesBlocksPom = (Long) tackles.get("blocks");
					Integer tacklesBlocks = tacklesBlocksPom.intValue();
					playerFixStatisic.setTacklesBlocks(tacklesBlocks);

					Long tacklesInterceptionsPom = (Long) tackles.get("interceptions");
					Integer tacklesInterceptions = tacklesInterceptionsPom.intValue();
					playerFixStatisic.setTacklesInterceptions(tacklesInterceptions);

					// duels
					JSONObject duels = (JSONObject) objectPlayers.get("duels");

					Long duelsTotalPom = (Long) duels.get("total");
					Integer duelsTotal = duelsTotalPom.intValue();
					playerFixStatisic.setDuelsTotal(duelsTotal);

					Long duelsWonPom = (Long) duels.get("won");
					Integer duelsWon = duelsWonPom.intValue();
					playerFixStatisic.setDuelsWon(duelsWon);

					// dribbles
					JSONObject dribbles = (JSONObject) objectPlayers.get("dribbles");

					Long dribblesAttemptsPom = (Long) dribbles.get("attempts");
					Integer dribblesAttempts = dribblesAttemptsPom.intValue();
					playerFixStatisic.setDribblesAttempts(dribblesAttempts);

					Long dribblesSucessPom = (Long) dribbles.get("attempts");
					Integer dribblesSucess = dribblesSucessPom.intValue();
					playerFixStatisic.setDribblesSuccess(dribblesSucess);

					Long dribblesPastPom = (Long) dribbles.get("past");
					Integer dribblesPast = dribblesPastPom.intValue();
					playerFixStatisic.setDribblesPast(dribblesPast);

					// fouls
					JSONObject fouls = (JSONObject) objectPlayers.get("fouls");

					Long foulsDrawnPom = (Long) fouls.get("drawn");
					Integer foulsDrawn = foulsDrawnPom.intValue();
					playerFixStatisic.setFoulsDrawn(foulsDrawn);

					Long foulsCommittedPom = (Long) fouls.get("committed");
					Integer foulsCommitted = foulsCommittedPom.intValue();
					playerFixStatisic.setFoulsComitted(foulsCommitted);

					// cards
					JSONObject cards = (JSONObject) objectPlayers.get("cards");

					Long cardsYellowPom = (Long) cards.get("yellow");
					Integer cardsYellow = cardsYellowPom.intValue();
					playerFixStatisic.setCardsYellow(cardsYellow);

					Long cardsRedPom = (Long) cards.get("red");
					Integer cardsRed = cardsRedPom.intValue();
					playerFixStatisic.setCardsRed(cardsRed);

					// penalty
					JSONObject penalty = (JSONObject) objectPlayers.get("penalty");

					Long penaltyWonPom = (Long) penalty.get("won");
					Integer penaltyWon = penaltyWonPom.intValue();
					playerFixStatisic.setCardsRed(penaltyWon);

					Long penaltyCommitedPom = (Long) penalty.get("commited");
					Integer penaltyCommited = penaltyCommitedPom.intValue();
					playerFixStatisic.setPenaltyComitted(penaltyCommited);

					Long penaltySuccessPom = (Long) penalty.get("success");
					Integer penaltySuccess = penaltySuccessPom.intValue();
					playerFixStatisic.setPenaltySuccess(penaltySuccess);

					Long penaltyMissedPom = (Long) penalty.get("missed");
					Integer penaltyMissed = penaltyMissedPom.intValue();
					playerFixStatisic.setPenaltyMissed(penaltyMissed);

					Long penaltySavedPom = (Long) penalty.get("saved");
					Integer penaltySaved = penaltySavedPom.intValue();
					playerFixStatisic.setPenaltySaved(penaltySaved);

					// save u bazi

					Long teamPlayerIdPom = (Long) objectPlayers.get("team_id");
					Team team = tr.getOne(teamPlayerIdPom.intValue());

					List<TeamPlayer> teamPlayerList = tpr.findByTeam(team);

					for (TeamPlayer teamp : teamPlayerList) {
						if (teamp.getPlayer().getIdPlayer() == playerId && teamp.getSeason().getSeason() == f.getRound()
								.getLeague().getSeasonBean().getSeason()) {
							playerFixStatisic.setTeamPlayer(teamp);
						}
					}

					pfr.save(playerFixStatisic);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return listPlayerFixStat;
	}
}
