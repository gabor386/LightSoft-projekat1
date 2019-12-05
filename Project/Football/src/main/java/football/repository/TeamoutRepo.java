package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Team;
import model.TeamOut;

public interface TeamoutRepo extends JpaRepository<TeamOut, Integer>{
	List<TeamOut> findByTeam(Team team);
}
