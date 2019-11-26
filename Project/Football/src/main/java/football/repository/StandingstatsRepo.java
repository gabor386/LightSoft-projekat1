package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Standingstat;

public interface StandingstatsRepo extends JpaRepository<Standingstat, Integer>{

}
