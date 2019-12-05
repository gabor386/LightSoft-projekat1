package football.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import football.api.Param;
import football.repository.BookmakerRepo;
import model.Bookmaker;
import model.Label;

@RestController
public class BookmakerController {

	private Param param = new Param();
	
	@Autowired
	BookmakerRepo bkr;
	
	// dodaavanje bookmaker-a u bazu
	@RequestMapping (value = "try3")
		public void apiLabel() {
			String json = null;

			try {
				HttpResponse<String> response = Unirest.get(param.getAdd() + "/odds/bookmakers/")
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
				json = response.getBody();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (json != null) {
				List<Bookmaker> bms=bkr.findAll();
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
									Bookmaker b = new Bookmaker();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									b.setIdBookmaker(parser.getIntValue());
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									b.setName(parser.getValueAsString());
									jsonToken = parser.nextToken();
									if (!bms.contains(b)) {
										bms.add(b);
										bkr.save(b);
									}
									
								}
							}
						}
					}
				}

				catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
	
}
