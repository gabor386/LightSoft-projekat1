package football.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.repository.LeagueRepo;
import football.repository.TeamRepo;
import football.repository.TeamstatisticRepo;
import model.League;
import model.Team;
import model.TeamStatistic;

@RestController
@RequestMapping(value = "/TeamStatControler")
public class TeamStatisticController {

	private Param param = new Param();

	@Autowired
	TeamstatisticRepo teamStatRepo;

	@Autowired
	TeamRepo teamRepo;

	@Autowired
	LeagueRepo leagueRepo;

	@RequestMapping(value = "/saveTeamStat", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public void apiTeamStat() {

		List<League> leagues = leagueRepo.findAll();
		List<Team> teams = teamRepo.findAll();

		for (League l : leagues) {

			for (Team t : teams) {

				List<TeamStatistic> teamStatDelete = teamStatRepo.findByTeamAndLeague(t, l);

				for (TeamStatistic ts : teamStatDelete) {
					teamStatRepo.delete(ts);
				}

				HttpResponse<String> response = null;
				try {
					System.out.println("league " + l.getIdLeague() + " team " + t.getIdTeam());
					response = Unirest.get(param.getAdd() + "/statistics/" + l.getIdLeague() + "/" + t.getIdTeam())
							.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2())
							.asString();
				} catch (UnirestException e1) {
					e1.printStackTrace();
				}

				String json = response.getBody();

				JSONParser parse = new JSONParser();
				JSONObject o;

				try {

					o = (JSONObject) parse.parse(json);
					JSONObject o1 = (JSONObject) o.get("api");

					JSONObject o2 = (JSONObject) o1.get("statistics");

					JSONObject o3 = (JSONObject) o2.get("matchs");
					// TeamStatistic teamStat = new TeamStatistic();
					TeamStatistic teamStatMP = new TeamStatistic();

					teamStatMP.setTeam(t);

					teamStatMP.setLeague(l);

					JSONObject o4 = (JSONObject) o3.get("matchsPlayed");

					// TeamStatistic teamStatMP = new TeamStatistic();
					// odigrani mecevi
					teamStatMP.setStatName("matchsPlayed");

				
					String h =  (Long)o4.get("home") + "";
					teamStatMP.setHome(h);

					String a = (Long) o4.get("away") + "";
					teamStatMP.setAway(a);
					
			

					String tot = (Long) o4.get("total") + "";
					teamStatMP.setTotal(tot);
				

					if (!tot.equals("0")) {

					teamStatRepo.save(teamStatMP);
					
					
					JSONObject obWins = (JSONObject) o3.get("wins");

					TeamStatistic teamStatWins = new TeamStatistic();
					// pobede
					teamStatWins.setStatName("wins");

					 h  = (Long)obWins.get("home")+ "";
					teamStatWins.setHome(h);

					 a = (Long)obWins.get("away") + "";
					teamStatWins.setAway(a);

					tot = (Long)obWins.get("total")+ "";
					teamStatWins.setTotal(tot);
					
					teamStatWins.setTeam(t);

					teamStatWins.setLeague(l);


					teamStatRepo.save(teamStatWins);
					

					JSONObject obDraws = (JSONObject) o3.get("draws");
					
					TeamStatistic teamStatDraws = new TeamStatistic();
					// neresene
					teamStatDraws.setStatName("draws");


					 h  = (Long)obDraws.get("home")+ "";
					teamStatDraws.setHome(h);

					 a = (Long)obDraws.get("away") + "";
					teamStatDraws.setAway(a);

					tot = (Long)obDraws.get("total")+ "";
					teamStatDraws.setTotal(tot);
					
					teamStatDraws.setTeam(t);

					teamStatDraws.setLeague(l);


					teamStatRepo.save(teamStatDraws);
					

					JSONObject obLoses = (JSONObject) o3.get("loses");
					
					TeamStatistic teamStatLoses = new TeamStatistic();
					// izgubljene
					teamStatLoses.setStatName("loses");

					 h  = (Long)obLoses.get("home")+ "";
					teamStatLoses.setHome(h);

					 a = (Long)obLoses.get("away") + "";
					teamStatLoses.setAway(a);

					tot = (Long)obLoses.get("total")+ "";
					teamStatLoses.setTotal(tot);
					
					teamStatLoses.setTeam(t);

					teamStatLoses.setLeague(l);


					teamStatRepo.save(teamStatLoses);

					
					
					
					JSONObject o5 = (JSONObject) o2.get("goals");
					
					JSONObject obGF = (JSONObject) o5.get("goalsFor");
					
					TeamStatistic teamStatGF = new TeamStatistic();	
					// dati golovi
					teamStatGF.setStatName("goalsFor");

					h = (Long) obGF.get("home") + "";
					teamStatGF.setHome(h);

					a = (Long) obGF.get("away") + "";
					teamStatGF.setAway(a);

					tot = (Long) obGF.get("total") + "";
					teamStatGF.setTotal(tot);
					
					teamStatGF.setTeam(t);

					teamStatGF.setLeague(l);


					teamStatRepo.save(teamStatGF);

					
					JSONObject obGA = (JSONObject) o5.get("goalsAgainst");
					
					TeamStatistic teamStatGA = new TeamStatistic();
					// primljeni golovi
					teamStatGA.setStatName("goalsAgainst");

					h = (Long) obGA.get("home") + "";
					teamStatGA.setHome(h);

					a = (Long) obGA.get("away") + "";
					teamStatGA.setAway(a);

					tot = (Long) obGA.get("total") + "";
					teamStatGA.setTotal(tot);
					
					teamStatGA.setTeam(t);

					teamStatGA.setLeague(l);


					teamStatRepo.save(teamStatGA);

					
					
					
					JSONObject o6 = (JSONObject) o2.get("goalsAvg");
					
					JSONObject obGFA = (JSONObject) o6.get("goalsFor");
					
					TeamStatistic teamStatGFA = new TeamStatistic();
					// dati golovi po mecu
					teamStatGFA.setStatName("goalsFor");
					
					String homeAvg = (String) obGFA.get("home");
					teamStatGFA.setHome(homeAvg);

					String awayAvg = (String) obGFA.get("away");
					teamStatGFA.setAway(awayAvg);

					String totalAvg = (String) obGFA.get("total");
					teamStatGFA.setTotal(totalAvg);
					
					teamStatGFA.setTeam(t);

					teamStatGFA.setLeague(l);


					teamStatRepo.save(teamStatGFA);
					
					
					JSONObject obGAA = (JSONObject) o6.get("goalsAgainst");
					
					TeamStatistic teamStatGAA = new TeamStatistic();
					// primljeni golovi po mecu
					teamStatGAA.setStatName("goalsAgainst");

					String homeAvgA = (String) obGAA.get("home");
					teamStatGAA.setHome(homeAvgA);

					String awayAvgA = (String) obGAA.get("away");
					teamStatGAA.setAway(awayAvgA);

					String totalAvgA = (String) obGAA.get("total");
					teamStatGAA.setTotal(totalAvgA);
					
					teamStatGAA.setTeam(t);

					teamStatGAA.setLeague(l);


					 teamStatRepo.save(teamStatGAA);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
