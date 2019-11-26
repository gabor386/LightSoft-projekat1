package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the stading database table.
 * 
 */
@Entity
@NamedQuery(name="Stading.findAll", query="SELECT s FROM Stading s")
public class Stading implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idStading;

	private String description;

	private String form;

	private int goalsDiff;

	private String group;

	private String lastUpdate;

	private int points;

	private int rank;

	//bi-directional many-to-one association to League
	@ManyToOne
	private League league;

	//bi-directional many-to-one association to Team
	@ManyToOne
	private Team team;

	//bi-directional many-to-one association to Standingstat
	@OneToMany(mappedBy="stading")
	private List<Standingstat> standingstats;

	public Stading() {
	}

	public int getIdStading() {
		return this.idStading;
	}

	public void setIdStading(int idStading) {
		this.idStading = idStading;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getForm() {
		return this.form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public int getGoalsDiff() {
		return this.goalsDiff;
	}

	public void setGoalsDiff(int goalsDiff) {
		this.goalsDiff = goalsDiff;
	}

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getRank() {
		return this.rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public League getLeague() {
		return this.league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<Standingstat> getStandingstats() {
		return this.standingstats;
	}

	public void setStandingstats(List<Standingstat> standingstats) {
		this.standingstats = standingstats;
	}

	public Standingstat addStandingstat(Standingstat standingstat) {
		getStandingstats().add(standingstat);
		standingstat.setStading(this);

		return standingstat;
	}

	public Standingstat removeStandingstat(Standingstat standingstat) {
		getStandingstats().remove(standingstat);
		standingstat.setStading(null);

		return standingstat;
	}

}