package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Fixture;
import model.FixtureStat;

public interface FixturestatRepo extends JpaRepository<FixtureStat, Integer>{

	FixtureStat findByFixture (Fixture f);
	
}
