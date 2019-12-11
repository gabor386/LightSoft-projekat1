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

import football.repository.FixtureRepo;
import football.repository.FixturestatRepo;
import model.Fixture;
import model.FixtureStat;

@RestController
@RequestMapping(value = "/fixtureStatisticController")
public class FixtureStatisticController {

	private Param param = new Param();

	@Autowired
	FixtureRepo fixtureRepo;

	@Autowired
	FixturestatRepo fixtureStatRepo;

	@RequestMapping(value = "/saveFixtureStatistic", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public void apiPrediction() {

		List<Fixture> fixtures = fixtureRepo.findAll();

		for (Fixture f : fixtures) {

			List<FixtureStat> retfixtureStats = new ArrayList<FixtureStat>();

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
			
			FixtureStat deleteStat = fixtureStatRepo.findByFixture(f);
			if(deleteStat != null)
				fixtureStatRepo.delete(deleteStat);

			try {

				o = (JSONObject) parse.parse(json);
				JSONObject o1 = (JSONObject) o.get("api");

				
				System.out.println("Mecevi " + f.getIdFixtures());
				
				  JSONObject o2 ;
				
				if(o1.get("statistics") instanceof JSONObject) {
				    o2= (JSONObject) o1.get("statistics");
				
				 
				
				//Sutevi na golllllllllllllllllllllllllllllllllllllllllll
				
				FixtureStat shotOnGoal = new FixtureStat();

				JSONObject o3 = (JSONObject) o2.get("Shots on Goal");

				shotOnGoal.setFixtureStatName("Shots on Goal");

				String home = (String) o3.get("home");
				shotOnGoal.setHome(home == null ? null : home);

				String away = (String) o3.get("away");
				shotOnGoal.setAway(away == null ? null : away);

				shotOnGoal.setFixture(f);

				retfixtureStats.add(shotOnGoal);
				
				//Sutevi pored golaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

				FixtureStat shotOffGoal = new FixtureStat();

				JSONObject o4 = (JSONObject) o2.get("Shots off Goal");

				shotOffGoal.setFixtureStatName("Shots off Goal");

			     home = (String) o4.get("home");
				shotOffGoal.setHome(home == null ? null : home);

				 away = (String) o4.get("away");
				shotOffGoal.setAway(away == null ? null : away);

				shotOffGoal.setFixture(f);

				retfixtureStats.add(shotOffGoal);
				
				//Ukupno suteviiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
				
				FixtureStat totalShots = new FixtureStat();

				JSONObject o5 = (JSONObject) o2.get("Total Shots");

				totalShots.setFixtureStatName("Total Shots");

			    home = (String) o5.get("home");
				totalShots.setHome(home == null ? null : home);

				 away = (String) o5.get("away");
				totalShots.setAway(away == null ? null : away);

				totalShots.setFixture(f);

				retfixtureStats.add(totalShots);
				
				//Blokirani suteviiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
				
				FixtureStat blockedShots = new FixtureStat();

				JSONObject o6 = (JSONObject) o2.get("Blocked Shots");

				blockedShots.setFixtureStatName("Blocked Shots");

			    home = (String) o6.get("home");
				blockedShots.setHome(home == null ? null : home);

				away = (String) o6.get("away");
				blockedShots.setAway(away == null ? null : away);

				blockedShots.setFixture(f);

				retfixtureStats.add(blockedShots);
				
				//Shots insideboxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
				
				FixtureStat shotInsideBox = new FixtureStat();

				JSONObject o7 = (JSONObject) o2.get("Shots insidebox");

				shotInsideBox.setFixtureStatName("Shots insidebox");

			    home = (String) o7.get("home");
				shotInsideBox.setHome(home == null ? null : home);

				away = (String) o7.get("away");
				shotInsideBox.setAway(away == null ? null : away);

				shotInsideBox.setFixture(f);

				retfixtureStats.add(shotInsideBox);
				
				//Shots outsideboxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
				
				FixtureStat shotOutsideBox = new FixtureStat();

				JSONObject o8 = (JSONObject) o2.get("Shots outsidebox");

				shotOutsideBox.setFixtureStatName("Shots outsidebox");

			    home = (String) o8.get("home");
				shotOutsideBox.setHome(home == null ? null : home);

				away = (String) o8.get("away");
				shotOutsideBox.setAway(away == null ? null : away);

				shotOutsideBox.setFixture(f);

				retfixtureStats.add(shotOutsideBox);
				
				//Fauloviiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
				
				FixtureStat fouls = new FixtureStat();

				JSONObject o9 = (JSONObject) o2.get("Fouls");

				fouls.setFixtureStatName("Fouls");

			    home = (String) o9.get("home");
				fouls.setHome(home == null ? null : home);

				away = (String) o9.get("away");
				fouls.setAway(away == null ? null : away);

				fouls.setFixture(f);

				retfixtureStats.add(fouls);
				
				//Korneriiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
				
				FixtureStat cornerKicks = new FixtureStat();

				JSONObject o10 = (JSONObject) o2.get("Corner Kicks");

				cornerKicks.setFixtureStatName("Corner Kicks");

			    home = (String) o10.get("home");
				cornerKicks.setHome(home == null ? null : home);

				away = (String) o10.get("away");
				cornerKicks.setAway(away == null ? null : away);

				cornerKicks.setFixture(f);

				retfixtureStats.add(cornerKicks);
				
				//Ofsajdiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
				
				FixtureStat offsides = new FixtureStat();

				JSONObject o11 = (JSONObject) o2.get("Offsides");

				offsides.setFixtureStatName("Offsides");

			    home = (String) o11.get("home");
				offsides.setHome(home == null ? null : home);

				away = (String) o11.get("away");
				offsides.setAway(away == null ? null : away);

				offsides.setFixture(f);

				retfixtureStats.add(offsides);
				
				//Posed lopteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
				

				FixtureStat ballPossession = new FixtureStat();

				JSONObject o12 = (JSONObject) o2.get("Ball Possession");

				ballPossession.setFixtureStatName("Ball Possession");

			    home = (String) o12.get("home");
				ballPossession.setHome(home == null ? null : home);

				away = (String) o12.get("away");
				ballPossession.setAway(away == null ? null : away);

				ballPossession.setFixture(f);

				retfixtureStats.add(ballPossession);
				
				//Zuti kartoniiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
				

				FixtureStat yellowCards = new FixtureStat();

				JSONObject o13 = (JSONObject) o2.get("Yellow Cards");

				yellowCards.setFixtureStatName("Yellow Cards");

			    home = (String) o13.get("home");
				yellowCards.setHome(home == null ? null : home);

				away = (String) o13.get("away");
				yellowCards.setAway(away == null ? null : away);

				yellowCards.setFixture(f);

				retfixtureStats.add(yellowCards);
				
				//Crveni kartoniiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
				

				FixtureStat redCards = new FixtureStat();

				JSONObject o14 = (JSONObject) o2.get("Red Cards");

				redCards.setFixtureStatName("Red Cards");

			    home = (String) o14.get("home");
				redCards.setHome(home == null ? null : home);

				away = (String) o14.get("away");
				redCards.setAway(away == null ? null : away);

				redCards.setFixture(f);

				retfixtureStats.add(redCards);
				
				//Odbraneeeeee golmanaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
				

				FixtureStat goalkeeperSaves = new FixtureStat();

				JSONObject o15 = (JSONObject) o2.get("Goalkeeper Saves");

				goalkeeperSaves.setFixtureStatName("Goalkeeper Saves");

			    home = (String) o15.get("home");
				goalkeeperSaves.setHome(home == null ? null : home);

				away = (String) o15.get("away");
				goalkeeperSaves.setAway(away == null ? null : away);

				goalkeeperSaves.setFixture(f);

				retfixtureStats.add(goalkeeperSaves);
				
				//Ukupnoo pasovaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
				
				

				FixtureStat totalPasses = new FixtureStat();

				JSONObject o16 = (JSONObject) o2.get("Total passes");

				totalPasses.setFixtureStatName("Total passes");

			    home = (String) o16.get("home");
				totalPasses.setHome(home == null ? null : home);

				away = (String) o16.get("away");
				totalPasses.setAway(away == null ? null : away);

				totalPasses.setFixture(f);

				retfixtureStats.add(totalPasses);
				
				// Passes accurateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
				

				FixtureStat passesAccurate = new FixtureStat();

				JSONObject o17 = (JSONObject) o2.get("Passes accurate");

				passesAccurate.setFixtureStatName("Passes accurate");

			    home = (String) o17.get("home");
				passesAccurate.setHome(home == null ? null : home);

				away = (String) o17.get("away");
				passesAccurate.setAway(away == null ? null : away);

				passesAccurate.setFixture(f);

				retfixtureStats.add(passesAccurate);
				
				//  Passes %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
				

				FixtureStat passes = new FixtureStat();

				JSONObject o18 = (JSONObject) o2.get("Passes %");

				passes.setFixtureStatName("Passes %");

			    home = (String) o18.get("home");
				passes.setHome(home == null ? null : home);

				away = (String) o18.get("away");
				passes.setAway(away == null ? null : away);

				passes.setFixture(f);

				retfixtureStats.add(passes);
				
				
				    fixtureStatRepo.saveAll(retfixtureStats); 
			}
				
				
				else {
					System.out.println("Ovde preskacem");
					continue;
				}
				
				

			} catch (ParseException e) {

				e.printStackTrace();
			}

		}
	}
}
