package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.StandingStat;

public interface StandingstatsRepo extends JpaRepository<StandingStat, Integer>{

}
