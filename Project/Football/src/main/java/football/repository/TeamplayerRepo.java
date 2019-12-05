package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.TeamPlayer;

public interface TeamplayerRepo extends JpaRepository<TeamPlayer, Integer>{

}
