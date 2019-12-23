package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the LastFiveStat database table.
 * 
 */
@Entity
@NamedQuery(name="LastFiveStat.findAll", query="SELECT l FROM LastFiveStat l")
public class LastFiveStat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idlastFiveStat;

	private String att;

	private String def;

	private String forme;

	private int goals;

	private int goalsAgainst;

	private double goalsAgainstAvg;

	private double goalsAvg;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="Team_idTeam")
	private Team team;

	public LastFiveStat() {
	}

	public int getIdlastFiveStat() {
		return this.idlastFiveStat;
	}

	public void setIdlastFiveStat(int idlastFiveStat) {
		this.idlastFiveStat = idlastFiveStat;
	}

	public String getAtt() {
		return this.att;
	}

	public void setAtt(String att) {
		this.att = att;
	}

	public String getDef() {
		return this.def;
	}

	public void setDef(String def) {
		this.def = def;
	}

	public String getForme() {
		return this.forme;
	}

	public void setForme(String forme) {
		this.forme = forme;
	}

	public int getGoals() {
		return this.goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getGoalsAgainst() {
		return this.goalsAgainst;
	}

	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public double getGoalsAgainstAvg() {
		return this.goalsAgainstAvg;
	}

	public void setGoalsAgainstAvg(double goalsAgainstAvg) {
		this.goalsAgainstAvg = goalsAgainstAvg;
	}

	public double getGoalsAvg() {
		return this.goalsAvg;
	}

	public void setGoalsAvg(double goalsAvg) {
		this.goalsAvg = goalsAvg;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}