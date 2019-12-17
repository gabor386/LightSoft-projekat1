package football.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.repository.CountryRepo;
import model.Country;
@RestController
public class CountryController {

	private Param param=new Param();
	@Autowired
	CountryRepo cr;

	// Dodavanje drzava u bazu
	//jednom mesecno prvog u ponoc
	//@Scheduled(cron="0 0 0 1 1-12 *")
	
	public void apiCountry() {
		System.out.println("Dodavanje drzava u bazu..");
		String json = null;
		try {
			HttpResponse<String> response = Unirest.get(param.getAdd()+"/countries")
					.header("x-rapidapi-host", param.getH1())
					.header("x-rapidapi-key", param.getH2()).asString();
			json = response.getBody();

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (json != null) {

			List<Country> cs = cr.findAll();

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
							jsonToken = parser.nextToken();
							for (int i = 0; i < br; i++) {
								Country c = new Country();
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								c.setName(parser.getValueAsString());
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								c.setCode(parser.getValueAsString());
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								c.setFlag(parser.getValueAsString());
								if (!hasCountry(c, cs)) {
									cr.save(c);
								}
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
							}
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean hasCountry(Country country, List<Country> cs) {
		for (Country c : cs) {
			if (c.getName().equals(country.getName()))
				return true;
		}
		cs.add(country);
		return false;
	}
}
