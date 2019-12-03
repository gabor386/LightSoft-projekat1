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
import model.Fixturestat;

@RestController
@RequestMapping(value = "/fixtureStatisticController")
public class FixtureStatisticController {

	private Param param = new Param();

	@Autowired
	FixturestatRepo fixtureStatisticRepo;

	@Autowired
	FixtureRepo fixtureRepo;

	@RequestMapping(value = "/saveStatisticFixture", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String saveStatisticFixture() {

		List<Fixturestat> fixtureStat = apiStatisticFixtures();
		fixtureStatisticRepo.saveAll(fixtureStat);

		return "Uspesno";

	}

	public List<Fixturestat> apiStatisticFixtures() {

		List<Fixture> fixtures = fixtureRepo.findAll();

		for (Fixture f : fixtures) {
			
			List<Fixturestat> retStatisticFixture = new ArrayList<Fixturestat>();

			try {
				HttpResponse<String> response = Unirest.get(param.getAdd() + "/players/fixture/" + f.getIdFixtures())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();

				String json = response.getBody();

				JSONParser parse = new JSONParser();

				JSONObject o = (JSONObject) parse.parse(json);

				JSONObject o1 = (JSONObject) o.get("api");

				JSONArray n1 = (JSONArray) o1.get("players");

				for (int i = 0; i < n1.size(); i++) {

					JSONObject o2 = (JSONObject) n1.get(i);

				     

				}

			} catch (UnirestException | ParseException e1) {
				e1.printStackTrace();
			}
			
			return retStatisticFixture;
		}
		return null;
		
	}

}
