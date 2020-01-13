package football.controllerTwo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import football.repository.LeagueRepo;
import football.repository.StandingRepo;
import model.League;
import model.Stading;
import modelA.LeagueBean;
import modelA.StadingBean;

@CrossOrigin(origins="http://localhost:4200" )
@RestController
public class FootballControllerTwo {
	
	@Autowired
	LeagueRepo lr;
	
	@Autowired
	StandingRepo sr;

	@GetMapping(path="/league-bean")
	public  List<LeagueBean> getLeagueAllBean () {
		List<League> leagues = lr.findAll();
		
		List<LeagueBean> listBean = new ArrayList<LeagueBean>();
 		
		for(League l : leagues) {
		LeagueBean lb = new LeagueBean(l.getName() ,l.getIdLeague() , l.getLogo());
		listBean.add(lb);
		
		}
		return listBean ;
	}
	
	@GetMapping(path="/statding-bean")
	public  List<StadingBean> getStadingAllBean () {
		List<Stading> stadings = sr.findAll();
		
		List<StadingBean> stadingBean = new ArrayList<StadingBean>();
 		
		for(Stading s : stadings) {
		StadingBean sa = new StadingBean(s.getTeam().getIdTeam(),s.getTeam().getTeamName(),s.getForm(),s.getGroup(),s.getPoints(), s.getLeague().getIdLeague() , s.getTeam().getLogo());
		stadingBean.add(sa);
		
		}
		return stadingBean ;
	}
	
	
	
	@RequestMapping(value="/statding-bean/{idLeague}", method= RequestMethod.GET)
	public  List<StadingBean> getStadingAllBean (@PathVariable Integer idLeague) {
		League l =  lr.getOne(idLeague);
		List<Stading> stading = sr.findByLeague(l);
		
		List<StadingBean> list = new ArrayList<StadingBean>();
 		
		for(Stading s : stading) {
		StadingBean sa = new StadingBean(s.getTeam().getIdTeam(),s.getTeam().getTeamName(),s.getForm(),s.getGroup(),s.getPoints(), s.getLeague().getIdLeague() , s.getTeam().getLogo());
		list.add(sa);
		
		}
		return list ;
	}
	
	
	
}
