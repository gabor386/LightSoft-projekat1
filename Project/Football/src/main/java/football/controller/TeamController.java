package football.controller;

import static org.hamcrest.CoreMatchers.instanceOf;

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

import football.repository.CountryRepo;
import football.repository.LeagueRepo;
import football.repository.TeamRepo;
import model.Country;
import model.League;
import model.Team;

@RestController
@RequestMapping(value = "/teamController")
public class TeamController {

	private Param param = new Param();

	@Autowired
	CountryRepo countryRepo;

	@Autowired
	TeamRepo teamRepo;

	@Autowired
	LeagueRepo leagueRepo;

	@RequestMapping(value = "/saveTeams", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public void apiFixtures() {

		List<League> leagues = leagueRepo.findAll();

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

					Country country = countryRepo.findByName(nameC);
					team.setCountry(country);

					teamRepo.save(team);

				}

			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

	}

}
