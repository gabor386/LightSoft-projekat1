package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Fixture;
import model.FixtureStat;

public interface FixturestatRepo extends JpaRepository<FixtureStat, Integer>{

	
	List<FixtureStat> findByFixture (Fixture f);
}
