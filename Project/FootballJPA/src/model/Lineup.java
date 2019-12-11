package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the LineUp database table.
 * 
 */
@Entity
@NamedQuery(name="LineUp.findAll", query="SELECT l FROM LineUp l")
public class LineUp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idLineUp;

	private String formation;

	//bi-directional many-to-one association to Fixture
	@ManyToOne
	@JoinColumn(name="Fixtures_idFixtures")
	private Fixture fixture;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="Team_idTeam")
	private Team team;

	//bi-directional many-to-one association to StartXI
	@OneToMany(mappedBy="lineUp")
	private List<StartXI> startXis;

	//bi-directional many-to-one association to Substitute
	@OneToMany(mappedBy="lineUp")
	private List<Substitute> substitutes;

	public LineUp() {
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

	public Fixture getFixture() {
		return this.fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<StartXI> getStartXis() {
		return this.startXis;
	}

	public void setStartXis(List<StartXI> startXis) {
		this.startXis = startXis;
	}

	public StartXI addStartXi(StartXI startXi) {
		getStartXis().add(startXi);
		startXi.setLineUp(this);

		return startXi;
	}

	public StartXI removeStartXi(StartXI startXi) {
		getStartXis().remove(startXi);
		startXi.setLineUp(null);

		return startXi;
	}

	public List<Substitute> getSubstitutes() {
		return this.substitutes;
	}

	public void setSubstitutes(List<Substitute> substitutes) {
		this.substitutes = substitutes;
	}

	public Substitute addSubstitute(Substitute substitute) {
		getSubstitutes().add(substitute);
		substitute.setLineUp(this);

		return substitute;
	}

	public Substitute removeSubstitute(Substitute substitute) {
		getSubstitutes().remove(substitute);
		substitute.setLineUp(null);

		return substitute;
	}

}