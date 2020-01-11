package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.PlayerStatistic;
import model.TeamPlayer;

public interface PlayerstatisticRepo extends JpaRepository<PlayerStatistic, Integer>{

	List<PlayerStatistic> findByTeamPlayer(TeamPlayer tp);
}
