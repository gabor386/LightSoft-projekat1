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

import football.repository.BetRepo;
import football.repository.BookmakerRepo;
import football.repository.FixtureRepo;
import football.repository.LabelRepo;
import football.repository.OddRepo;
import model.Bet;
import model.Bookmaker;
import model.Fixture;
import model.Label;
import model.Odd;

public class OddsApi extends Thread {

	private Param param = new Param();

	@Autowired
	OddRepo or;
	@Autowired
	BookmakerRepo bmr;
	@Autowired
	BetRepo betr;
	@Autowired
	LabelRepo lr;
	@Autowired
	FixtureRepo fr;

	public void run() {
		apiOdds();
	}

	public void apiOdds() {
		String json = null;
		List<Fixture> fs = fr.findAll();

		for (Fixture f : fs) {

			try {
				HttpResponse<String> response = Unirest.get(param.getAdd() + "/odds/fixture/" + f.getIdFixtures())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
				json = response.getBody();

			} catch (UnirestException e) {
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
								System.out.println("Broj rezultata " + br);
								for (int i = 0; i < br; i++) {
									Odd o = new Odd();
									o.setFixture(f);
									o = or.save(o);
									for (int j = 0; j < 12; j++) {
										jsonToken = parser.nextToken();
									}
									jsonToken = parser.nextToken();
									boolean ok = true;
									while (jsonToken != jsonToken.END_ARRAY && ok) {

										jsonToken = parser.nextToken();
										jsonToken = parser.nextToken();
										Bookmaker bm = bmr.getOne(parser.getIntValue());
										jsonToken = parser.nextToken();
										jsonToken = parser.nextToken();
										jsonToken = parser.nextToken();
										jsonToken = parser.nextToken();
										ok = false;
										boolean ok2 = true;
										jsonToken = parser.nextToken();
										while (jsonToken != jsonToken.END_ARRAY && ok2) {

											jsonToken = parser.nextToken(); // otvara objekat labele

											jsonToken = parser.nextToken();

											Label l = lr.getOne(parser.getIntValue());
											jsonToken = parser.nextToken();
											jsonToken = parser.nextToken();
											jsonToken = parser.nextToken();
											jsonToken = parser.nextToken();
											ok2 = false;
											jsonToken = parser.nextToken();
											while (jsonToken != jsonToken.END_ARRAY) {
												Bet b = new Bet();

												jsonToken = parser.nextToken();
												jsonToken = parser.nextToken();
												b.setBetValues(parser.getValueAsString());
												jsonToken = parser.nextToken();
												jsonToken = parser.nextToken();
												b.setOdd(parser.getValueAsString());
												b.setBookmaker(bm);
												b.setLabel(l);
												b.setOddBean(o);
												b = betr.save(b);
												System.out.println("insert bet");
												jsonToken = parser.nextToken();
												jsonToken = parser.nextToken();
											}
											l = null;
											ok2 = true;
											jsonToken = parser.nextToken(); // zatvara objekat labele
											jsonToken = parser.nextToken();
										}
										ok = true;

										jsonToken = parser.nextToken();
										jsonToken = parser.nextToken();
									}
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();

								}

							}
						}

					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				json = null;
			}
		}
	}
}
