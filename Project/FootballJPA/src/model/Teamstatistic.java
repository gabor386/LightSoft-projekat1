package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TeamStatistic database table.
 * 
 */
@Entity
@NamedQuery(name="TeamStatistic.findAll", query="SELECT t FROM TeamStatistic t")
public class TeamStatistic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTeamStatistic;

	private String away;

	private String home;

	private String statName;

	private String total;

	//bi-directional many-to-one association to League
	@ManyToOne
	@JoinColumn(name="League_idLeague")
	private League league;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="Team_idTeam")
	private Team team;

	public TeamStatistic() {
	}

	public int getIdTeamStatistic() {
		return this.idTeamStatistic;
	}

	public void setIdTeamStatistic(int idTeamStatistic) {
		this.idTeamStatistic = idTeamStatistic;
	}

	public String getAway() {
		return this.away;
	}

	public void setAway(String away) {
		this.away = away;
	}

	public String getHome() {
		return this.home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getStatName() {
		return this.statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public String getTotal() {
		return this.total;
	}

	public void setTotal(String total) {
		this.total = total;
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

}