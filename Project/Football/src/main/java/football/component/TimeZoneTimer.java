package football.component;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.controller.Param;
import football.repository.TimezonaRepo;
import model.TimeZona;

@Component
public class TimeZoneTimer {
	
	private Param param = new Param();
	
	@Autowired
	TimezonaRepo timeZonaRepo;
	
	 private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	 @Scheduled(fixedRate = 5000)
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
				
				System.out.println("Trenutno vreme" + dateFormat.format(new Date()));
				System.out.println("Vremenske zone " + n1.get(i).toString());
				
//				Timezona timezona = new Timezona();
//				
//				timezona.setTimeZone(n1.get(i).toString());
//		
//					
//				timeZonaRepo.save(timezona);
		
			}

		} catch (ParseException e) {

			e.printStackTrace();
		}		
				
		
	}
	
	
}
