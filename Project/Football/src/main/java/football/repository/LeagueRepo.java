package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.League;

public interface LeagueRepo extends JpaRepository<League, Integer>{

}
