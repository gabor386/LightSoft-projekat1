package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Team;
import model.Teamin;

public interface TeaminRepo extends JpaRepository<Teamin, Integer>{
	List<Teamin> findByTeam(Team team);
}
