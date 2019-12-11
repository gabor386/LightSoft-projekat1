package football.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Country;
import model.Team;

public interface TeamRepo extends JpaRepository<Team, Integer>{
	List<Team> findByCountry(Country c);
	
	
}
