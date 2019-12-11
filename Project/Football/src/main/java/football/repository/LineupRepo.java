package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Fixture;
import model.Lineup;

public interface LineupRepo extends JpaRepository<Lineup, Integer>{

	List<Lineup> findByFixture(Fixture f);
}
