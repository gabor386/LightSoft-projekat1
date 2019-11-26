package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the playerstatistic database table.
 * 
 */
@Entity
@NamedQuery(name="Playerstatistic.findAll", query="SELECT p FROM Playerstatistic p")
public class Playerstatistic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPlayerStatistic;

	private int captain;

	private int cardsRed;

	private int cardsYellow;

	private int cardsYellowred;

	private int dribblesAttempts;

	private int dribblesSuccess;

	private int duelsTotal;

	private int duelsWon;

	private int foulsComitted;

	private int foulsDrawn;

	private int gamesAppearences;

	private int gamesLinups;

	private int gamesMinutesPlayed;

	private int goalsAssists;

	private int goalsConceded;

	private int goalsTotal;

	private String injured;

	private String leauge;

	private int passesAccuracy;

	private int passesKey;

	private int passesTotal;

	private int penaltyCommited;

	private int penaltyMissed;

	private int penaltySaved;

	private int penaltySuccess;

	private int penaltyWon;

	private String rating;

	private int shotsOn;

	private int shotsTotal;

	private int substituesBench;

	private int substituesIn;

	private int substituesOut;

	private int tacklesBlocks;

	private int tacklesInterecptions;

	private int tacklesTotal;

	//bi-directional many-to-one association to Teamplayer
	@ManyToOne
	private Teamplayer teamplayer;

	public Playerstatistic() {
	}

	public int getIdPlayerStatistic() {
		return this.idPlayerStatistic;
	}

	public void setIdPlayerStatistic(int idPlayerStatistic) {
		this.idPlayerStatistic = idPlayerStatistic;
	}

	public int getCaptain() {
		return this.captain;
	}

	public void setCaptain(int captain) {
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

	public int getCardsYellowred() {
		return this.cardsYellowred;
	}

	public void setCardsYellowred(int cardsYellowred) {
		this.cardsYellowred = cardsYellowred;
	}

	public int getDribblesAttempts() {
		return this.dribblesAttempts;
	}

	public void setDribblesAttempts(int dribblesAttempts) {
		this.dribblesAttempts = dribblesAttempts;
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

	public int getGamesAppearences() {
		return this.gamesAppearences;
	}

	public void setGamesAppearences(int gamesAppearences) {
		this.gamesAppearences = gamesAppearences;
	}

	public int getGamesLinups() {
		return this.gamesLinups;
	}

	public void setGamesLinups(int gamesLinups) {
		this.gamesLinups = gamesLinups;
	}

	public int getGamesMinutesPlayed() {
		return this.gamesMinutesPlayed;
	}

	public void setGamesMinutesPlayed(int gamesMinutesPlayed) {
		this.gamesMinutesPlayed = gamesMinutesPlayed;
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

	public String getInjured() {
		return this.injured;
	}

	public void setInjured(String injured) {
		this.injured = injured;
	}

	public String getLeauge() {
		return this.leauge;
	}

	public void setLeauge(String leauge) {
		this.leauge = leauge;
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

	public int getPenaltyCommited() {
		return this.penaltyCommited;
	}

	public void setPenaltyCommited(int penaltyCommited) {
		this.penaltyCommited = penaltyCommited;
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

	public int getSubstituesBench() {
		return this.substituesBench;
	}

	public void setSubstituesBench(int substituesBench) {
		this.substituesBench = substituesBench;
	}

	public int getSubstituesIn() {
		return this.substituesIn;
	}

	public void setSubstituesIn(int substituesIn) {
		this.substituesIn = substituesIn;
	}

	public int getSubstituesOut() {
		return this.substituesOut;
	}

	public void setSubstituesOut(int substituesOut) {
		this.substituesOut = substituesOut;
	}

	public int getTacklesBlocks() {
		return this.tacklesBlocks;
	}

	public void setTacklesBlocks(int tacklesBlocks) {
		this.tacklesBlocks = tacklesBlocks;
	}

	public int getTacklesInterecptions() {
		return this.tacklesInterecptions;
	}

	public void setTacklesInterecptions(int tacklesInterecptions) {
		this.tacklesInterecptions = tacklesInterecptions;
	}

	public int getTacklesTotal() {
		return this.tacklesTotal;
	}

	public void setTacklesTotal(int tacklesTotal) {
		this.tacklesTotal = tacklesTotal;
	}

	public Teamplayer getTeamplayer() {
		return this.teamplayer;
	}

	public void setTeamplayer(Teamplayer teamplayer) {
		this.teamplayer = teamplayer;
	}

}