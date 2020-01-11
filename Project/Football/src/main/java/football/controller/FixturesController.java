package football.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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
import model.AwayTeam;
import model.Fixture;
import model.HomeTeam;
import model.League;
import model.Round;
import model.Score;
import model.Team;
import modelA.FixtureA;

@RestController
@RequestMapping(value = "/fixtureController")
public class FixturesController {

	private Param param = new Param();

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

	@RequestMapping(value = "/saveFixture", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public void apiFixtures() {

		List<League> leagues = leagueRepo.findAll();

		for (League l : leagues) {

			List<Fixture> retFixture = new ArrayList<Fixture>();

			HttpResponse<String> response = null;
			try {
				response = Unirest.get(param.getAdd() + "/fixtures/league/" + l.getIdLeague())
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

					fixture.setEventDate((String) o2.get("event_date") == null ? null : (String) o2.get("event_date"));
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

					homeTeam.setTeam(team);
					homeTeam = homeTeamRepo.save(homeTeam);

					Object idAwayTeamLong = o4.get("team_id");
					Integer idAwayTeam = idAwayTeamLong instanceof Long ? ((Long) idAwayTeamLong).intValue() : 0;

					team = teamRepo.getOne(idAwayTeam);

					awayTeam.setTeam(team);
					awayTeam = awayTeamRepo.save(awayTeam);

					fixture.setHomeTeam(homeTeam);
					fixture.setAwayTeam(awayTeam);

					JSONObject o5 = (JSONObject) o2.get("score");

					// Scoreeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

					Score score = new Score();

					score.setHalfTime((String) o5.get("halftime") == null ? null : (String) o5.get("halftime"));

					score.setFullTime((String) o5.get("fulltime") == null ? null : (String) o5.get("fulltime"));

					score.setExtraTime((String) o5.get("extratime") == null ? null : (String) o5.get("extratime"));

					score.setPenalty((String) o5.get("penalty") == null ? null : (String) o5.get("penalty"));

					score = scoreRepo.save(score);
					fixture.setScore(score);

					fixture=fixtureRepo.save(fixture);
					retFixture.add(fixture);
					

				} 

			} catch (ParseException e) {

				e.printStackTrace();
			}

		}
	}
	
	@RequestMapping(value = "/getNsFixtures/{idLeague}", method = RequestMethod.GET)
	public List<FixtureA> getNsFixtures (@PathVariable Integer idLeague) {
		League l = leagueRepo.getOne(idLeague);
		List<Round> rs= roundRepo.findByLeague(l);
		List<FixtureA> fixtures = new ArrayList<FixtureA>();
		for (Round r:rs) {
			List<Fixture> fs = fixtureRepo.findByRound(r);
			for (Fixture f:fs) {
				if (f.getStatusShort().equals("NS") ) {
				
					int idFixture = f.getIdFixtures();
					String date = f.getEventDate().split("T")[0];
					int idRound = f.getRound().getIdRound();
					String round = f.getRound().getReguralSeason();
					int idHomeTeam = f.getHomeTeam().getTeam().getIdTeam();
					String homeTeam = f.getHomeTeam().getTeam().getTeamName();
					String homeLogo = f.getHomeTeam().getTeam().getLogo();
					int idAwayTeam = f.getAwayTeam().getTeam().getIdTeam();
					String awayTeam = f.getAwayTeam().getTeam().getTeamName();
					String awayLogo = f.getAwayTeam().getTeam().getLogo();
					String score = "Not started";
					String status = f.getStatus();
					String statusShort = f.getStatusShort();
					
					FixtureA fA = new FixtureA(idFixture, date, idRound, round, idHomeTeam, homeTeam, homeLogo, idAwayTeam, awayTeam, awayLogo, score, status, statusShort);
			
					fixtures.add(fA);
				}
			}
		}
		return fixtures;
	}
	
	@RequestMapping(value = "/getFixtures/{idLeague}", method = RequestMethod.GET)
	public List<FixtureA> getFixtures (@PathVariable Integer idLeague) {
		League l = leagueRepo.getOne(idLeague);
		List<Round> rs= roundRepo.findByLeague(l);
		List<FixtureA> fixtures = new ArrayList<FixtureA>();
		for (Round r:rs) {
			List<Fixture> fs = fixtureRepo.findByRound(r);
			for (Fixture f:fs) {
				if (!f.getStatusShort().equals("NS") && !f.getStatusShort().equals("TBD")) {
				
					int idFixture = f.getIdFixtures();
					String date = f.getEventDate().split("T")[0];
					int idRound = f.getRound().getIdRound();
					String round = f.getRound().getReguralSeason();
					int idHomeTeam = f.getHomeTeam().getTeam().getIdTeam();
					String homeTeam = f.getHomeTeam().getTeam().getTeamName();
					String homeLogo = f.getHomeTeam().getTeam().getLogo();
					int idAwayTeam = f.getAwayTeam().getTeam().getIdTeam();
					String awayTeam = f.getAwayTeam().getTeam().getTeamName();
					String awayLogo = f.getAwayTeam().getTeam().getLogo();
					String score = f.getScore().getFullTime();
					String status = f.getStatus();
					String statusShort = f.getStatusShort();
					
					FixtureA fA = new FixtureA(idFixture, date, idRound, round, idHomeTeam, homeTeam, homeLogo, idAwayTeam, awayTeam, awayLogo, score, status, statusShort);
			
					fixtures.add(fA);
				}
			}
		}
		return fixtures;
	}
}
