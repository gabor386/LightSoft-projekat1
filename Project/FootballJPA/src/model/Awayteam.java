package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the awayteam database table.
 * 
 */
@Entity
@Table(name="AwayTeam")
@NamedQuery(name="Awayteam.findAll", query="SELECT a FROM Awayteam a")
public class Awayteam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idAwayTeam;

	//bi-directional many-to-one association to Team
	@ManyToOne
	private Team team;

	//bi-directional many-to-one association to Fixture
	@OneToMany(mappedBy="awayteam")
	private List<Fixture> fixtures;

	public Awayteam() {
	}

	public int getIdAwayTeam() {
		return this.idAwayTeam;
	}

	public void setIdAwayTeam(int idAwayTeam) {
		this.idAwayTeam = idAwayTeam;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<Fixture> getFixtures() {
		return this.fixtures;
	}

	public void setFixtures(List<Fixture> fixtures) {
		this.fixtures = fixtures;
	}

	public Fixture addFixture(Fixture fixture) {
		getFixtures().add(fixture);
		fixture.setAwayteam(this);

		return fixture;
	}

	public Fixture removeFixture(Fixture fixture) {
		getFixtures().remove(fixture);
		fixture.setAwayteam(null);

		return fixture;
	}

}