package football.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import football.repository.BookmakerRepo;
import model.Bookmaker;

public class BookmakerApi extends Thread {

	private Param param = new Param();

	@Autowired
	BookmakerRepo bkr;

	public void run() {

	}

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
			List<Bookmaker> bms = bkr.findAll();
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
