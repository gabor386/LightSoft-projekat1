package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.League;
import model.Round;

public interface RoundRepo extends JpaRepository<Round, Integer>{
	List<Round> findByLeague(League l);
}
