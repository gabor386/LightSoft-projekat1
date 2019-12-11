package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Event;
import model.Fixture;

public interface EventRepo extends JpaRepository<Event, Integer>{

	List<Event> findByFixture (Fixture f);
	
}
