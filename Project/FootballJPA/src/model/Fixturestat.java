package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fixturestat database table.
 * 
 */
@Entity
@Table(name="FixtureStat")
@NamedQuery(name="Fixturestat.findAll", query="SELECT f FROM Fixturestat f")
public class Fixturestat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idFixtureStat;

	private String away;

	private String fixtureStatName;

	private String home;

	//bi-directional many-to-one association to Fixture
	@ManyToOne
	@JoinColumn(name="Fixtures_idFixtures")
	private Fixture fixture;

	public Fixturestat() {
	}

	public int getIdFixtureStat() {
		return this.idFixtureStat;
	}

	public void setIdFixtureStat(int idFixtureStat) {
		this.idFixtureStat = idFixtureStat;
	}

	public String getAway() {
		return this.away;
	}

	public void setAway(String away) {
		this.away = away;
	}

	public String getFixtureStatName() {
		return this.fixtureStatName;
	}

	public void setFixtureStatName(String fixtureStatName) {
		this.fixtureStatName = fixtureStatName;
	}

	public String getHome() {
		return this.home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public Fixture getFixture() {
		return this.fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

}