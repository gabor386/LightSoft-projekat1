package football.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
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
import javassist.expr.Instanceof;
import model.Bet;
import model.Bookmaker;
import model.Career;
import model.Coach;
import model.Fixture;
import model.Label;
import model.Odd;

@RestController
public class OddsKontroller {

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

	@RequestMapping(value = "/odds")
	public void apiOdds() {
		System.out.println("ODDS UPDATE");
		String json = null;
		List<Fixture> fs = fr.findAll();

		for (Fixture f : fs) {

			try {
				HttpResponse<String> response = Unirest.get(param.getAdd() + "/odds/fixture/" + f.getIdFixtures())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
				json = response.getBody();
				System.out.println(f.getIdFixtures());

			} catch (UnirestException e) {
				e.printStackTrace();
			}

			if (json != null) {

				JSONParser parse = new JSONParser();
				JSONObject o;
				try {
					o = (JSONObject) parse.parse(json);

					JSONObject o1 = (JSONObject) o.get("api");

					Long result = (Long) o1.get("results");
					Integer br = result.intValue();

					JSONArray odds = (JSONArray) o1.get("odds");
					for (int i = 0; i < result; i++) {
						JSONObject objectOdds = (JSONObject) odds.get(i);
						// da ne ubacuje dva puta isto
						Odd oddDelete = or.findByFixture(f);
						if (oddDelete!=null)
							or.delete(oddDelete);
						Odd odd = new Odd();
						odd.setFixture(f);
						odd = or.save(odd);

						JSONArray bookmakers = (JSONArray) objectOdds.get("bookmakers");

						for (int j = 0; j < bookmakers.size(); j++) {
							JSONObject bm = (JSONObject) bookmakers.get(j);
							Long idBm = (Long) bm.get("bookmaker_id");
							Bookmaker bookmaker = bmr.getOne(idBm.intValue());
							JSONArray labels = (JSONArray) bm.get("bets");
							for (int z = 0; z < labels.size(); z++) {
								JSONObject lb = (JSONObject) labels.get(z);
								Long idLb = (Long) lb.get("label_id");
								Label label = lr.getOne(idLb.intValue());

								JSONArray bets = (JSONArray) lb.get("values");

								for (int y = 0; y < bets.size(); y++) {
									JSONObject bet = (JSONObject) bets.get(y);
									Bet b = new Bet();
									b.setOddBean(odd);
									b.setLabel(label);
									b.setBookmaker(bookmaker);
									Object betValue = (Object) bet.get("value");
									Object betOdds = (Object) bet.get("value");
									if (betValue instanceof Long) {
										Long lbv = (Long) betValue;
										String betValueStr = betValue + "";
										b.setBetValues(betValueStr);
									} else {
										String betValueStr = (String) betValue;
										b.setBetValues(betValueStr);
									}if (betOdds instanceof Long) {
										Long lo = (Long) betValue;
										String bos = lo + "";
										b.setOdd(bos);
									} else {
										String bos = (String) betValue;
										b.setOdd(bos);
									}

									
									betr.save(b);
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			json = null;
		}
	}
}
