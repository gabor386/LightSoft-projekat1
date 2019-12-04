package football.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.repository.PlayerRepo;
import football.repository.TeamRepo;
import football.repository.TeaminRepo;
import football.repository.TeamoutRepo;
import football.repository.TransferRepo;
import model.Player;
import model.Team;
import model.Teamin;
import model.Teamout;
import model.Transfer;

@RestController
public class TransferController {

	Param param = new Param();

	@Autowired
	TransferRepo transr;
	@Autowired
	PlayerRepo pr;
	@Autowired
	TeamRepo tr;
	@Autowired
	TeaminRepo tir;
	@Autowired
	TeamoutRepo tor;

	public void apiTransfer() {
		String json = null;
		List<Player> players = pr.findAll();
		List<Transfer>transferi = transr.findAll();

		for (Player p : players) {
			try {
				HttpResponse<String> response = Unirest.get(param.getAdd() + "/transfers/player/" + p.getIdPlayer())
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
								for (int i = 0; i < br; i++) {
									Transfer t = new Transfer();
									t.setPlayer(p);
									for (int j = 0; j < 7; j++) {
										jsonToken = parser.nextToken();
									}
									t.setTransferDate(parser.getValueAsString());
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									t.setType(parser.getValueAsString());
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									Teamin ti = new Teamin();
									Team teamI = tr.getOne(parser.getIntValue());
									ti.setTeam(teamI);
									List<Teamin> til = tir.findByTeam(teamI);
									if (til.size() == 0) {
										ti = tir.save(ti);
									} else {
										ti = til.get(0);
									}
									for (int j = 0; j < 5; j++) {
										jsonToken = parser.nextToken();
									}
									t.setTeamin(ti);
									Teamout to = new Teamout();
									Team teamO = tr.getOne(parser.getIntValue());
									to.setTeam(teamO);
									List<Teamout> tol = tor.findByTeam(teamO);
									if (tol.size() == 0) {
										to = tor.save(to);
									} else {
										to = tol.get(0);
									}
									t.setTeamout(to);
									for (int j = 0; j < 7; j++) {
										jsonToken = parser.nextToken();
									}
									int lu = parser.getIntValue();
									String lus = lu+"";
									t.setLastUpdate(lus);
									saveTransfer(transferi, t);

								}
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			json = null;
		}
	}
	
	public void saveTransfer(List<Transfer> transferi, Transfer t) {
		for (Transfer trans:transferi) {
			if (trans.getPlayer().getIdPlayer()==t.getPlayer().getIdPlayer()
					&& trans.getTeamin().getIdTeamIn()==t.getTeamin().getIdTeamIn()
					&& trans.getTransferDate().equals(t.getTransferDate())) {
				trans.setLastUpdate(t.getLastUpdate());
				transr.save(trans);
				return;
			}
		}		
		t=transr.save(t);
		transferi.add(t);
	}
}
