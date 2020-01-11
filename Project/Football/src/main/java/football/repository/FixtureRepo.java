package football.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import model.Fixture;
import model.Round;


public interface FixtureRepo extends JpaRepository<Fixture, Integer>{
	List<Fixture> findByRound (Round r);
}
