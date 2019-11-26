package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Career;

public interface CareerRepo extends JpaRepository<Career, Integer>{
	
}
