package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Stading database table.
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

	private String groupa;

	private String lastUpdate;

	private int points;

	

	//bi-directional many-to-one association to League
	@ManyToOne
	@JoinColumn(name="League_idLeague")
	private League league;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="Team_idTeam")
	private Team team;

	//bi-directional many-to-one association to StandingStat
	@OneToMany(mappedBy="stading")
	private List<StandingStat> standingStats;

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
		return this.groupa;
	}

	public void setGroup(String group) {
		this.groupa = group;
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

	public List<StandingStat> getStandingStats() {
		return this.standingStats;
	}

	public void setStandingStats(List<StandingStat> standingStats) {
		this.standingStats = standingStats;
	}

	public StandingStat addStandingStat(StandingStat standingStat) {
		getStandingStats().add(standingStat);
		standingStat.setStading(this);

		return standingStat;
	}

	public StandingStat removeStandingStat(StandingStat standingStat) {
		getStandingStats().remove(standingStat);
		standingStat.setStading(null);

		return standingStat;
	}

}