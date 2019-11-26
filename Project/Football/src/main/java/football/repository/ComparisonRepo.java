package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Comparison;

public interface ComparisonRepo extends JpaRepository<Comparison, Integer> {

}
