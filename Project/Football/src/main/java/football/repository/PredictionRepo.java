package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Prediction;

public interface PredictionRepo extends JpaRepository<Prediction, Integer>{

}
