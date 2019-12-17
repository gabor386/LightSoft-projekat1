package football.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import model.Player;


public interface PlayerRepo extends JpaRepository<Player, Integer>{

	Player getOneByplayerName(String playerName);
	
	Player getOne (int idPlayer);
}
