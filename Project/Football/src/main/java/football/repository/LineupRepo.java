package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.LineUp;

public interface LineupRepo extends JpaRepository<LineUp, Integer>{

}
