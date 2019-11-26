package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Lastfivestat;

public interface LastfivestatRepo extends JpaRepository<Lastfivestat, Integer>{

}
