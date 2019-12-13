package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Team;
import model.TeamIn;

public interface TeaminRepo extends JpaRepository<TeamIn, Integer>{
	List<TeamIn> findByTeam(Team team);
}
