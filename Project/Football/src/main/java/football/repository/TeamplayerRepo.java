package football.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import model.Team;
import model.TeamPlayer;

public interface TeamplayerRepo extends JpaRepository<TeamPlayer, Integer>{
	public List<TeamPlayer> findByTeam(Team t);
}
