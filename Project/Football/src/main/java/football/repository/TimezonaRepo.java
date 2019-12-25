package football.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.TimeZona;

public interface TimezonaRepo extends JpaRepository<TimeZona, Integer>{

	List<TimeZona> findBytimeZone (String  t);
}

