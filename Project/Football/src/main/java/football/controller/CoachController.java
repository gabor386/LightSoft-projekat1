package football.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.api.Param;
import football.repository.CareerRepo;
import football.repository.CoachRepo;
import football.repository.CountryRepo;
import football.repository.TeamRepo;
import model.Career;
import model.Coach;
import model.Team;

@RestController
public class CoachController {

	private Param param = new Param();
	
	@Autowired
	CoachRepo cr;
	@Autowired
	CareerRepo careerr;
	@Autowired
	TeamRepo tr;
	@Autowired
	CountryRepo countryr;
	
	
	@RequestMapping (value = "/coach")
	public void apiCoach() {
		String json=null;
		List<Team> teams = tr.findAll();
		
		for (Team t:teams) {
			try {
				HttpResponse<String> response = Unirest.get(param.getAdd() + "/coachs/team/" +t.getIdTeam())
						.header("x-rapidapi-host", param.getH1())
						.header("x-rapidapi-key", param.getH2()).asString();
				json = response.getBody();
				System.out.println(json);
			} catch (UnirestException e) {
				e.printStackTrace();
			}
			
			if (json!=null) {
				JSONParser parse = new JSONParser();
				JSONObject o;
				try {
				o = (JSONObject) parse.parse(json);

				JSONObject o1 = (JSONObject) o.get("api");

				Long result = (Long) o1.get("results");
				Integer br = result.intValue();

				JSONArray coaches = (JSONArray) o1.get("coachs");
				for(int i=0;i<result;i++) {
					JSONObject objectCoach=(JSONObject)coaches.get(i);
					
					Coach c= new Coach();
					Long idCoach = (Long) objectCoach.get("id");
					Integer id= idCoach.intValue();
					c.setIdCoach(id);
					String name = (String) objectCoach.get("name");
					c.setName(name);
					String firstName = (String) objectCoach.get("firstname");
					c.setFristName(firstName);
					String lastName = (String) objectCoach.get("lastname");
					c.setLastName(lastName);
					if (objectCoach.get("age")!=null) {
					Long age = (Long) objectCoach.get("age");
					c.setAge(age.intValue());
					}
					String birthDate = (String) objectCoach.get("birth_date");
					c.setBrithDate(birthDate);
					String birthPlace = (String) objectCoach.get("birth_place");
					c.setBrithPlace(birthPlace);
					String birthCountry = (String) objectCoach.get("birth_country");
					c.setBrithCountry(birthCountry);
					String nationality = (String) objectCoach.get("nationality");
					c.setNationality(nationality);
					String height = (String) objectCoach.get("height");
					c.setHeight(height);
					String weight = (String) objectCoach.get("weight");
					c.setWeight(weight);
					c.setTeam(t);
					c.setCountry(countryr.findByName(birthCountry));
					System.out.println(c.getTeam().getTeamName());
					c=cr.save(c);
					JSONArray careers = (JSONArray) objectCoach.get("career");
					for (int j=0; j< careers.size(); j++) {
						JSONObject objectCareers=(JSONObject)careers.get(j);
						Career career = new Career();
						String start = (String) objectCareers.get("start");
						career.setStart(start);
						String end = (String) objectCareers.get("end");
						career.setEnd(end);
						JSONObject tim = (JSONObject) objectCareers.get("team");
						Long idTim = (Long) tim.get("id");
						System.out.println((String) tim.get("name"));
						if (idTim!=null) {
						career.setTeam(tr.getOne(idTim.intValue()));
						}
						career.setCoach(c);
						careerr.save(career);
					}
					
				}
				
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			json=null;
			
		}
		
	}
	
	
	
}
