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

import football.repository.LeagueRepo;
import football.repository.RoundRepo;
import model.League;
import model.Round;
@RestController
public class RoundController {

	private Param param = new Param();

	@Autowired
	LeagueRepo lr;
	@Autowired
	RoundRepo rr;
	
	
	@RequestMapping (value = "try2")
	public void apiRaund() {
		List<League> leagues = lr.findAll();
		
		for (League l : leagues) {
			if (l != null) {
				String json = null;

				try {
					HttpResponse<String> response = Unirest.get(param.getAdd() + "/fixtures/rounds/" + l.getIdLeague())
							.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2())
							.asString();
					json = response.getBody();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (json != null) {
					List<Round> runde = rr.findByLeague(l);
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
										jsonToken = parser.nextToken();
										Round r = new Round();
										r.setReguralSeason(parser.getValueAsString());
										if (!hasRound(runde, r)) {
											r.setLeague(l);
											rr.save(r);
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
	}

	private boolean hasRound(List<Round> rounds, Round round) {
		for (Round r : rounds) {
			if (r.getReguralSeason().equals(round.getReguralSeason())) {
				return true;
			}
		}
		rounds.add(round);
		return false;
	}

}
