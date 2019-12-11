package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.TopScorer;

public interface TopscorerRepo extends JpaRepository<TopScorer, Integer>{

}
