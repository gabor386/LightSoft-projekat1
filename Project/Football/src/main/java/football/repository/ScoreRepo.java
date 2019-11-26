package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Score;

public interface ScoreRepo extends JpaRepository<Score, Integer>{

}
