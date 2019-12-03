package football.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import model.Hometeam;


public interface HometeamRepo extends JpaRepository<Hometeam, Integer> {
	
}
