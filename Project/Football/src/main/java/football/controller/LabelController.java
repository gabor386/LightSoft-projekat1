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

import football.repository.LabelRepo;
import model.Label;

@RestController
public class LabelController {

	private Param param = new Param();
	
	@Autowired
	LabelRepo lr;
	
	
	// dodaavanje labela u bazu

	public void apiLabel() {
		String json = null;

		try {
			HttpResponse<String> response = Unirest.get(param.getAdd() + "/odds/labels/")
					.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
			json = response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (json != null) {
			List<Label> labels=lr.findAll();
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
								Label l = new Label();
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								l.setIdLabel(parser.getIntValue());
								jsonToken = parser.nextToken();
								jsonToken = parser.nextToken();
								l.setLabel(parser.getValueAsString());
								jsonToken = parser.nextToken();
								if (!labels.contains(l)) {
									labels.add(l);
									lr.save(l);
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
