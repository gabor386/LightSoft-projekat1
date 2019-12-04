package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Team;
import model.Teamout;

public interface TeamoutRepo extends JpaRepository<Teamout, Integer>{
	List<Teamout> findByTeam(Team team);
}
