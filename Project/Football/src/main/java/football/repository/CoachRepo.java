package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Coach;

public interface CoachRepo extends JpaRepository<Coach, Integer>{

}
