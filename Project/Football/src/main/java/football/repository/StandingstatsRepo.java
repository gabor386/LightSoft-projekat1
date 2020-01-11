package football.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Stading;
import model.StandingStat;

public interface StandingstatsRepo extends JpaRepository<StandingStat, Integer>{

	List<StandingStat> findByStading (Stading s);
}
