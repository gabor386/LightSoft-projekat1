package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.TeamIn;

public interface TeaminRepo extends JpaRepository<TeamIn, Integer>{

}
