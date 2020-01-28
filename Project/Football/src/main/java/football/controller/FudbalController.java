package football.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.json.HTTP;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.repository.AssistRepo;
import football.repository.AwayteamRepo;
import football.repository.BetRepo;
import football.repository.BookmakerRepo;
import football.repository.CountryRepo;
import football.repository.EventRepo;
import football.repository.FixtureRepo;
import football.repository.FixturestatRepo;
import football.repository.HometeamRepo;
import football.repository.LabelRepo;
import football.repository.LastfivestatRepo;
import football.repository.LeagueRepo;
import football.repository.LineupRepo;
import football.repository.OddRepo;
import football.repository.PlayerRepo;
import football.repository.PlayerfixstatRepo;
import football.repository.RoundRepo;
import football.repository.ScoreRepo;
import football.repository.StartxiRepo;
import football.repository.SubstituteRepo;
import football.repository.TeamRepo;
import football.repository.TeamplayerRepo;
import football.repository.TopscorerRepo;
import model.Assist;
import model.AwayTeam;
import model.Bet;
import model.Bookmaker;
import model.Country;
import model.Event;
import model.Fixture;
import model.FixtureStat;
import model.HomeTeam;
import model.Label;
import model.LastFiveStat;
import model.League;
import model.LineUp;
import model.Odd;
import model.Player;
import model.PlayerFixStat;
import model.Round;
import model.Score;
import model.StartXI;
import model.Substitute;
import model.Team;
import model.TeamPlayer;
import model.TopScorer;

@RestController
public class FudbalController {

	// update-ovanje baze za prosla 3 dana i narednih 14 dana

	private Param param = new Param();

	@Autowired
	OddRepo or;
	@Autowired
	BookmakerRepo bmr;
	@Autowired
	BetRepo betr;
	@Autowired
	LabelRepo lr;

	@Autowired
	AssistRepo assistRepo;

	@Autowired
	EventRepo eventRepo;

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
	PlayerfixstatRepo pfr;

	@Autowired
	TeamRepo teamRepo;

	@Autowired
	CountryRepo cr;

	@Autowired
	SubstituteRepo substituteRepo;

	@Autowired
	StartxiRepo startxiRepo;

	@Autowired
	LineupRepo lineUpRepo;

	@Autowired
	PlayerRepo playerRepo;

	@Autowired
	TeamplayerRepo teamplayerRepo;

	@Autowired
	FixturestatRepo fixtureStatRepo;

	@Autowired
	LastfivestatRepo lastFiveRepo;
	
	
	private Date date;

	@RequestMapping(value = "dateupdate")
	public void update17days() {
		List<String> dates = getDates();
		// List<String> dates = new ArrayList<String>();
		// dates.add("2020-05-17");
		// dates.add("2020-05-10");
		// dates.add("2020-05-09");
		List<Fixture> fixtures = apiFixturesDate(dates);

		ExecutorService executor = Executors.newFixedThreadPool(5);

		executor.execute(() -> {
			apiLineUpDate(fixtures);
		});
		executor.execute(() -> {
			apiFixStatDate(fixtures);
		});
		executor.execute(() -> {
			apiPlayerFixStatDate(fixtures);
		});
		executor.execute(() -> {
			Date now = new Date();
			if (!date.equals(now)) {
				date = now;
				apiOddsDate(fixtures);
			}
		});
		executor.execute(() -> {
			apiLastFiveStatisics(fixtures);
		});
	}

	public List<String> getDates() {
		List<String> rez = new ArrayList<String>();
		for (int i = -3; i < 14; i++) {
			String s;
			Date d = new Date(System.currentTimeMillis() - i * 24 * 60 * 60 * 1000);
			if (d.getDay() > 9) {
				s = 1900 + d.getYear() + "-" + d.getMonth() + "-" + d.getDay();
			} else {
				s = 1900 + d.getYear() + "-" + d.getMonth() + "-0" + d.getDay();
			}
			rez.add(s);
			System.out.println(s);
		}
		return rez;
	}

	public List<Fixture> apiFixturesDate(List<String> dates) {

		List<Fixture> retFixture = new ArrayList<Fixture>();
		for (String date : dates) {

			HttpResponse<String> response = null;
			try {
				response = Unirest.get(param.getAdd() + "/fixtures/date/" + date)
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

				JSONArray n1 = (JSONArray) o1.get("fixtures");

				for (int i = 0; i < n1.size(); i++) {

					Fixture fixture = new Fixture();
					JSONObject o2 = (JSONObject) n1.get(i);

					// Fixtureeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

					Object idLongFixture = o2.get("fixture_id");
					Integer idFixture = idLongFixture instanceof Long ? ((Long) idLongFixture).intValue() : 0;
					fixture.setIdFixtures(idFixture);

					Object eventTimeStampLong = o2.get("event_timestamp");
					Integer eventTimeStamp = eventTimeStampLong instanceof Long ? ((Long) eventTimeStampLong).intValue()
							: 0;
					fixture.setEventTimeStamp(eventTimeStamp);

					Object firstHalfStartLong = o2.get("firstHalfStart");
					Integer firstHalfStart = firstHalfStartLong instanceof Long ? ((Long) firstHalfStartLong).intValue()
							: 0;
					fixture.setFristHalfStart(firstHalfStart);

					Object secondtHalfStartLong = o2.get("secondHalfStart");
					Integer secondHalfStart = secondtHalfStartLong instanceof Long
							? ((Long) secondtHalfStartLong).intValue()
							: 0;
					fixture.setSecondHalfStart(secondHalfStart);

					Object elapsedLong = o2.get("elapsed");
					Integer elapsed = elapsedLong instanceof Long ? ((Long) elapsedLong).intValue() : 0;
					fixture.setElapsed(elapsed);

					Object goalsHomeTeamLong = o2.get("goalsHomeTeam");
					Integer goalsHomeTeam = goalsHomeTeamLong instanceof Long ? ((Long) goalsHomeTeamLong).intValue()
							: 0;
					fixture.setGoalsHomeTeam(goalsHomeTeam);

					Object goalsAwayTeamLong = o2.get("goalsAwayTeam");
					Integer goalsAwayTeam = goalsAwayTeamLong instanceof Long ? ((Long) goalsAwayTeamLong).intValue()
							: 0;
					fixture.setGoalsAwayTeam(goalsAwayTeam);

					fixture.setEventDate((Date) o2.get("event_date") == null ? null : (Date) o2.get("event_date"));
					fixture.setStatus((String) o2.get("status") == null ? null : (String) o2.get("status"));
					fixture.setStatusShort(
							(String) o2.get("statusShort") == null ? null : (String) o2.get("statusShort"));
					fixture.setVenue((String) o2.get("venue") == null ? null : (String) o2.get("venue"));
					fixture.setReferee((String) o2.get("referee") == null ? null : (String) o2.get("referee"));

					// Rounddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd

					Object idLeagueLong = o2.get("league_id");
					Integer idLeague = idLeagueLong instanceof Long ? ((Long) idLeagueLong).intValue() : 0;

					League league = leagueRepo.getOne(idLeague);
					List<Round> rounds = roundRepo.findByLeague(league);

					for (Round r : rounds) {
						if (r.getReguralSeason().equals(o2.get("round"))) {
							fixture.setRound(r);
						}
					}

					// Home away
					// teammmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm

					JSONObject o3 = (JSONObject) o2.get("homeTeam");
					JSONObject o4 = (JSONObject) o2.get("awayTeam");

					HomeTeam homeTeam = new HomeTeam();
					AwayTeam awayTeam = new AwayTeam();

					Object idHomeTeamLong = o3.get("team_id");
					Integer idHomeTeam = idHomeTeamLong instanceof Long ? ((Long) idHomeTeamLong).intValue() : 0;

					Team team = teamRepo.getOne(idHomeTeam);
					// HomeTeam htB = homeTeamRepo.findByTeam(team); // HomeTeam iz baze
					// if (htB == null) {
					homeTeam.setTeam(team);
					homeTeam = homeTeamRepo.save(homeTeam);
					fixture.setHomeTeam(homeTeam);
					// } else {
					// fixture.setHomeTeam(htB);
					// }
					Object idAwayTeamLong = o4.get("team_id");
					Integer idAwayTeam = idAwayTeamLong instanceof Long ? ((Long) idAwayTeamLong).intValue() : 0;

					team = teamRepo.getOne(idAwayTeam);

					// AwayTeam atB = awayTeamRepo.findByTeam(team); // AwayTeam iz baze
					// if (atB == null) {
					awayTeam.setTeam(team);
					awayTeam = awayTeamRepo.save(awayTeam);
					fixture.setAwayTeam(awayTeam);
					// } else {
					// fixture.setAwayTeam(atB);
					// }

					JSONObject o5 = (JSONObject) o2.get("score");

					// Scoreeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

					Score score = new Score();

					score.setHalfTime((String) o5.get("halftime") == null ? null : (String) o5.get("halftime"));

					score.setFullTime((String) o5.get("fulltime") == null ? null : (String) o5.get("fulltime"));

					score.setExtraTime((String) o5.get("extratime") == null ? null : (String) o5.get("extratime"));

					score.setPenalty((String) o5.get("penalty") == null ? null : (String) o5.get("penalty"));

					score = scoreRepo.save(score);
					fixture.setScore(score);

					fixture = fixtureRepo.save(fixture);

					retFixture.add(fixture);

				}

			} catch (ParseException e) {

				e.printStackTrace();
			}

		}
		System.out.println(retFixture);
		int j = 1;
		for (Fixture f : retFixture) {
			System.out.println("****************");
			System.out.println(j + ". " + f.getIdFixtures() + "  " + f.getHomeTeam().getTeam().getTeamName() + " - "
					+ f.getAwayTeam().getTeam().getTeamName());
			j++;
		}
		return retFixture;
	}

	// dodavanje lineUp-a za date meceve
	public void apiLineUpDate(List<Fixture> fixtures) {

		for (Fixture f : fixtures) {

			List<LineUp> lineUpDelete = lineUpRepo.findByFixture(f);

			for (LineUp l : lineUpDelete) {
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

				JSONObject tim1 = (JSONObject) o2.get(f.getHomeTeam().getTeam().getTeamName());
				LineUp lineup = new LineUp();

				String formacija = (String) tim1.get("formation");
				lineup.setFormation(formacija);

				lineup.setTeam(f.getHomeTeam().getTeam());

				lineup.setFixture(f);

				lineup = lineUpRepo.save(lineup);

				// Tim 2 ++++++++++++++

				JSONObject tim2 = (JSONObject) o2.get(f.getAwayTeam().getTeam().getTeamName());
				lineup = new LineUp();

				formacija = (String) tim2.get("formation");
				lineup.setFormation(formacija);

				lineup.setTeam(f.getAwayTeam().getTeam());

				lineup.setFixture(f);

				lineup = lineUpRepo.save(lineup);

				// Tim 1 +++++++++++
				JSONArray n = (JSONArray) tim1.get("startXI");

				for (int i = 0; i < n.size(); i++) {

					JSONObject objekatUNizu = (JSONObject) n.get(i);

					StartXI startxi = new StartXI();

					Object playerIdLong = objekatUNizu.get("player_id");
					Integer playerId = playerIdLong instanceof Long ? ((Long) playerIdLong).intValue() : 0;

					Player player = playerRepo.getOne(playerId);
					List<TeamPlayer> pomList = teamplayerRepo.findByPlayer(player);

					for (TeamPlayer tp : pomList) {

						if (tp.getSeason().getSeason() == f.getRound().getLeague().getSeasonBean().getSeason()) {
							startxi.setTeamPlayer(tp);
						}

					}
					startxi.setLineUp(lineup);

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
					List<TeamPlayer> pomList2 = teamplayerRepo.findByPlayer(player);

					for (TeamPlayer tp : pomList2) {

						if (tp.getSeason().getSeason() == f.getRound().getLeague().getSeasonBean().getSeason()) {
							substitutes.setTeamPlayer(tp);
						}
					}
					substitutes.setLineUp(lineup);
					substitutes = substituteRepo.save(substitutes);
				}

				// Tim 2 +++++++++++++++++

				JSONArray n2 = (JSONArray) tim2.get("startXI");

				for (int i = 0; i < n2.size(); i++) {

					JSONObject objekatUNizu = (JSONObject) n2.get(i);

					StartXI startxi = new StartXI();

					Object playerIdLong = objekatUNizu.get("player_id");
					Integer playerId = playerIdLong instanceof Long ? ((Long) playerIdLong).intValue() : 0;

					Player player = playerRepo.getOne(playerId);
					List<TeamPlayer> pomList = teamplayerRepo.findByPlayer(player);

					for (TeamPlayer tp : pomList) {

						if (tp.getSeason().getSeason() == f.getRound().getLeague().getSeasonBean().getSeason()) {
							startxi.setTeamPlayer(tp);
						}

					}
					startxi.setLineUp(lineup);

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
					List<TeamPlayer> pomList2 = teamplayerRepo.findByPlayer(player);

					for (TeamPlayer tp : pomList2) {

						if (tp.getSeason().getSeason() == f.getRound().getLeague().getSeasonBean().getSeason()) {
							substitutes.setTeamPlayer(tp);
						}
					}
					substitutes.setLineUp(lineup);
					substitutes = substituteRepo.save(substitutes);
				}

			} catch (ParseException e) {

				e.printStackTrace();
			}
		}

	}

	// dodavanje statistike za date meceve
	public void apiFixStatDate(List<Fixture> fixtures) {

		List<FixtureStat> retfixtureStats = new ArrayList<FixtureStat>();
		for (Fixture f : fixtures) {
			HttpResponse<String> response = null;
			try {
				response = Unirest.get(param.getAdd() + "/statistics/fixture/" + f.getIdFixtures())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
			} catch (UnirestException e1) {
				e1.printStackTrace();
			}

			String json = response.getBody();

			JSONParser parse = new JSONParser();
			JSONObject o;

			List<FixtureStat> deleteStat = fixtureStatRepo.findByFixture(f);
			for (FixtureStat ds : deleteStat) {
				fixtureStatRepo.delete(ds);
			}
			try {

				o = (JSONObject) parse.parse(json);
				JSONObject o1 = (JSONObject) o.get("api");

				System.out.println("Mecevi " + f.getIdFixtures());

				JSONObject o2;

				if (o1.get("statistics") instanceof JSONObject) {
					o2 = (JSONObject) o1.get("statistics");

					// Sutevi na golllllllllllllllllllllllllllllllllllllllllll

					FixtureStat shotOnGoal = new FixtureStat();

					JSONObject o3 = (JSONObject) o2.get("Shots on Goal");

					shotOnGoal.setFixtureStatName("Shots on Goal");

					String home = (String) o3.get("home");
					shotOnGoal.setHome(home == null ? null : home);

					String away = (String) o3.get("away");
					shotOnGoal.setAway(away == null ? null : away);

					shotOnGoal.setFixture(f);

					retfixtureStats.add(shotOnGoal);

					// Sutevi pored golaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

					FixtureStat shotOffGoal = new FixtureStat();

					JSONObject o4 = (JSONObject) o2.get("Shots off Goal");

					shotOffGoal.setFixtureStatName("Shots off Goal");

					home = (String) o4.get("home");
					shotOffGoal.setHome(home == null ? null : home);

					away = (String) o4.get("away");
					shotOffGoal.setAway(away == null ? null : away);

					shotOffGoal.setFixture(f);

					retfixtureStats.add(shotOffGoal);

					// Ukupno
					// suteviiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii

					FixtureStat totalShots = new FixtureStat();

					JSONObject o5 = (JSONObject) o2.get("Total Shots");

					totalShots.setFixtureStatName("Total Shots");

					home = (String) o5.get("home");
					totalShots.setHome(home == null ? null : home);

					away = (String) o5.get("away");
					totalShots.setAway(away == null ? null : away);

					totalShots.setFixture(f);

					retfixtureStats.add(totalShots);

					// Blokirani suteviiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii

					FixtureStat blockedShots = new FixtureStat();

					JSONObject o6 = (JSONObject) o2.get("Blocked Shots");

					blockedShots.setFixtureStatName("Blocked Shots");

					home = (String) o6.get("home");
					blockedShots.setHome(home == null ? null : home);

					away = (String) o6.get("away");
					blockedShots.setAway(away == null ? null : away);

					blockedShots.setFixture(f);

					retfixtureStats.add(blockedShots);

					// Shots
					// insideboxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

					FixtureStat shotInsideBox = new FixtureStat();

					JSONObject o7 = (JSONObject) o2.get("Shots insidebox");

					shotInsideBox.setFixtureStatName("Shots insidebox");

					home = (String) o7.get("home");
					shotInsideBox.setHome(home == null ? null : home);

					away = (String) o7.get("away");
					shotInsideBox.setAway(away == null ? null : away);

					shotInsideBox.setFixture(f);

					retfixtureStats.add(shotInsideBox);

					// Shots
					// outsideboxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

					FixtureStat shotOutsideBox = new FixtureStat();

					JSONObject o8 = (JSONObject) o2.get("Shots outsidebox");

					shotOutsideBox.setFixtureStatName("Shots outsidebox");

					home = (String) o8.get("home");
					shotOutsideBox.setHome(home == null ? null : home);

					away = (String) o8.get("away");
					shotOutsideBox.setAway(away == null ? null : away);

					shotOutsideBox.setFixture(f);

					retfixtureStats.add(shotOutsideBox);

					// Fauloviiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii

					FixtureStat fouls = new FixtureStat();

					JSONObject o9 = (JSONObject) o2.get("Fouls");

					fouls.setFixtureStatName("Fouls");

					home = (String) o9.get("home");
					fouls.setHome(home == null ? null : home);

					away = (String) o9.get("away");
					fouls.setAway(away == null ? null : away);

					fouls.setFixture(f);

					retfixtureStats.add(fouls);

					// Korneriiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii

					FixtureStat cornerKicks = new FixtureStat();

					JSONObject o10 = (JSONObject) o2.get("Corner Kicks");

					cornerKicks.setFixtureStatName("Corner Kicks");

					home = (String) o10.get("home");
					cornerKicks.setHome(home == null ? null : home);

					away = (String) o10.get("away");
					cornerKicks.setAway(away == null ? null : away);

					cornerKicks.setFixture(f);

					retfixtureStats.add(cornerKicks);

					// Ofsajdiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii

					FixtureStat offsides = new FixtureStat();

					JSONObject o11 = (JSONObject) o2.get("Offsides");

					offsides.setFixtureStatName("Offsides");

					home = (String) o11.get("home");
					offsides.setHome(home == null ? null : home);

					away = (String) o11.get("away");
					offsides.setAway(away == null ? null : away);

					offsides.setFixture(f);

					retfixtureStats.add(offsides);

					// Posed
					// lopteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

					FixtureStat ballPossession = new FixtureStat();

					JSONObject o12 = (JSONObject) o2.get("Ball Possession");

					ballPossession.setFixtureStatName("Ball Possession");

					home = (String) o12.get("home");
					ballPossession.setHome(home == null ? null : home);

					away = (String) o12.get("away");
					ballPossession.setAway(away == null ? null : away);

					ballPossession.setFixture(f);

					retfixtureStats.add(ballPossession);

					// Zuti
					// kartoniiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii

					FixtureStat yellowCards = new FixtureStat();

					JSONObject o13 = (JSONObject) o2.get("Yellow Cards");

					yellowCards.setFixtureStatName("Yellow Cards");

					home = (String) o13.get("home");
					yellowCards.setHome(home == null ? null : home);

					away = (String) o13.get("away");
					yellowCards.setAway(away == null ? null : away);

					yellowCards.setFixture(f);

					retfixtureStats.add(yellowCards);

					// Crveni
					// kartoniiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii

					FixtureStat redCards = new FixtureStat();

					JSONObject o14 = (JSONObject) o2.get("Red Cards");

					redCards.setFixtureStatName("Red Cards");

					home = (String) o14.get("home");
					redCards.setHome(home == null ? null : home);

					away = (String) o14.get("away");
					redCards.setAway(away == null ? null : away);

					redCards.setFixture(f);

					retfixtureStats.add(redCards);

					// Odbraneeeeee golmanaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

					FixtureStat goalkeeperSaves = new FixtureStat();

					JSONObject o15 = (JSONObject) o2.get("Goalkeeper Saves");

					goalkeeperSaves.setFixtureStatName("Goalkeeper Saves");

					home = (String) o15.get("home");
					goalkeeperSaves.setHome(home == null ? null : home);

					away = (String) o15.get("away");
					goalkeeperSaves.setAway(away == null ? null : away);

					goalkeeperSaves.setFixture(f);

					retfixtureStats.add(goalkeeperSaves);

					// Ukupnoo pasovaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

					FixtureStat totalPasses = new FixtureStat();

					JSONObject o16 = (JSONObject) o2.get("Total passes");

					totalPasses.setFixtureStatName("Total passes");

					home = (String) o16.get("home");
					totalPasses.setHome(home == null ? null : home);

					away = (String) o16.get("away");
					totalPasses.setAway(away == null ? null : away);

					totalPasses.setFixture(f);

					retfixtureStats.add(totalPasses);

					// Passes
					// accurateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

					FixtureStat passesAccurate = new FixtureStat();

					JSONObject o17 = (JSONObject) o2.get("Passes accurate");

					passesAccurate.setFixtureStatName("Passes accurate");

					home = (String) o17.get("home");
					passesAccurate.setHome(home == null ? null : home);

					away = (String) o17.get("away");
					passesAccurate.setAway(away == null ? null : away);

					passesAccurate.setFixture(f);

					retfixtureStats.add(passesAccurate);

					// Passes %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

					FixtureStat passes = new FixtureStat();

					JSONObject o18 = (JSONObject) o2.get("Passes %");

					passes.setFixtureStatName("Passes %");

					home = (String) o18.get("home");
					passes.setHome(home == null ? null : home);

					away = (String) o18.get("away");
					passes.setAway(away == null ? null : away);

					passes.setFixture(f);

					retfixtureStats.add(passes);
				}

				else {
					System.out.println("Ovde preskacem");
					continue;
				}

			} catch (ParseException e) {

				e.printStackTrace();
			}

		}
		fixtureStatRepo.saveAll(retfixtureStats);
	}

	public List<PlayerFixStat> apiPlayerFixStatDate(List<Fixture> fixtures) {
		// pfr.deleteAll();
		List<PlayerFixStat> listPlayerFixStat = new ArrayList<PlayerFixStat>();

		String json = "";

		HttpResponse<String> response;
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
					Team team = teamRepo.getOne(teamPlayerIdPom.intValue());

					List<TeamPlayer> teamPlayerList = teamplayerRepo.findByTeam(team);

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

	public void apiOddsDate(List<Fixture> fs) {
		System.out.println("ODDS UPDATE");
		String json = null;

		for (Fixture f : fs) {

			try {
				HttpResponse<String> response = Unirest.get(param.getAdd() + "/odds/fixture/" + f.getIdFixtures())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
				json = response.getBody();
				System.out.println(f.getIdFixtures());

			} catch (UnirestException e) {
				e.printStackTrace();
			}

			if (json != null) {

				JSONParser parse = new JSONParser();
				JSONObject o;
				try {
					o = (JSONObject) parse.parse(json);

					JSONObject o1 = (JSONObject) o.get("api");

					Long result = (Long) o1.get("results");
					Integer br = result.intValue();

					JSONArray odds = (JSONArray) o1.get("odds");
					for (int i = 0; i < result; i++) {
						JSONObject objectOdds = (JSONObject) odds.get(i);
						// da ne ubacuje dva puta isto
						Odd oddDelete = or.findByFixture(f);
						if (oddDelete != null)
							or.delete(oddDelete);
						Odd odd = new Odd();
						odd.setFixture(f);
						odd = or.save(odd);

						JSONArray bookmakers = (JSONArray) objectOdds.get("bookmakers");

						for (int j = 0; j < bookmakers.size(); j++) {
							JSONObject bm = (JSONObject) bookmakers.get(j);
							Long idBm = (Long) bm.get("bookmaker_id");
							Bookmaker bookmaker = bmr.getOne(idBm.intValue());
							JSONArray labels = (JSONArray) bm.get("bets");
							for (int z = 0; z < labels.size(); z++) {
								JSONObject lb = (JSONObject) labels.get(z);
								Long idLb = (Long) lb.get("label_id");
								Label label = lr.getOne(idLb.intValue());

								JSONArray bets = (JSONArray) lb.get("values");

								for (int y = 0; y < bets.size(); y++) {
									JSONObject bet = (JSONObject) bets.get(y);
									Bet b = new Bet();
									b.setOddBean(odd);
									b.setLabel(label);
									b.setBookmaker(bookmaker);
									Object betValue = (Object) bet.get("value");
									Object betOdds = (Object) bet.get("value");
									if (betValue instanceof Long) {
										Long lbv = (Long) betValue;
										String betValueStr = betValue + "";
										b.setBetValues(betValueStr);
									} else {
										String betValueStr = (String) betValue;
										b.setBetValues(betValueStr);
									}
									if (betOdds instanceof Long) {
										Long lo = (Long) betValue;
										String bos = lo + "";
										b.setOdd(bos);
									} else {
										String bos = (String) betValue;
										b.setOdd(bos);
									}

									betr.save(b);
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

	// dodavanje Last Five po date meceve
	public void apiLastFiveStatisics(List<Fixture> listaFixutres) {

		List<LastFiveStat> listaLastFiveStat = new ArrayList<LastFiveStat>();

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

					Team teamPom = teamRepo.getOne(teamId);
					if (teamPom != null) {
						lastFiveStatistics.setTeam(teamPom);
					}

					lastFiveRepo.save(lastFiveStatistics);

					listaLastFiveStat.add(lastFiveStatistics);
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	}

	
	

	public void apiEventDate(List<Fixture> fixtures) {

		for (Fixture f : fixtures) {

			List<Event> retEvent = new ArrayList<Event>();

			List<Event> eventDelete = eventRepo.findByFixture(f);

			for (Event e : eventDelete) {
				eventRepo.delete(e);
			}

			System.out.println("Mecevi " + f.getIdFixtures());

			HttpResponse<String> response = null;
			try {
				response = Unirest.get(param.getAdd() + "/events/" + f.getIdFixtures())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
			} catch (UnirestException e1) {
				e1.printStackTrace();
			}

			String json = response.getBody();

			JSONParser parse = new JSONParser();
			JSONObject o;

			Event event = new Event();

			try {
				o = (JSONObject) parse.parse(json);
				JSONObject o1 = (JSONObject) o.get("api");

				JSONArray n1 = (JSONArray) o1.get("events");

				for (int i = 0; i < n1.size(); i++) {

					JSONObject o2 = (JSONObject) n1.get(i);

					Object elapsedLong = o2.get("elapsed");
					Integer elapsed = elapsedLong instanceof Long ? ((Long) elapsedLong).intValue() : 0;
					event.setElapsed(elapsed);

					event.setType((String) o2.get("type") == null ? null : (String) o2.get("type"));

					event.setDetail((String) o2.get("detail") == null ? null : (String) o2.get("detail"));

					event.setFixture(f);

					Object playerLong = o2.get("player_id");
					Integer playerId = playerLong instanceof Long ? ((Long) playerLong).intValue() : 0;

					Player player = playerRepo.getOne(playerId);

					List<TeamPlayer> teamplayers = teamplayerRepo.findByPlayer(player);

					for (TeamPlayer tp : teamplayers) {
						if (tp.getSeason().getSeason() == f.getRound().getLeague().getSeasonBean().getSeason()) {
							event.setTeamPlayer(tp);
						}
					}

					Object playerAssistLong = o2.get("assist_id");

					Integer playerAssistId = playerAssistLong instanceof Long ? ((Long) playerAssistLong).intValue()
							: 0;

					Assist assist = new Assist();

					player = playerRepo.getOne(playerAssistId);

					teamplayers = teamplayerRepo.findByPlayer(player);

					for (TeamPlayer tp : teamplayers) {

						if (tp.getSeason().getSeason() == f.getRound().getLeague().getSeasonBean().getSeason()) {

							assist.setTeamPlayer(tp);
							assistRepo.save(assist);

							event.setAssist(assist);
						} else {
							assist.setTeamPlayer(null);
							assistRepo.save(assist);
							event.setAssist(assist);
						}

					}

					retEvent.add(event);
					eventRepo.saveAll(retEvent);

				}

			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

	}
}
