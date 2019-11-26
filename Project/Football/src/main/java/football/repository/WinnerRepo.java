package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Winner;

public interface WinnerRepo extends JpaRepository<Winner, Integer>{

}
