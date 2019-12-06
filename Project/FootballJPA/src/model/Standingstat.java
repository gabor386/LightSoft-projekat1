package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the StandingStat database table.
 * 
 */
@Entity
@NamedQuery(name="StandingStat.findAll", query="SELECT s FROM StandingStat s")
public class StandingStat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idStendingStat;

	private int draw;

	private int goalsAgainst;

	private int goalsFor;

	private int lose;

	private int matchesPlayed;

	private String standingStatName;

	private int win;

	//bi-directional many-to-one association to Stading
	@ManyToOne
	@JoinColumn(name="Stading_idStading")
	private Stading stading;

	public StandingStat() {
	}

	public int getIdStendingStat() {
		return this.idStendingStat;
	}

	public void setIdStendingStat(int idStendingStat) {
		this.idStendingStat = idStendingStat;
	}

	public int getDraw() {
		return this.draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getGoalsAgainst() {
		return this.goalsAgainst;
	}

	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public int getGoalsFor() {
		return this.goalsFor;
	}

	public void setGoalsFor(int goalsFor) {
		this.goalsFor = goalsFor;
	}

	public int getLose() {
		return this.lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public int getMatchesPlayed() {
		return this.matchesPlayed;
	}

	public void setMatchesPlayed(int matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}

	public String getStandingStatName() {
		return this.standingStatName;
	}

	public void setStandingStatName(String standingStatName) {
		this.standingStatName = standingStatName;
	}

	public int getWin() {
		return this.win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public Stading getStading() {
		return this.stading;
	}

	public void setStading(Stading stading) {
		this.stading = stading;
	}

}