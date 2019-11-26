package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Bet;

public interface BetRepo extends JpaRepository<Bet, Integer>{

}
