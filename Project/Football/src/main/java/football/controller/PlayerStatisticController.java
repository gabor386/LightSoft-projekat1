package football.controller;

import java.util.ArrayList;
import java.util.List;

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

import football.repository.PlayerRepo;
import football.repository.PlayerstatisticRepo;
import football.repository.SeasonRepo;
import football.repository.TeamRepo;
import football.repository.TeamplayerRepo;
import model.Player;
import model.PlayerStatistic;
import model.Season;
import model.Team;
import model.TeamPlayer;

@RestController
public class PlayerStatisticController {

	@Autowired
	PlayerRepo pr;

	@Autowired
	SeasonRepo sr;

	@Autowired
	PlayerstatisticRepo psr;

	@Autowired
	TeamplayerRepo tpr;

	@Autowired
	TeamRepo tr;

	private Param param = new Param();

	@RequestMapping(value = "playerStatisic")
	public List<PlayerStatistic> getApiPlayerStatistic() {
		
		
		List<PlayerStatistic> listaPlayerStaticis = new ArrayList<PlayerStatistic>();

		List<Player> player = pr.findAll();

		String json = "";
		for (Player p : player) {

			HttpResponse<String> response;
			try {
				response = Unirest.get(param.getAdd() + "/players/player/" + p.getIdPlayer())
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
				Integer intigerFixturs = result.intValue();

				JSONArray nizPlayers = (JSONArray) o1.get("players");

				for (int i = 0; i < result; i++) {
					JSONObject objectPlayers = (JSONObject) nizPlayers.get(i);

					PlayerStatistic playerStatisic = new PlayerStatistic();

					// injured
					playerStatisic.setInjured((String) objectPlayers.get("injured"));

					// rating
					playerStatisic.setRating((String) objectPlayers.get("rating"));

					// leauge
					playerStatisic.setLeauge((String) objectPlayers.get("league"));

					// captain
					Long playerCaptionPom = (Long) objectPlayers.get("captain");
					Integer playerCaption = playerCaptionPom.intValue();
					playerStatisic.setCaptain(playerCaption);

					// shots
					JSONObject shot = (JSONObject) objectPlayers.get("shots");

					Long shotTotalPom = (Long) shot.get("total");
					Integer shotTotal = shotTotalPom.intValue();
					playerStatisic.setShotsTotal(shotTotal);

					Long shotOnPom = (Long) shot.get("on");
					Integer shotOn = shotOnPom.intValue();
					playerStatisic.setShotsOn(shotOn);

					// goals
					JSONObject goals = (JSONObject) objectPlayers.get("goals");

					Long goalsTotalpom = (Long) goals.get("total");
					Integer goalsTotal = goalsTotalpom.intValue();
					playerStatisic.setGoalsTotal(goalsTotal);

					Long goalsConcededpom = (Long) goals.get("conceded");
					Integer goalsConceded = goalsConcededpom.intValue();
					playerStatisic.setGoalsConceded(goalsConceded);

					Long goalAssistsPom = (Long) goals.get("assists");
					Integer goalAsists = goalAssistsPom.intValue();
					playerStatisic.setGoalsAssists(goalAsists);

					// passes
					JSONObject passes = (JSONObject) objectPlayers.get("passes");

					Long passesTotalPom = (Long) passes.get("total");
					Integer passesTotal = passesTotalPom.intValue();
					playerStatisic.setPassesTotal(passesTotal);

					Long passesKeyPom = (Long) passes.get("key");
					Integer passesKey = passesKeyPom.intValue();
					playerStatisic.setPassesKey(passesKey);

					Long passesaccuracyPom = (Long) passes.get("accuracy");
					Integer passesaccuracy = passesaccuracyPom.intValue();
					playerStatisic.setPassesAccuracy(passesaccuracy);

					// tackles
					JSONObject tackles = (JSONObject) objectPlayers.get("tackles");

					Long tacklesTotalPom = (Long) tackles.get("total");
					Integer tacklesTotal = tacklesTotalPom.intValue();
					playerStatisic.setTacklesTotal(tacklesTotal);

					Long tacklesblocksPom = (Long) tackles.get("blocks");
					Integer tacklesblocks = tacklesblocksPom.intValue();
					playerStatisic.setTacklesBlocks(tacklesblocks);

					Long tacklesinterceptionsPom = (Long) tackles.get("interceptions");
					Integer tacklesinterceptions = tacklesinterceptionsPom.intValue();
					playerStatisic.setTacklesInterecptions(tacklesinterceptions);

					// duels
					JSONObject duels = (JSONObject) objectPlayers.get("duels");

					Long duelstotalPom = (Long) duels.get("total");
					Integer duelsTotal = duelstotalPom.intValue();
					playerStatisic.setDuelsTotal(duelsTotal);

					Long duelswinPom = (Long) duels.get("won");
					Integer duelsWon = duelswinPom.intValue();
					playerStatisic.setDuelsWon(duelsWon);

					// dribbles
					JSONObject dribbles = (JSONObject) objectPlayers.get("dribbles");

					Long attemptsPom = (Long) dribbles.get("attempts");
					Integer attempts = attemptsPom.intValue();
					playerStatisic.setDribblesAttempts(attempts);

					Long successPom = (Long) dribbles.get("success");
					Integer success = successPom.intValue();
					playerStatisic.setDribblesSuccess(success);

					// fouls
					JSONObject fouls = (JSONObject) objectPlayers.get("fouls");

					Long foulsPom = (Long) fouls.get("drawn");
					Integer foulsInt = foulsPom.intValue();
					playerStatisic.setFoulsDrawn(foulsInt);

					Long foulscommittedPom = (Long) fouls.get("committed");
					Integer foulscommitted = foulscommittedPom.intValue();
					playerStatisic.setFoulsComitted(foulscommitted);

					// cards
					JSONObject cards = (JSONObject) objectPlayers.get("cards");

					Long yellowPom = (Long) cards.get("yellow");
					Integer yellow = yellowPom.intValue();
					playerStatisic.setCardsYellow(yellow);

					Long yellowredPom = (Long) cards.get("yellowred");
					Integer yellowred = yellowredPom.intValue();
					playerStatisic.setCardsYellowred(yellowred);

					Long redPom = (Long) cards.get("red");
					Integer red = redPom.intValue();
					playerStatisic.setCardsRed(red);

					// penalty
					JSONObject penalty = (JSONObject) objectPlayers.get("penalty");

					Long penaltyWonPom = (Long) penalty.get("won");
					Integer penaltyWo = penaltyWonPom.intValue();
					playerStatisic.setPenaltyWon(penaltyWo);

					Long penaltycommitedPom = (Long) penalty.get("commited");
					Integer penaltycommited = penaltycommitedPom.intValue();
					playerStatisic.setPenaltyCommited(penaltycommited);

					Long penaltysuccessPom = (Long) penalty.get("success");
					Integer penaltysuccess = penaltysuccessPom.intValue();
					playerStatisic.setPenaltySuccess(penaltysuccess);

					Long penaltymissedPom = (Long) penalty.get("missed");
					Integer penaltymissed = penaltymissedPom.intValue();
					playerStatisic.setPenaltyMissed(penaltymissed);

					Long penaltysavedPom = (Long) penalty.get("saved");
					Integer penaltysaved = penaltysavedPom.intValue();
					playerStatisic.setPenaltySaved(penaltysaved);

					// games
					JSONObject games = (JSONObject) objectPlayers.get("games");

					Long gamesappearencesPom = (Long) games.get("appearences");
					Integer gamesappearences = gamesappearencesPom.intValue();
					playerStatisic.setGamesAppearences(gamesappearences);

					Long gamesminutes_playedsPom = (Long) games.get("minutes_played");
					Integer gamesminutes_playeds = gamesminutes_playedsPom.intValue();
					playerStatisic.setGamesMinutesPlayed(gamesminutes_playeds);

					Long gameslineupsPom = (Long) games.get("lineups");
					Integer gameslineups = gameslineupsPom.intValue();
					playerStatisic.setGamesLinups(gameslineups);

					// substitutes
					JSONObject substitutes = (JSONObject) objectPlayers.get("substitutes");

					Long substitutesInPom = (Long) substitutes.get("in");
					Integer substitutesIn = substitutesInPom.intValue();
					playerStatisic.setSubstituesIn(substitutesIn);

					Long substitutesOutPom = (Long) substitutes.get("out");
					Integer substitutesOut = substitutesOutPom.intValue();
					playerStatisic.setSubstituesOut(substitutesOut);

					Long substitutesBenchPom = (Long) substitutes.get("bench");
					Integer substitutesBench = substitutesBenchPom.intValue();
					playerStatisic.setSubstituesBench(substitutesBench);

					// season
					String seasonPom = (String) objectPlayers.get("season");
					String[] t = seasonPom.split("");
					String season = t[0] + t[1] + t[2] + t[3];
					Integer seasonInt = Integer.parseInt(season);

					// team_id
					List<TeamPlayer> teamplayerList=new ArrayList<TeamPlayer>();
					Long teamPlayerIdPom = (Long) objectPlayers.get("team_id");
					if (teamPlayerIdPom != null) {
						Integer teamPlayerId = teamPlayerIdPom.intValue();
						Team team = tr.getOne(teamPlayerId);
						teamplayerList = tpr.findByTeam(team);
					}else {
						TeamPlayer teamPlayerNull=new TeamPlayer();
						teamPlayerNull.setPlayer(p);
						System.out.println(seasonInt);
						Season seasonPlayerNull=sr.getOne(seasonInt);
						teamPlayerNull.setSeason(seasonPlayerNull);
						//save baza
						teamPlayerNull=tpr.save(teamPlayerNull);
						playerStatisic.setTeamPlayer(teamPlayerNull);
					}
					// save baza

					

					for (TeamPlayer teamp : teamplayerList) {
						if (teamp.getPlayer().getIdPlayer() == p.getIdPlayer()
								&& seasonInt == teamp.getSeason().getSeason()) {
							playerStatisic.setTeamPlayer(teamp);

						}
					}
					psr.save(playerStatisic);

					listaPlayerStaticis.add(playerStatisic);

				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return listaPlayerStaticis;

	}

}
