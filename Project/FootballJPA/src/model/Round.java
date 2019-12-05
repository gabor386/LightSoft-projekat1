package model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


/**
 * The persistent class for the round database table.
 * 
 */
@Entity
@Table(name="Round")
@NamedQuery(name="Round.findAll", query="SELECT r FROM Round r")
public class Round implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idRound;

	@JsonProperty("round")
	private String reguralSeason;

	//bi-directional many-to-one association to Fixture
	@OneToMany(mappedBy="round")
	private List<Fixture> fixtures;

	//bi-directional many-to-one association to League
	@ManyToOne
	private League league;

	public Round() {
	}

	public int getIdRound() {
		return this.idRound;
	}

	public void setIdRound(int idRound) {
		this.idRound = idRound;
	}

	public String getReguralSeason() {
		return this.reguralSeason;
	}

	public void setReguralSeason(String reguralSeason) {
		this.reguralSeason = reguralSeason;
	}

	public List<Fixture> getFixtures() {
		return this.fixtures;
	}

	public void setFixtures(List<Fixture> fixtures) {
		this.fixtures = fixtures;
	}

	public Fixture addFixture(Fixture fixture) {
		getFixtures().add(fixture);
		fixture.setRound(this);

		return fixture;
	}

	public Fixture removeFixture(Fixture fixture) {
		getFixtures().remove(fixture);
		fixture.setRound(null);

		return fixture;
	}

	public League getLeague() {
		return this.league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

}