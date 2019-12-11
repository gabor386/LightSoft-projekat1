package model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


/**
 * The persistent class for the Score database table.
 * 
 */
@Entity
@NamedQuery(name="Score.findAll", query="SELECT s FROM Score s")
public class Score implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idScore;

	@JsonProperty("extratime")
	private String extraTime;

	@JsonProperty("fulltime")
	private String fullTime;

	@JsonProperty("halftime")
	private String halfTime;

	private String penalty;

	//bi-directional many-to-one association to Fixture
	@OneToMany(mappedBy="score")
	private List<Fixture> fixtures;

	public Score() {
	}

	public int getIdScore() {
		return this.idScore;
	}

	public void setIdScore(int idScore) {
		this.idScore = idScore;
	}

	public String getExtraTime() {
		return this.extraTime;
	}

	public void setExtraTime(String extraTime) {
		this.extraTime = extraTime;
	}

	public String getFullTime() {
		return this.fullTime;
	}

	public void setFullTime(String fullTime) {
		this.fullTime = fullTime;
	}

	public String getHalfTime() {
		return this.halfTime;
	}

	public void setHalfTime(String halfTime) {
		this.halfTime = halfTime;
	}

	public String getPenalty() {
		return this.penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public List<Fixture> getFixtures() {
		return this.fixtures;
	}

	public void setFixtures(List<Fixture> fixtures) {
		this.fixtures = fixtures;
	}

	public Fixture addFixture(Fixture fixture) {
		getFixtures().add(fixture);
		fixture.setScore(this);

		return fixture;
	}

	public Fixture removeFixture(Fixture fixture) {
		getFixtures().remove(fixture);
		fixture.setScore(null);

		return fixture;
	}

}