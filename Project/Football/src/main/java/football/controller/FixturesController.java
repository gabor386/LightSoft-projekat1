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
@RequestMapping(value="/fixtureController")
public class FixturesController {
	
	
	private Param param=new Param();
	
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
	
	
	
	@RequestMapping(value="/saveFixture", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
	public String saveFixture() {
		
		List<Fixture> fixtures  = apiFixtures();
			fixtureRepo.saveAll(fixtures);
		
		return "Uspesno";
		
	}
	
	
	
	
	public List<Fixture> apiFixtures() {
		
		List<Fixture> retFixture = new ArrayList<Fixture>();
		

		try {
			HttpResponse<String> response = Unirest.get(param.getAdd()+"/fixtures")
					.header("x-rapidapi-host", param.getH1())
					.header("x-rapidapi-key", param.getH2()).asString();
			
			
			
			String json = response.getBody();
			
			JSONParser parse = new JSONParser();
			
			JSONObject o = (JSONObject)parse.parse(json);
			
			JSONObject o1 =  (JSONObject) o.get("api");
				
			JSONArray n1 = (JSONArray) o1.get("fixtures");
			
			for(int i=0;i<n1.size();i++){
				
				JSONObject o2 = (JSONObject)n1.get(i);
				
				Fixture fixture = new Fixture();
				
				fixture.setIdFixtures((int) o2.get("fixture_id"));
				fixture.setEventDate((String) o2.get("event_date"));
				fixture.setEventTimeStamp((int) o2.get("event_timestamp"));
				fixture.setFristHalfStart((int) o2.get("firstHalfStart"));
				fixture.setSecondHalfStart((int) o2.get("secondHalfStart"));
				fixture.setStatus((String) o2.get("status"));
				fixture.setStatusShort((String) o2.get("statusShort"));
				fixture.setElapsed((int) o2.get("elapsed"));
				fixture.setVenue((String) o2.get("venue"));
				fixture.setReferee((String) o2.get( "referee"));
				fixture.setGoalsHomeTeam((int) o2.get("goalsHomeTeam"));
				fixture.setGoalsAwayTeam((int) o2.get("goalsAwayTeam"));
				
				
			  String pomRound = (String) o2.get("round");
			  
			  Round round = null;
			  
			  League league = leagueRepo.getOne((Integer) o1.get("league_id"));
			  List<Round> rounds = roundRepo.findByLeague(league);
			  
			  for(Round r : rounds) {
				  if(r.getReguralSeason().equals(pomRound)) {
					  round = r;
				  }
			  }
			  
			  fixture.setRound(round);
			  
			  JSONObject o3 =  (JSONObject) o2.get("homeTeam");
			  JSONObject o4 =  (JSONObject) o2.get("awayTeam");
			  Score score =  (Score) o2.get("score");
			  
			  
			  
			  Hometeam pomHomeTeam =  (Hometeam) o3.get("team_id");
			  Awayteam pomAwayTeam =  (Awayteam) o4.get("team_id");
			 
			 
			  fixture.setHometeam(pomHomeTeam);
			  fixture.setAwayteam(pomAwayTeam);
			  fixture.setScore(score);
			  
			  
			  
			  
			  retFixture.add(fixture);
				
			}
			
			
		} catch (UnirestException | ParseException  e1) {
			e1.printStackTrace();
		}
		return retFixture;
	}

}
