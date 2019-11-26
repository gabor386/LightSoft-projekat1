package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the playerfixstat database table.
 * 
 */
@Entity
@NamedQuery(name="Playerfixstat.findAll", query="SELECT p FROM Playerfixstat p")
public class Playerfixstat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPlayerFixStat;

	private String captain;

	private int cardsRed;

	private int cardsYellow;

	private int dribblesAttempts;

	private int dribblesPast;

	private int dribblesSuccess;

	private int duelsTotal;

	private int duelsWon;

	private int foulsComitted;

	private int foulsDrawn;

	private int goalsAssists;

	private int goalsConceded;

	private int goalsTotal;

	private String offsides;

	private int passesAccuracy;

	private int passesKey;

	private int passesTotal;

	private int penaltyComitted;

	private int penaltyMissed;

	private int penaltySaved;

	private int penaltySuccess;

	private int penaltyWon;

	private String rating;

	private int shotsOn;

	private int shotsTotal;

	private String substitute;

	private int tacklesBlocks;

	private int tacklesInterceptions;

	private int tacklesTotal;

	private int updateAt;

	//bi-directional many-to-one association to Fixture
	@ManyToOne
	@JoinColumn(name="Fixtures_idFixtures")
	private Fixture fixture;

	//bi-directional many-to-one association to Teamplayer
	@ManyToOne
	private Teamplayer teamplayer;

	public Playerfixstat() {
	}

	public int getIdPlayerFixStat() {
		return this.idPlayerFixStat;
	}

	public void setIdPlayerFixStat(int idPlayerFixStat) {
		this.idPlayerFixStat = idPlayerFixStat;
	}

	public String getCaptain() {
		return this.captain;
	}

	public void setCaptain(String captain) {
		this.captain = captain;
	}

	public int getCardsRed() {
		return this.cardsRed;
	}

	public void setCardsRed(int cardsRed) {
		this.cardsRed = cardsRed;
	}

	public int getCardsYellow() {
		return this.cardsYellow;
	}

	public void setCardsYellow(int cardsYellow) {
		this.cardsYellow = cardsYellow;
	}

	public int getDribblesAttempts() {
		return this.dribblesAttempts;
	}

	public void setDribblesAttempts(int dribblesAttempts) {
		this.dribblesAttempts = dribblesAttempts;
	}

	public int getDribblesPast() {
		return this.dribblesPast;
	}

	public void setDribblesPast(int dribblesPast) {
		this.dribblesPast = dribblesPast;
	}

	public int getDribblesSuccess() {
		return this.dribblesSuccess;
	}

	public void setDribblesSuccess(int dribblesSuccess) {
		this.dribblesSuccess = dribblesSuccess;
	}

	public int getDuelsTotal() {
		return this.duelsTotal;
	}

	public void setDuelsTotal(int duelsTotal) {
		this.duelsTotal = duelsTotal;
	}

	public int getDuelsWon() {
		return this.duelsWon;
	}

	public void setDuelsWon(int duelsWon) {
		this.duelsWon = duelsWon;
	}

	public int getFoulsComitted() {
		return this.foulsComitted;
	}

	public void setFoulsComitted(int foulsComitted) {
		this.foulsComitted = foulsComitted;
	}

	public int getFoulsDrawn() {
		return this.foulsDrawn;
	}

	public void setFoulsDrawn(int foulsDrawn) {
		this.foulsDrawn = foulsDrawn;
	}

	public int getGoalsAssists() {
		return this.goalsAssists;
	}

	public void setGoalsAssists(int goalsAssists) {
		this.goalsAssists = goalsAssists;
	}

	public int getGoalsConceded() {
		return this.goalsConceded;
	}

	public void setGoalsConceded(int goalsConceded) {
		this.goalsConceded = goalsConceded;
	}

	public int getGoalsTotal() {
		return this.goalsTotal;
	}

	public void setGoalsTotal(int goalsTotal) {
		this.goalsTotal = goalsTotal;
	}

	public String getOffsides() {
		return this.offsides;
	}

	public void setOffsides(String offsides) {
		this.offsides = offsides;
	}

	public int getPassesAccuracy() {
		return this.passesAccuracy;
	}

	public void setPassesAccuracy(int passesAccuracy) {
		this.passesAccuracy = passesAccuracy;
	}

	public int getPassesKey() {
		return this.passesKey;
	}

	public void setPassesKey(int passesKey) {
		this.passesKey = passesKey;
	}

	public int getPassesTotal() {
		return this.passesTotal;
	}

	public void setPassesTotal(int passesTotal) {
		this.passesTotal = passesTotal;
	}

	public int getPenaltyComitted() {
		return this.penaltyComitted;
	}

	public void setPenaltyComitted(int penaltyComitted) {
		this.penaltyComitted = penaltyComitted;
	}

	public int getPenaltyMissed() {
		return this.penaltyMissed;
	}

	public void setPenaltyMissed(int penaltyMissed) {
		this.penaltyMissed = penaltyMissed;
	}

	public int getPenaltySaved() {
		return this.penaltySaved;
	}

	public void setPenaltySaved(int penaltySaved) {
		this.penaltySaved = penaltySaved;
	}

	public int getPenaltySuccess() {
		return this.penaltySuccess;
	}

	public void setPenaltySuccess(int penaltySuccess) {
		this.penaltySuccess = penaltySuccess;
	}

	public int getPenaltyWon() {
		return this.penaltyWon;
	}

	public void setPenaltyWon(int penaltyWon) {
		this.penaltyWon = penaltyWon;
	}

	public String getRating() {
		return this.rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public int getShotsOn() {
		return this.shotsOn;
	}

	public void setShotsOn(int shotsOn) {
		this.shotsOn = shotsOn;
	}

	public int getShotsTotal() {
		return this.shotsTotal;
	}

	public void setShotsTotal(int shotsTotal) {
		this.shotsTotal = shotsTotal;
	}

	public String getSubstitute() {
		return this.substitute;
	}

	public void setSubstitute(String substitute) {
		this.substitute = substitute;
	}

	public int getTacklesBlocks() {
		return this.tacklesBlocks;
	}

	public void setTacklesBlocks(int tacklesBlocks) {
		this.tacklesBlocks = tacklesBlocks;
	}

	public int getTacklesInterceptions() {
		return this.tacklesInterceptions;
	}

	public void setTacklesInterceptions(int tacklesInterceptions) {
		this.tacklesInterceptions = tacklesInterceptions;
	}

	public int getTacklesTotal() {
		return this.tacklesTotal;
	}

	public void setTacklesTotal(int tacklesTotal) {
		this.tacklesTotal = tacklesTotal;
	}

	public int getUpdateAt() {
		return this.updateAt;
	}

	public void setUpdateAt(int updateAt) {
		this.updateAt = updateAt;
	}

	public Fixture getFixture() {
		return this.fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public Teamplayer getTeamplayer() {
		return this.teamplayer;
	}

	public void setTeamplayer(Teamplayer teamplayer) {
		this.teamplayer = teamplayer;
	}

}