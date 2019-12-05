package football.api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.repository.SeasonRepo;
import model.Season;


public class SeasonApi extends Thread {

	Param param = new Param();
	
	@Autowired
	SeasonRepo sr;
	
	
	public void run() {
		
		
	}
	
	public void apiSeason() {
		
		String json = null;
		try {
			HttpResponse<String> response = Unirest.get(param.getAdd()+"/seasons")
					.header("x-rapidapi-host", param.getH1())
					.header("x-rapidapi-key", param.getH2()).asString();
			json = response.getBody();

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
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
							
							for (int i = 0; i < br; i++) {
								Season s = new Season();
								jsonToken = parser.nextToken();
								s.setSeason(parser.getIntValue());
								int st=s.getSeason()+1;
								System.out.println(parser.getIntValue());
								System.out.println(parser.getIntValue());
								s.setSeasonText(s.getSeason()+"-"+st);
								System.out.println(s);
								System.out.println(s.getSeason());
								System.out.println(s.getSeasonText());
								s=sr.save(s);
								System.out.println(s.getSeason());
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

	
}