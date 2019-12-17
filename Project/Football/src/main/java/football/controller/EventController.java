package football.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.repository.AssistRepo;
import football.repository.EventRepo;
import football.repository.FixtureRepo;
import football.repository.PlayerRepo;
import football.repository.TeamRepo;
import football.repository.TeamplayerRepo;
import model.Assist;
import model.Event;
import model.Fixture;
import model.Player;
import model.Team;
import model.TeamPlayer;

@RestController
@RequestMapping(value = "/eventController")
public class EventController {

	private Param param = new Param();

	@Autowired
	EventRepo eventRepo;

	@Autowired
	TeamplayerRepo teamPlayerRepo;

	@Autowired
	PlayerRepo playerRepo;

	@Autowired
	FixtureRepo fixtureRepo;

	@Autowired
	AssistRepo assistRepo;

	@Autowired
	TeamRepo teamRepo;

	@RequestMapping(value = "/saveEvent", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public void apiFixtures() {

		List<Fixture> fixtures = fixtureRepo.findAll();

		for (Fixture f : fixtures) {


			List<Event> eventDelete = eventRepo.findByFixture(f);

			for (Event e : eventDelete) {
				
				eventRepo.delete(e);
				
				
				
				
				
			}

			System.out.println("Mecevi " + f.getIdFixtures());

			HttpResponse<String> response = null;
			try {
				response = Unirest.get(param.getAdd() + "/events/" + f.getIdFixtures())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
			} catch (UnirestException e1) {
				e1.printStackTrace();
			}

			String json = response.getBody();

			JSONParser parse = new JSONParser();
			JSONObject o;

			try {
				o = (JSONObject) parse.parse(json);
				JSONObject o1 = (JSONObject) o.get("api");

				JSONArray n1 = (JSONArray) o1.get("events");

				for (int i = 0; i < n1.size(); i++) {

					JSONObject o2 = (JSONObject) n1.get(i);

					Event event = new Event();

					Object elapsedLong = o2.get("elapsed");
					Integer elapsed = elapsedLong instanceof Long ? ((Long) elapsedLong).intValue() : 0;
					event.setElapsed(elapsed);

					String type = (String) o2.get("type") == null ? null : (String) o2.get("type");
					event.setType(type);

					event.setDetail((String) o2.get("detail") == null ? null : (String) o2.get("detail"));

					event.setFixture(f);

					Object playerLong = o2.get("player_id");
					Integer playerId = playerLong instanceof Long ? ((Long) playerLong).intValue() : 0;

					Player player = playerRepo.getOne(playerId);

					List<TeamPlayer> teamplayers = teamPlayerRepo.findByPlayer(player);

					for (TeamPlayer tp : teamplayers) {

						if (tp.getSeason().getSeason() == f.getRound().getLeague().getSeasonBean().getSeason()) {
							event.setTeamPlayer(tp);

						}
					}

					Object playerAssistLong = o2.get("assist_id");

					Integer playerAssistId = playerAssistLong instanceof Long ? ((Long) playerAssistLong).intValue()
							: 0;

					String playerAssistName = (String) o2.get("assist");

					Object teamAssistLong = o2.get("team_id");

					Integer teamAssistId = teamAssistLong instanceof Long ? ((Long) teamAssistLong).intValue() : 0;

					Team t = teamRepo.getOne(teamAssistId);

					if (  playerAssistId == 0 && playerAssistName != null) {
						List<TeamPlayer> teamPlayers2 = teamPlayerRepo.findByTeamAndSeason(t,
								f.getRound().getLeague().getSeasonBean());

						for (TeamPlayer t2 : teamPlayers2) {
							
							if (playerAssistName.equals(t2.getPlayer().getPlayerName())) {
								Assist assist = new Assist();
								assist.setTeamPlayer(t2);
								assist = assistRepo.save(assist);
								
							
								event.setAssist(assist);
								event = eventRepo.save(event);
								System.out.println("Uspesno  "+ assist.getIdAssist()+" "  + event.getIdEvent() +" "+event.getAssist().getIdAssist() );
								eventRepo.save(event);

							}
//							 else {
//								event.setAssist(null);
//								event = eventRepo.save(event);
//							}

						}

					} else {
						player = playerRepo.getOne(playerAssistId);

						List<TeamPlayer> teamPlayers2 = teamPlayerRepo.findByPlayerAndTeamAndSeason(player, t,
								f.getRound().getLeague().getSeasonBean());

						if (teamPlayers2.size() > 0) {

							TeamPlayer player2 = teamPlayers2.get(0);

							Assist assist = new Assist();
							assist.setTeamPlayer(player2);
							assist = assistRepo.save(assist);

							event.setAssist(assist);
							event = eventRepo.save(event);
						}

						else {
							event.setAssist(null);
							event = eventRepo.save(event);
						}
					}

					
				

				}

			} catch (ParseException e) {

				e.printStackTrace();
			}

			
		}

	}

}
