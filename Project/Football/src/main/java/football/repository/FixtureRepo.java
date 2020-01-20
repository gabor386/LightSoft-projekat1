package football.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Fixture;
import model.Round;
import modelA.FixtureA;


public interface FixtureRepo extends JpaRepository<Fixture, Integer>{
	List<Fixture> findByRound (Round r);
	
	
	@Query("select new modelA.FixtureA(f.idFixtures, f.eventDate, f.round.idRound, f.round.reguralSeason, f.homeTeam.team.idTeam, f.homeTeam.team.teamName, f.homeTeam.team.logo, f.awayTeam.team.idTeam, f.awayTeam.team.teamName, f.awayTeam.team.logo, f.score.fullTime, f.status, f.statusShort) from Fixture f where f.round.league.idLeague = :l")
	List<FixtureA> getAng( @Param("l") int l);
	
	
	//("SELECT new {javaPackagePath}.RuleVO(r.id, r.name) FROM Rul
}
