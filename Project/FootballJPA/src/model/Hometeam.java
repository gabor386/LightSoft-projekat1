package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the hometeam database table.
 * 
 */
@Entity
@Table(name="HomeTeam")
@NamedQuery(name="Hometeam.findAll", query="SELECT h FROM Hometeam h")
public class Hometeam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idHomeTeam;

	//bi-directional many-to-one association to Fixture
	@OneToMany(mappedBy="hometeam")
	private List<Fixture> fixtures;

	//bi-directional many-to-one association to Team
	@ManyToOne
	private Team team;

	public Hometeam() {
	}

	public int getIdHomeTeam() {
		return this.idHomeTeam;
	}

	public void setIdHomeTeam(int idHomeTeam) {
		this.idHomeTeam = idHomeTeam;
	}

	public List<Fixture> getFixtures() {
		return this.fixtures;
	}

	public void setFixtures(List<Fixture> fixtures) {
		this.fixtures = fixtures;
	}

	public Fixture addFixture(Fixture fixture) {
		getFixtures().add(fixture);
		fixture.setHometeam(this);

		return fixture;
	}

	public Fixture removeFixture(Fixture fixture) {
		getFixtures().remove(fixture);
		fixture.setHometeam(null);

		return fixture;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}