package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.League;
import model.Stading;
import model.Team;

public interface StandingRepo extends JpaRepository<Stading, Integer>{

	List<Stading> findByLeague(League l);
	
	Stading findByLeagueAndTeam(League l, Team t);
	
}
