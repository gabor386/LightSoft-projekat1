package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Assist;
import model.Player;
import model.Season;
import model.Team;
import model.TeamPlayer;

public interface TeamplayerRepo extends JpaRepository<TeamPlayer, Integer>{

	List <TeamPlayer> findByPlayerAndTeam(Player player , Team team);
	
	List <TeamPlayer> findByPlayer(Player player);
	
	//List<Teamplayer> findByPlayerAndTeamAndSeason(Player p , Team t , Season s);
	
	public List<TeamPlayer> findByTeam(Team t);
}
