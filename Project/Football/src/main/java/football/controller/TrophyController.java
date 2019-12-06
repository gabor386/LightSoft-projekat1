package football.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.api.Param;
import football.repository.CoachRepo;
import football.repository.LeagueRepo;
import football.repository.PlayerRepo;
import football.repository.TeamplayerRepo;
import football.repository.TrophyRepo;
import football.repository.WinnerRepo;
import model.Coach;
import model.League;
import model.Player;
import model.SideLine;
import model.TeamPlayer;
import model.Trophy;
import model.Winner;

@RestController
public class TrophyController {

	Param param = new Param();

	@Autowired
	CoachRepo cr;
	@Autowired
	PlayerRepo pr;
	@Autowired
	WinnerRepo wr;
	@Autowired
	TrophyRepo tr;
	@Autowired
	LeagueRepo lr;

	
	@RequestMapping (value = "/trophy")
	public void apiTrophy() {
		String json = null;
		List<Player> tps = pr.findAll();
		List<Coach> cs = cr.findAll();
		List<Trophy> tropies = tr.findAll();
		List<Winner> ws = wr.findAll();

		for (Player p : tps) {
			try {
				HttpResponse<String> response = Unirest.get(param.getAdd() + "/trophies/player/" + p.getIdPlayer())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
				json = response.getBody();
				System.out.println(json);
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
									Trophy t = new Trophy();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									List<League> ls = lr.findByName(parser.getValueAsString());
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									League l = tropheyLeague(ls, parser.getValueAsString());
									t.setLeague(l);
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									t.setPlace(parser.getValueAsString());
									jsonToken = parser.nextToken();
									Trophy tt = hasTrophy(tropies, t);
									if (tt == null) {
										tt = tr.save(t);
									}
									Winner w = new Winner();
									w.setPlayer(p);
									w.setTrophy(tt);
									if (!hasWinner(ws, w)) {
										wr.save(w);
									}
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
		for (Coach c : cs) {
			try {
				HttpResponse<String> response = Unirest.get(param.getAdd() + "/trophies/coach/" + c.getIdCoach())
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
									Trophy t = new Trophy();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									List<League> ls = lr.findByName(parser.getValueAsString());
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									League l = tropheyLeague(ls, parser.getValueAsString());
									t.setLeague(l);
									jsonToken = parser.nextToken();
									jsonToken = parser.nextToken();
									t.setPlace(parser.getValueAsString());
									jsonToken = parser.nextToken();
									Trophy tt = hasTrophy(tropies, t);
									if (tt == null) {
										tt = tr.save(t);
									}
									tt.setCoach(c);
									tr.save(tt);
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

	public League tropheyLeague(List<League> ls, String s) {
		String[] t = s.split("");
		String f4 = t[0] + t[1] + t[2] + t[3];
		try {
			int season = Integer.parseInt(f4);
			for (League l : ls) {
				if (l.getSeasonBean().getSeason() == season) {
					return l;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Trophy hasTrophy(List<Trophy> ts, Trophy trophy) {
		for (Trophy t : ts) {
			if (t.getLeague().getName().equals(trophy.getLeague().getName())
					&& t.getLeague().getSeasonBean().getSeason() == trophy.getLeague().getSeasonBean().getSeason()
					&& t.getPlace().equals(trophy.getPlace())) {
				return t;
			}
		}
		return null;
	}

	public boolean hasWinner(List<Winner> ws, Winner win) {
		for (Winner w : ws) {
			if (w.getTrophy().getIdTrophy() == win.getTrophy().getIdTrophy()
					&& win.getPlayer().getIdPlayer() == w.getPlayer().getIdPlayer()) {
				return true;
			}
		}
		ws.add(win);
		return false;
	}

}
