package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Team;

public interface TeamRepo extends JpaRepository<Team, Integer>{

}
