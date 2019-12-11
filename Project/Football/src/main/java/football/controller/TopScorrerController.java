package football.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.repository.LeagueRepo;
import football.repository.TeamRepo;
import football.repository.TeamplayerRepo;
import football.repository.TopscorerRepo;
import model.League;
import model.Team;
import model.TeamPlayer;
import model.TopScorer;

@RestController
public class TopScorrerController {

	@Autowired
	TopscorerRepo tsr;
	
	@Autowired
	LeagueRepo lr;
	
	@Autowired
	TeamRepo tr;

	@Autowired
	TeamplayerRepo tpr;
	
	private Param param = new Param();

	@RequestMapping(value = "topscorrer")
	public List<TopScorer> getApiTopScorrer() {

		List<TopScorer> listaTopScorrera = new ArrayList<TopScorer>();

		List<League> league = lr.findAll();

		String json = "";

		for (League l : league) {
			HttpResponse<String> response;
			try {
				response = Unirest.get(param.getAdd() + "/topscorers/" + l.getIdLeague())
						.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
				json = response.getBody();

			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				JSONParser parse = new JSONParser();
				JSONObject o;

				o = (JSONObject) parse.parse(json);

				JSONObject o1 = (JSONObject) o.get("api");

				Long result = (Long) o1.get("results");

				JSONArray nizPlayers = (JSONArray) o1.get("topscorers");
				for (int i = 0; i < nizPlayers.size(); i++) {
					JSONObject objectPlayers = (JSONObject) nizPlayers.get(i);

					TopScorer topScorer=new TopScorer();
					
					//player
					Long playerIdPom=(Long)objectPlayers.get("player_id");
					Integer playerId=playerIdPom.intValue();
					
					//league
					topScorer.setLeague(l);
					
					//team
					Long teamPom=(Long) objectPlayers.get("team_id");
					Team team=tr.getOne(teamPom.intValue());
					
					List<TeamPlayer> teamPlayerList=tpr.findByTeam(team);
					
					for(TeamPlayer teamP: teamPlayerList) {
						if(teamP.getPlayer().getIdPlayer() == playerId) {
							topScorer.setTeamPlayer(teamP);
						}
						
					}
					
					tsr.save(topScorer);
					
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			System.out.println(json);
		}
		return null;

	}

}
