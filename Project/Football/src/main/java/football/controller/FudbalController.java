package football.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.json.HTTP;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.repository.CountryRepo;
import model.Country;

@RestController
public class FudbalController {
	
	@Autowired
	CountryRepo cr;
	
	
	public void apiCountry() {
		try {
			HttpResponse<String> response = Unirest.get("http://www.api-football.com/demo/api/v2/countries")
					.header("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
					.header("x-rapidapi-key", "SIGN-UP-FOR-KEY").asString();
					
		
			
			//Country c=postCountry(response);
			 
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PostMapping("/country")
	public Country postCountry(@RequestBody List<Country> c) {
		

		cr.saveAll(c);
		return c.get(0);
	}
	
	

}
