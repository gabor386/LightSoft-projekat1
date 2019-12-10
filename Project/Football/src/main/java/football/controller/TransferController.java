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
import model.TeamIn;
import model.TeamOut;
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

	@RequestMapping(value = "/transfer")
	public void apiTransfer() {
		String json = null;
		List<Player> players = pr.findAll();
		List<Transfer> transferi = transr.findAll();

		for (Player p : players) {
			try {
				HttpResponse<String> response = Unirest.get(param.getAdd() + "/transfers/player/" + p.getIdPlayer())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
				json = response.getBody();

			} catch (UnirestException e) {
				e.printStackTrace();
			}

			if (json != null) {
				JSONParser parse = new JSONParser();
				JSONObject o;
				try {
					o = (JSONObject) parse.parse(json);
					JSONObject api = (JSONObject) o.get("api");
					JSONArray transfers = (JSONArray) api.get("transfers");
					for (int i = 0; i < transfers.size(); i++) {
						JSONObject transfer = (JSONObject) transfers.get(i);
						Transfer t = new Transfer();
						Long lu = (Long) transfer.get("lastUpdate");
						t.setLastUpdate(lu.intValue() + "");
						t.setType((String) transfer.get("type"));
						TeamIn ti = new TeamIn();
						JSONObject tio = (JSONObject) transfer.get("team_in");
						Long idTeamIn = (Long) tio.get("team_id");
						if (idTeamIn!=null) {
						Team teamIn = tr.getOne(idTeamIn.intValue());
						ti.setTeam(teamIn);
						ti = tir.save(ti);
						t.setTeamIn(ti);
						}
						TeamOut to = new TeamOut();
						JSONObject too = (JSONObject) transfer.get("team_out");
						Object idTeamOut = (Object) too.get("team_id");
						if (idTeamOut instanceof Long) {
							Long idTeamOutL = (Long) idTeamOut;
							Team teamOut = tr.getOne(idTeamOutL.intValue());
							to.setTeam(teamOut);
							to = tor.save(to);
							t.setTeamOut(to);
						}
						
						t.setPlayer(p);
						t.setTransferDate((String) transfer.get("transfer_date"));
						transr.save(t);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			json = null;
		}
	}

	public void saveTransfer(List<Transfer> transferi, Transfer t) {
		for (Transfer trans : transferi) {
			if (trans.getPlayer().getIdPlayer() == t.getPlayer().getIdPlayer()
					&& trans.getTeamIn().getIdTeamIn() == t.getTeamIn().getIdTeamIn()
					&& trans.getTransferDate().equals(t.getTransferDate())) {
				trans.setLastUpdate(t.getLastUpdate());
				transr.save(trans);
				return;
			}
		}
		t = transr.save(t);
		transferi.add(t);
	}
}
