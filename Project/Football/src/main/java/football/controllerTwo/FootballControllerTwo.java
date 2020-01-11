package football.controllerTwo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import football.repository.LeagueRepo;
import model.League;

@CrossOrigin(origins="http://localhost:4200" )
@RestController
public class FootballControllerTwo {
	
	@Autowired
	LeagueRepo lr;

	@GetMapping(path="/league-bean")
	public  List<LeagueBean> getLeagueAllBean () {
		List<League> leagues = lr.findAll();
		
		List<LeagueBean> listBean = new ArrayList<LeagueBean>();
 		
		for(League l : leagues) {
		LeagueBean lb = new LeagueBean(l.getName() ,l.getIdLeague());
		listBean.add(lb);
		
		}
		return listBean ;
	}
	
	
}
