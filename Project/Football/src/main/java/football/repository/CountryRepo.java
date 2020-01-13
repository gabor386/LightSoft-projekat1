package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Country;
import model.Team;

public interface CountryRepo extends JpaRepository<Country, Integer>{
	Country findByName(String name);
	
}
