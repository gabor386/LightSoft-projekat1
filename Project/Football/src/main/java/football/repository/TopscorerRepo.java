package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.League;
import model.TopScorer;

public interface TopscorerRepo extends JpaRepository<TopScorer, Integer>{

	List<TopScorer> findByLeague (League l);
}
