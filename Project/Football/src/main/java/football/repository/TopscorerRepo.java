package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Topscorer;

public interface TopscorerRepo extends JpaRepository<Topscorer, Integer>{

}
