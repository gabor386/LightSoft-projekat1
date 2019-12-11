package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.LastFiveStat;

public interface LastfivestatRepo extends JpaRepository<LastFiveStat, Integer>{

}
