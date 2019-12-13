package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Fixture;
import model.LineUp;

public interface LineupRepo extends JpaRepository<LineUp, Integer>{

	List<LineUp> findByFixture(Fixture f);
}
