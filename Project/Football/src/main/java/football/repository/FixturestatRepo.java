package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Fixture;
import model.Fixturestat;

public interface FixturestatRepo extends JpaRepository<Fixturestat, Integer>{

	Fixturestat findByFixture (Fixture f);
	
}
