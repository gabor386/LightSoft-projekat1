package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Coach;
import model.Team;

public interface CoachRepo extends JpaRepository<Coach, Integer>{
	
	List<Coach> findByTeam(Team t);
}
