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

import football.repository.CountryRepo;
import football.repository.TeamRepo;
import model.Country;
import model.Team;



@RestController
@RequestMapping(value="/teamController")
public class TeamController {
	
	
	private Param param=new Param();
	
	@Autowired
	CountryRepo countryRepo;
	
	@Autowired 
	TeamRepo teamRepo;
	
	
	
	@RequestMapping(value="/saveTeams", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
	public void apiFixtures() {
		
		List<Team> retTeam = new ArrayList<Team>();
		

		HttpResponse<String> response = null;
		try {
			response = Unirest.get(param.getAdd()+"/teams/league/357")
					.header("x-rapidapi-host", param.getH1())
					.header("x-rapidapi-key", param.getH2()).asString();
		} catch (UnirestException e1) {
			e1.printStackTrace();
		}
		
		
		
		String json = response.getBody();
		
		JSONParser parse = new JSONParser();
		JSONObject o;
		
		Team team = new Team();
		
		try {
			o = (JSONObject)parse.parse(json);
			JSONObject o1 =  (JSONObject) o.get("api");
			
			JSONArray n1 = (JSONArray) o1.get("teams");
			
			for(int i=0;i<n1.size();i++){
				
				JSONObject o2 = (JSONObject)n1.get(i);
				
			       Long teamIdLong = (Long) o2.get("team_id"); 
				   Integer teamId  = teamIdLong.intValue();
				   team.setIdTeam(teamId);
				   
				   Long teamFoundedLong = (Long) o2.get("founded"); 
				   Integer teamFounded  = teamFoundedLong.intValue();
				   team.setFounded(teamFounded);
				   
				   Long teamCapacityLong = (Long) o2.get("venue_capacity"); 
				   Integer teamCapacity  = teamCapacityLong.intValue();
				   team.setCapacity(teamCapacity);
				   
				 
				   String teamAddress  = (String) o2.get("venue_address");
				   team.setStadionAddress(teamAddress);
				   
 
				   
				   
				  team.setTeamName((String) o2.get("name"));
				  team.setLogo((String) o2.get("logo"));
				  team.setStadionName((String) o2.get("venue_name"));
				  team.setStadionCity((String) o2.get("venue_city"));
				 
				  team.setStadionSurface((String) o2.get("venue_surface"));
				  
				  String nameCountryTeam = (String) o2.get("country");
				  
				 Country country = countryRepo.findByName(nameCountryTeam);
				 team.setCountry(country);
				 
//				 List<Team> teams = teamRepo.findByCountry(country);
//				 
//				 for(Team t : teams) {
//					 if(t.getCountry().getName().equals(nameCountryTeam)) {
//						 
//					 }
//				 }
				  
				  
				
				
				 retTeam.add(team);
			     retTeam = teamRepo.saveAll(retTeam);
				
			}
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		
	
	}

}
