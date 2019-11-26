package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Round;

public interface RoundRepo extends JpaRepository<Round, Integer>{

}
