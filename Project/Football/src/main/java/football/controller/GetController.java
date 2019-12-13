package football.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import football.repository.SeasonRepo;
import model.Season;

@RestController
public class GetController {

	@Autowired
	SeasonRepo sr;
	
	@GetMapping(value="get")
	public List<Season> getSeason(){
		List<Season> season=sr.findAll();
		for(Season s: season) {
			s.setTeamPlayers(null);
			s.setLeagues(null);
		}
		return season;
		
		
	}
}
