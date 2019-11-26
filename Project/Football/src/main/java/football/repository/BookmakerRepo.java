package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Bookmaker;

public interface BookmakerRepo extends JpaRepository<Bookmaker, Integer>{

}
