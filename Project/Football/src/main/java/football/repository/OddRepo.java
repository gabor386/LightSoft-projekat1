package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Fixture;
import model.Odd;

public interface OddRepo extends JpaRepository<Odd, Integer>{
	Odd findByFixture(Fixture fixture);
}
