package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Fixture;
import model.Score;

public interface ScoreRepo extends JpaRepository<Score, Integer>{
	Score findByFixture(Fixture fixture);
}
