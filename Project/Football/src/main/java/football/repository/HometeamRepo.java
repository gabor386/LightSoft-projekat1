package football.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import model.HomeTeam;
import model.Team;

public interface HometeamRepo extends JpaRepository<HomeTeam, Integer> {

		HomeTeam findByTeam(Team team);
	
}
