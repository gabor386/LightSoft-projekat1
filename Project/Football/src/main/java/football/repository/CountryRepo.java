package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Country;

public interface CountryRepo extends JpaRepository<Country, Integer>{
	public Country findByName(String name);
}
