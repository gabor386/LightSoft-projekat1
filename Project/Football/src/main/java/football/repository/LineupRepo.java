package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Lineup;

public interface LineupRepo extends JpaRepository<Lineup, Integer>{

}
