package football.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import model.HomeTeam;

public interface HometeamRepo extends JpaRepository<HomeTeam, Integer> {

	
}
