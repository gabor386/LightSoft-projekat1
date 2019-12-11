package football.repository;



import org.springframework.data.jpa.repository.JpaRepository;


import model.Fixture;


public interface FixtureRepo extends JpaRepository<Fixture, Integer>{

}
