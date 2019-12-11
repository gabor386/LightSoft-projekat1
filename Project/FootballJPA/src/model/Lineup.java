package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lineup database table.
 * 
 */
@Entity
@Table(name="LineUp")
@NamedQuery(name="Lineup.findAll", query="SELECT l FROM Lineup l")
public class Lineup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idLineUp;

	private String formation;

	//bi-directional many-to-one association to Team
	@ManyToOne
	private Team team;

	//bi-directional many-to-one association to Fixture
	@ManyToOne
	@JoinColumn(name="Fixtures_idFixtures")
	private Fixture fixture;

	//bi-directional many-to-one association to Startxi
	@OneToMany(mappedBy="lineup")
	private List<Startxi> startxis;

	//bi-directional many-to-one association to Substitute
	@OneToMany(mappedBy="lineup")
	private List<Substitute> substitutes;

	public Lineup() {
	}

	public int getIdLineUp() {
		return this.idLineUp;
	}

	public void setIdLineUp(int idLineUp) {
		this.idLineUp = idLineUp;
	}

	public String getFormation() {
		return this.formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Fixture getFixture() {
		return this.fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public List<Startxi> getStartxis() {
		return this.startxis;
	}

	public void setStartxis(List<Startxi> startxis) {
		this.startxis = startxis;
	}

	public Startxi addStartxi(Startxi startxi) {
		getStartxis().add(startxi);
		startxi.setLineup(this);

		return startxi;
	}

	public Startxi removeStartxi(Startxi startxi) {
		getStartxis().remove(startxi);
		startxi.setLineup(null);

		return startxi;
	}

	public List<Substitute> getSubstitutes() {
		return this.substitutes;
	}

	public void setSubstitutes(List<Substitute> substitutes) {
		this.substitutes = substitutes;
	}

	public Substitute addSubstitute(Substitute substitute) {
		getSubstitutes().add(substitute);
		substitute.setLineup(this);

		return substitute;
	}

	public Substitute removeSubstitute(Substitute substitute) {
		getSubstitutes().remove(substitute);
		substitute.setLineup(null);

		return substitute;
	}

}