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

		List<Fixture> retFixture = new ArrayList<Fixture>();

		HttpResponse<String> response = null;
		try {
			response = Unirest.get(param.getAdd() + "/fixtures/league/357").header("x-rapidapi-host", param.getH1())
					.header("x-rapidapi-key", param.getH2()).asString();
		} catch (UnirestException e1) {
			e1.printStackTrace();
		}

		String json = response.getBody();

		JSONParser parse = new JSONParser();
		JSONObject o;

		Fixture fixture = new Fixture();

		try {
			o = (JSONObject) parse.parse(json);
			JSONObject o1 = (JSONObject) o.get("api");

			JSONArray n1 = (JSONArray) o1.get("fixtures");

			for (int i = 0; i < n1.size(); i++) {

				JSONObject o2 = (JSONObject) n1.get(i);

				Long idLongFixture = (long) o2.get("fixture_id");
				Integer idFixture = idLongFixture == null ? null : idLongFixture.intValue();
				fixture.setIdFixtures(idFixture);
				System.out.println("Id fix : " + idFixture);
//					  
//					   Long eventTimeStampLong =  (Long) o2.get("event_timestamp");
//					   Integer eventTimeStamp = eventTimeStampLong == null ? null : eventTimeStampLong.intValue();
//					  // fixture.setEventTimeStamp(eventTimeStamp);
//					  System.out.println("Event time stamp" + eventTimeStamp);	
//					 
//
//					   Long firstHalfStartLong =  (Long) o2.get("firstHalfStart");
//					   Integer firstHalfStart  = firstHalfStartLong == null ? null : firstHalfStartLong.intValue();
//					   //fixture.setFristHalfStart(firstHalfStart);
//					   System.out.println("Frist half start" + firstHalfStart);
//
//					   Long secondtHalfStartLong = (Long) o2.get("secondHalfStart");
//					   Integer secondHalfStart  = secondtHalfStartLong  == null ? null:secondtHalfStartLong.intValue();
//					  // fixture.setSecondHalfStart(secondHalfStart);
//					   System.out.println("Sec half start" + secondHalfStart);
//					   
//					   
//					   Long elapsedLong = (Long) o2.get("elapsed");
//					   Integer elapsed  = elapsedLong == null ? null :elapsedLong.intValue();
//					   //fixture.setElapsed(elapsed);
//					   System.out.println("Elapsed" + elapsed);
//					   
//					   Long goalsHomeTeamLong = (Long) o2.get("goalsHomeTeam"); 
//					   Integer goalsHomeTeam  = goalsHomeTeamLong == null  ?  null : goalsHomeTeamLong.intValue();
//					   //fixture.setGoalsHomeTeam(goalsHomeTeam);
//					   System.out.println("Goals team home" + goalsHomeTeam);
//					   
//					   Long goalsAwayTeamLong = (Long) o2.get("goalsAwayTeam"); 
//					   Integer goalsAwayTeam  = goalsAwayTeamLong == null ? null :goalsAwayTeamLong.intValue();
//					   //fixture.setGoalsAwayTeam(goalsAwayTeam);
//					   System.out.println("Goals team away" + goalsAwayTeam); 

//					   
//					   fixture.setEventDate((String) o2.get("event_date"));
//					   fixture.setStatus((String) o2.get("status"));
//					   fixture.setStatusShort((String) o2.get("statusShort"));
//					   fixture.setVenue((String) o2.get("venue"));
//					   fixture.setReferee((String) o2.get("referee"));

//			
//				 
//				 Long idLeagueLong = (Long) o2.get("league_id"); 
//				 Integer idLeague  = idLeagueLong.intValue();
//				 System.out.println("Liga id " + idLeague);
//				  
//
//				  League league = leagueRepo.getOne(idLeague);
//				  List<Round> rounds = roundRepo.findByLeague(league);
//				  
//				  for(Round r : rounds) {
//					  if(r.getReguralSeason().equals(o2.get("round"))) {
//						  System.out.println("Runde" + r.getReguralSeason());
//						 // fixture.setRound(r);
//					  }
//				  }

//				 
//				  
				JSONObject o3 = (JSONObject) o2.get("homeTeam");
				JSONObject o4 = (JSONObject) o2.get("awayTeam");

				Hometeam homeTeam = new Hometeam();
				Awayteam awayTeam = new Awayteam();

				Long idHomeTeamLong = (Long) o3.get("team_id");
				Integer idHomeTeam = idHomeTeamLong != null ? idHomeTeamLong.intValue() : null;

				Team team = teamRepo.getOne(idHomeTeam);

				homeTeam.setTeam(team);
				homeTeam = homeTeamRepo.save(homeTeam);

				Long idAwayTeamLong = (Long) o4.get("team_id");
				Integer idAwayTeam = idAwayTeamLong != null ? idAwayTeamLong.intValue() : null;

				team = teamRepo.getOne(idAwayTeam);

				awayTeam.setTeam(team);
				awayTeam = awayTeamRepo.save(awayTeam);

				fixture.setHometeam(homeTeam);
				fixture.setAwayteam(awayTeam);

//				  JSONObject o5 =   (JSONObject) o2.get("score");
//				  
//				  Score score = new Score();
//				  score.setHalfTime((String) o5.get("halftime"));
//				  System.out.println("Half time" + (String) o5.get("halftime"));
//				  score.setFullTime((String) o5.get("fulltime"));
//				  System.out.println("Full time" + (String) o5.get("fulltime"));
//				  score.setExtraTime((String) o5.get("extratime"));
//				  System.out.println("Extra time" + (String) o5.get("extratime"));
//				  score.setPenalty((String) o5.get("penalty"));
//				  System.out.println("Penali" + (String) o5.get("penalty"));
//				  
//				  score = scoreRepo.save(score);
//				  fixture.setScore(score);
//				  				 

				retFixture.add(fixture);
				retFixture = fixtureRepo.saveAll(retFixture);

			}

		} catch (ParseException e) {

			e.printStackTrace();
		}

	}

}
