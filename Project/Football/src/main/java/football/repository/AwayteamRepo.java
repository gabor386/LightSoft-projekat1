package football.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import model.Awayteam;


public interface AwayteamRepo extends JpaRepository<Awayteam, Integer>{
	
}
