package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Event;

public interface EventRepo extends JpaRepository<Event, Integer>{

}
