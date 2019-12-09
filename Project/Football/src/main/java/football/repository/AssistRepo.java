package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Assist;
import model.Country;
import model.Team;
import model.Teamplayer;

public interface AssistRepo extends JpaRepository<Assist, Integer> {

	
	
}
