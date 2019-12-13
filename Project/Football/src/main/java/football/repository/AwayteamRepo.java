package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.AwayTeam;
import model.Team;

public interface AwayteamRepo extends JpaRepository<AwayTeam, Integer> {
	AwayTeam findByTeam(Team team);

}
