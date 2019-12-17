package football.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import model.Season;

public interface SeasonRepo extends JpaRepository<Season, Integer>{
	public Season findBySeason(int s);
	
}
