package football.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import model.TimeZona;

public interface TimezonaRepo extends JpaRepository<TimeZona, Integer>{

	TimeZona findBytimeZone (String  t);
}

