package football.controller;

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

import football.repository.TimezonaRepo;
import model.Timezona;

@RestController
@RequestMapping(value = "/timeZoneController")
public class TimeZoneController {
	
	private Param param = new Param();
	
	@Autowired
	TimezonaRepo timeZonaRepo;
	
	
	@RequestMapping(value = "/saveTimeZone", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public void apiFixtures() {
		
		

		HttpResponse<String> response = null;
		try {
			response = Unirest.get(param.getAdd() + "/timezone")
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

			JSONArray n1 = (JSONArray) o1.get("timezone");

			for (int i = 0; i < n1.size(); i++) {
				
				System.out.println("Vremenske zone " + n1.get(i).toString());
				
				Timezona timezona = new Timezona();
				
				timezona.setTimeZone(n1.get(i).toString());
		
					
				timeZonaRepo.save(timezona);
		
			}

		} catch (ParseException e) {

			e.printStackTrace();
		}		
				
		
	}
	
	
}
