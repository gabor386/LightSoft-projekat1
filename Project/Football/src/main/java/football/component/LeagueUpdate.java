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
import football.repository.CountryRepo;
import football.repository.LeagueRepo;
import football.repository.PlayerRepo;
import football.repository.PlayerstatisticRepo;
import football.repository.RoundRepo;
import football.repository.SeasonRepo;
import football.repository.TeamRepo;
import football.repository.TeamplayerRepo;
import football.repository.TopscorerRepo;
import model.Country;
import model.League;
import model.Player;
import model.PlayerStatistic;
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

	@Autowired
	PlayerRepo pr;

	@Autowired
	PlayerstatisticRepo psr;

	private Date date;

	@Scheduled(cron = "0 0 0 * * ?")
	public void update() {
		int y = getYear();
		Season s = getSeason(y);
		List<League> ls = apiLeague(s);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		ExecutorService executorService = Executors.newFixedThreadPool(4);

		// Kreira posebnu novu nitttt
		executorService.execute(() -> {
			apiRaund(ls);
		});

		executorService.execute(() -> {
			apiTopScorrer(ls);
		});

		executorService.execute(() -> {
			Date d = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			int i = Integer.parseInt(formatter.format(d));

			int j = Integer.parseInt(formatter.format(date));

			if (j == i || date == null) {

				date = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
				apiTeam(ls);
			}
		});

		executorService.execute(() -> {
			getApiPlayerStatistic(s);
		});

		// jos standing i statistic team
	}

	public int getYear() {
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		int i = Integer.parseInt(formatter.format(d));

		System.out.println("Obradjujem godinu " + i);

		return i;
	}

	public Season getSeason(int s) {

		Season season = sr.getOne(s);
		return season;

	}

	public List<League> apiLeague(Season se) {

		String json = null;
		List<League> ls = new ArrayList<League>();

		try {
			HttpResponse<String> response = Unirest.get(param.getAdd() + "/leagues/season/" + se.getSeason())
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

					l = lr.save(l);
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
				json = null;
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

	public void apiTopScorrer(List<League> league) {

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

				List<TopScorer> tsd = topScorrerRepo.findByLeague(l);

				for (TopScorer dts : tsd) {
					topScorrerRepo.delete(dts);
				}

				JSONParser parse = new JSONParser();
				JSONObject o;

				o = (JSONObject) parse.parse(json);

				JSONObject o1 = (JSONObject) o.get("api");

				Long result = (Long) o1.get("results");

				JSONArray nizPlayers = (JSONArray) o1.get("topscorers");
				for (int i = 0; i < nizPlayers.size(); i++) {
					JSONObject objectPlayers = (JSONObject) nizPlayers.get(i);

					TopScorer topScorer = new TopScorer();

					// player
					Long playerIdPom = (Long) objectPlayers.get("player_id");
					Integer playerId = playerIdPom.intValue();

					// league
					topScorer.setLeague(l);

					// team
					Long teamPom = (Long) objectPlayers.get("team_id");
					Team team = teamRepo.getOne(teamPom.intValue());

					List<TeamPlayer> teamPlayerList = teamplayerRepo.findByTeam(team);

					for (TeamPlayer teamP : teamPlayerList) {
						if (teamP.getPlayer().getIdPlayer() == playerId) {
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

	public void apiTeam(List<League> leagues) {

		for (League l : leagues) {

			HttpResponse<String> response = null;
			try {
				response = Unirest.get(param.getAdd() + "/teams/league/" + l.getIdLeague())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
			} catch (UnirestException e1) {
				e1.printStackTrace();
			}

			String json = response.getBody();

			JSONParser parse = new JSONParser();
			JSONObject o;

			Team team = new Team();

			try {
				o = (JSONObject) parse.parse(json);
				JSONObject o1 = (JSONObject) o.get("api");

				JSONArray n1 = (JSONArray) o1.get("teams");

				for (int i = 0; i < n1.size(); i++) {

					JSONObject o2 = (JSONObject) n1.get(i);

					Object teamIdLong = o2.get("team_id");
					Integer teamId = teamIdLong instanceof Long ? ((Long) teamIdLong).intValue() : 0;
					team.setIdTeam(teamId);

					Object teamFoundedLong = o2.get("founded");
					Integer founded = teamFoundedLong instanceof Long ? ((Long) teamFoundedLong).intValue() : 0;
					team.setFounded(founded);

					Object teamCapacityLong = o2.get("venue_capacity");
					Integer teamCapacity = teamCapacityLong instanceof Long ? ((Long) teamCapacityLong).intValue() : 0;
					team.setCapacity(teamCapacity);

					String teamAddress = (String) o2.get("venue_address") == null ? null
							: (String) o2.get("venue_address");
					team.setStadionAddress(teamAddress);

					String teamN = (String) o2.get("name") == null ? null : (String) o2.get("name");
					team.setTeamName(teamN);

					String logo = (String) o2.get("logo") == null ? null : (String) o2.get("logo");
					team.setLogo(logo);

					String stadionN = (String) o2.get("venue_name") == null ? null : (String) o2.get("venue_name");
					team.setStadionName(stadionN);

					String stadionCity = (String) o2.get("venue_city") == null ? null : (String) o2.get("venue_city");
					team.setStadionCity(stadionCity);

					String stadionSurface = (String) o2.get("venue_surface") == null ? null
							: (String) o2.get("venue_surface");
					team.setStadionSurface(stadionSurface);

					String nameCountryTeam = (String) o2.get("country");

					String nameC = nameCountryTeam != null ? nameCountryTeam : null;

					Country country = cr.findByName(nameC);
					team.setCountry(country);

					teamRepo.save(team);

				}

			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

	}

	public List<PlayerStatistic> getApiPlayerStatistic(Season s) {

		List<PlayerStatistic> listaPlayerStaticis = new ArrayList<PlayerStatistic>();

		List<Player> player = pr.findAll();

		String json = "";
		for (Player p : player) {

			HttpResponse<String> response;
			try {
				response = Unirest.get(param.getAdd() + "/players/player/" + p.getIdPlayer() + "/" + s.getSeason())
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

					// team_id
					List<TeamPlayer> teamplayerList = teamplayerRepo.findByPlayerAndSeason(p, s);

					if (teamplayerList.size() > 0) {
						for (TeamPlayer tp : teamplayerList) {
							List<PlayerStatistic> psd = psr.findByTeamPlayer(tp);
							for (PlayerStatistic ps : psd) {
								psr.delete(ps);
							}
						}
						playerStatisic.setTeamPlayer(teamplayerList.get(0));
					} else {
						TeamPlayer teamPlayerNull = new TeamPlayer();
						teamPlayerNull.setPlayer(p);
						Long teamPlayerIdPom = (Long) objectPlayers.get("team_id");
						if (teamPlayerIdPom != null) {
							Integer teamPlayerId = teamPlayerIdPom.intValue();
							Team team = teamRepo.getOne(teamPlayerId);
							teamPlayerNull.setTeam(team);
						} else {
							teamPlayerNull.setTeam(null);
						}
						teamPlayerNull.setSeason(s);
						// save baza
						teamPlayerNull = teamplayerRepo.save(teamPlayerNull);
						playerStatisic.setTeamPlayer(teamPlayerNull);

					}

					playerStatisic = psr.save(playerStatisic);

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
