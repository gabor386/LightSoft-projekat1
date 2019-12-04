package football.controller;

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

	private Param param=new Param();

	@Autowired
	LeagueRepo lr;
	@Autowired
	CountryRepo cr;
	@Autowired
	SeasonRepo sr;
	
	
	
	@RequestMapping (value = "try")
	public void apiLeague() {

		String json = null;

		try {
			HttpResponse<String> response = Unirest.get(param.getAdd()+"/leagues")
					.header("x-rapidapi-host", param.getH1())
					.header("x-rapidapi-key", param.getH2()).asString();
			json = response.getBody();
			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (json != null) {
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
								League l = new League();
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								l.setIdLeague(parser.getIntValue());
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								l.setName(parser.getValueAsString());
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								l.setType(parser.getValueAsString());
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								Country c=null;
								try {
								c = cr.findByName(parser.getValueAsString());
								}catch (Exception e) {
									e.printStackTrace();
								}
								l.setCountry(c);
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								Season s =null;
								try {
									s=sr.findBySeason(parser.getIntValue());
								}catch (Exception e) {
									e.printStackTrace();
								}	
								l.setSeasonBean(s);
								 // Ovde treba povezati sa sezonm...
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								l.setSeasonStart(parser.getValueAsString());
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								l.setSeasonEnd(parser.getValueAsString());
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								l.setLogo(parser.getValueAsString());
								for (int j=0; j<31; j++) {
									jsonToken = parser.nextToken();
								}
								System.out.println("trying");
								System.out.println(l);
								if (lr.getOne(l.getIdLeague())==null) {
									lr.save(l);
									
								}
								
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		json=null;
	}

}
