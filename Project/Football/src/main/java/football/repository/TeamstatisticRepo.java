package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.League;
import model.Team;
import model.TeamStatistic;

public interface TeamstatisticRepo extends JpaRepository<TeamStatistic, Integer>{

	List <TeamStatistic> findByTeamAndLeague(Team t, League l);
	
}
