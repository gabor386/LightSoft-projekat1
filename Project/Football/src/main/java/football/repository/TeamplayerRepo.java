package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Assist;
import model.Player;
import model.Season;
import model.Team;
import model.Teamplayer;

public interface TeamplayerRepo extends JpaRepository<Teamplayer, Integer>{

	List <Teamplayer> findByPlayer(Player player);
	
	//List<Teamplayer> findByPlayerAndTeamAndSeason(Player p , Team t , Season s);
	
}
