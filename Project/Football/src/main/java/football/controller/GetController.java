package football.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import football.repository.CoachRepo;
import football.repository.CountryRepo;
import football.repository.PlayerRepo;
import football.repository.SeasonRepo;
import football.repository.TeamRepo;
import football.repository.TeamplayerRepo;
import model.Coach;
import model.Country;
import model.Player;
import model.PlayerStatistic;
import model.Season;
import model.Team;
import model.TeamPlayer;


@CrossOrigin("http://localhost:4200")
@RestController
public class GetController {

	@Autowired
	SeasonRepo sr;
	
	@Autowired
	TeamRepo tr;
	
	@Autowired
	TeamplayerRepo tpr;
	
	@GetMapping(value="season" )
	public List<Season> getSeason(){
		List<Season> season=sr.findAll();
		for(Season s: season) {
			s.setTeamPlayers(null);
			s.setLeagues(null);
		}
		return season;

	}
	@Autowired
	PlayerRepo pr;
	
	@Autowired
	CoachRepo cr;
	
	@Autowired
	CountryRepo countryRepo;
	
	@GetMapping(value="team/{id}")
	public Team getTeam(@PathVariable("id") Integer id){
		Team team=tr.findById(id).get();
		
		
		Season s=sr.getOne(2019);
		List<TeamPlayer> teamPlayer=tpr.findByTeamAndSeason(team, s);
		List<TeamPlayer> players=new ArrayList<TeamPlayer>();
		
		//Coach object
		List<Coach> coach=cr.findByTeam(team);
		List<Coach> coachList=new ArrayList<Coach>();
		for(Coach c : coach ) {
			Coach coachObj=new Coach();
			c.setCareers(null);
			c.setSideLines(null);
			c.setCountry(null);
			c.setTeam(null);
			c.setTrophies(null);
			coachList.add(c);
		}
		
		//TeamPlayer- Player object
		for(TeamPlayer teamP: teamPlayer) {
				TeamPlayer tp=new TeamPlayer();	
				teamP.getPlayer().setTeamPlayers(null);
				teamP.getPlayer().setTransfers(null);
				teamP.getPlayer().setWinners(null);
				teamP.getPlayer().setTeamPlayers(null);
				
				tp.setPlayer(teamP.getPlayer());
				players.add(tp);
			
		}
		if(team.getCountry()!=null) {
		team.getCountry().setCoaches(null);
		team.getCountry().setLeagues(null);
		team.getCountry().setTeams(null);
		}
		team.setTeamPlayers(players);
		team.setAwayTeams(null);
		team.setCareers(null);
		
		team.setCoaches(coachList);
		team.setHomeTeams(null);
		team.setLastFiveStats(null);
		team.setLineUps(null);
		team.setStadings(null);
		team.setTeamIns(null);
		team.setTeamOuts(null);
		team.setTeamStatistics(null);
		
		return team;
	}
}
