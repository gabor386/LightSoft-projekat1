package football.controller;


import java.io.IOException;
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
import model.Country;


@RestController
@RequestMapping(value="/footballController")
public class FootballController {

	@Autowired
	CountryRepo cr;
	
	@RequestMapping(value="/saveCountry", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
	public String saveCountry() {
		
		
		List<Country> co = getCountries();
			cr.saveAll(co);
		
		return "Uspesno";
		
	}
	

	    public List<Country> getCountries() {
	      
	    	List<Country> ret = new ArrayList<Country>();
	     
	    
	        	 //"https://www.api-football.com/demo/v2/countries"
	    	
	    	
	                 HttpResponse<String> response = null;
					try {
						response = Unirest.get("https://server1.api-football.com/countries")
						.header("x-rapidapi-host", "https://server1.api-football.com")
						.header("x-rapidapi-key", "2437ee009238740cb9bf18ba3efd23ed")
						.asString();
					
						String rez = response.getBody();
						
						JSONParser parse = new JSONParser();
							
						JSONObject o = (JSONObject)parse.parse(rez);
						
						JSONObject o1 =  (JSONObject) o.get("api");
							
						JSONArray n1 = (JSONArray) o1.get("countries");
						
						for(int i=0;i<n1.size();i++){
							
							JSONObject o2 = (JSONObject)n1.get(i);
								
							Country c = new Country();
							c.setName((String) o2.get("country"));
							c.setFlag((String) o2.get("flag"));
							c.setCode((String) o2.get("code"));
							
			              ret.add(c);
						
						}
						
		
					} catch (UnirestException | ParseException  e1) {
						e1.printStackTrace();
					}
	          
	        	return ret;
    }

}