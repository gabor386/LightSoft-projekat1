package football.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.api.Param;
import football.repository.CoachRepo;
import football.repository.SidelineRepo;
import football.repository.TeamplayerRepo;
import model.Coach;
import model.SideLine;
import model.TeamPlayer;

@RestController
public class SidelinedController {

	private Param param = new Param();
	
	@Autowired
	TeamplayerRepo pr;
	@Autowired
	SidelineRepo sr;
	@Autowired
	CoachRepo cr;
	
	
	
	@RequestMapping (value = "/sideline")
	public void apiSidelined() {
		List<TeamPlayer> players = pr.findAll();
		List<Coach> coaches = cr.findAll();
		List<SideLine> sls = sr.findAll();
		String json = null;
		for (TeamPlayer p:players) {
			try {
				HttpResponse<String> response = Unirest.get(param.getAdd() + "/sidelined/player/" + p.getIdTeamPlayer())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
				json = response.getBody();
				System.out.println(json);
			} catch (UnirestException e) {
				e.printStackTrace();
			}
			if(json!=null) {
				JsonFactory factory = new JsonFactory();
				try {
					JsonParser parser = factory.createParser(json);
					while (!parser.isClosed()) {
						JsonToken jsonToken = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(jsonToken)) {
							String fieldName = parser.getCurrentName();
							if ("results".equals(fieldName)) {
								jsonToken = parser.nextToken();
								int br = parser.getIntValue();
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								for (int i=0; i<br; i++) {
									SideLine s = new SideLine();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									s.setType(parser.getValueAsString());
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									s.setStart(parser.getValueAsString());
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									s.setEnd(parser.getValueAsString());
									jsonToken = parser.nextToken();
									if	(!hasSideline(s, sls)) {
										s.setTeamPlayer(p);
										sr.save(s);
									}
								}
							}
						}
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
				json=null;
			}
		}
		for (Coach c:coaches) {
			try {
				HttpResponse<String> response = Unirest.get(param.getAdd() + "/sidelined/coach/" + c.getIdCoach())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
				json = response.getBody();
				
			} catch (UnirestException e) {
				e.printStackTrace();
			}
			if(json!=null) {
				JsonFactory factory = new JsonFactory();
				try {
					JsonParser parser = factory.createParser(json);
					while (!parser.isClosed()) {
						JsonToken jsonToken = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(jsonToken)) {
							String fieldName = parser.getCurrentName();
							if ("results".equals(fieldName)) {
								jsonToken = parser.nextToken();
								int br = parser.getIntValue();
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								for (int i=0; i<br; i++) {
									SideLine s = new SideLine();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									s.setType(parser.getValueAsString());
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									s.setStart(parser.getValueAsString());
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									s.setEnd(parser.getValueAsString());
									jsonToken = parser.nextToken();
									if	(!hasSideline(s, sls)) {
										s.setCoach(c);
										sr.save(s);
									}	
								}
							}
						}
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
				json=null;
			}
		}
		
		
	}
	
	public boolean hasSideline (SideLine sl, List<SideLine> sls) {
		for(SideLine s:sls) {
			if (s.getType().equals(sl.getType()) && s.getStart().equals(sl.getStart()) && s.getEnd().equals(sl.getEnd())) {
				if(s.getCoach().getIdCoach()==sl.getCoach().getIdCoach() || s.getTeamPlayer().getIdTeamPlayer()==sl.getTeamPlayer().getIdTeamPlayer()) {
					return true;
				}
			}
		}
		sls.add(sl);
		return false;
	}
	
	
}
