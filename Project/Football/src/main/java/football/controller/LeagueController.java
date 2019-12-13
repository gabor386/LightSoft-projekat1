package football.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import football.repository.CountryRepo;
import football.repository.LeagueRepo;
import football.repository.SeasonRepo;
import model.Country;
import model.League;
import model.Season;

@RestController
public class LeagueController {

	private Param param = new Param();

	@Autowired
	LeagueRepo lr;
	@Autowired
	CountryRepo cr;
	@Autowired
	SeasonRepo sr;
	
	@RequestMapping(value="/leauge")
	public void apiLeague() {

		String json = null;

		try {
			HttpResponse<String> response = Unirest.get(param.getAdd() + "/leagues")
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
					Long season = (Long) league.get("season");
					Season s = sr.getOne(season.intValue());
					l.setSeasonBean(s);
					String start = (String) league.get("season_start");
					l.setSeasonStart(start);
					String end = (String) league.get("season_end");
					l.setSeasonEnd(end);
					String logo = (String) league.get("logo");
					l.setLogo(logo);
					String type = (String) league.get("type");
					l.setType(type);

					lr.save(l);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		json = null;
	}

}
