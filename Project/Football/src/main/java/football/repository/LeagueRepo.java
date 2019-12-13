package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.League;

public interface LeagueRepo extends JpaRepository<League, Integer>{
	List<League> findByName(String name);
}
