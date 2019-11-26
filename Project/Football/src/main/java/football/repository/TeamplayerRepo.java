package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Teamplayer;

public interface TeamplayerRepo extends JpaRepository<Teamplayer, Integer>{

}
