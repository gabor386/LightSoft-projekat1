package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Season;

public interface SeasonRepo extends JpaRepository<Season, Integer>{

}
