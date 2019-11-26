package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the fixtures database table.
 * 
 */
@Entity
@Table(name="fixtures")
@NamedQuery(name="Fixture.findAll", query="SELECT f FROM Fixture f")
public class Fixture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idFixtures;

	private int elapsed;

	private String eventDate;

	private int eventTimeStamp;

	private int fristHalfStart;

	private int goalsAwayTeam;

	private int goalsHomeTeam;

	private String referee;

	private int secondHalfStart;

	private String status;

	private String statusShort;

	private String venue;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="fixture")
	private List<Event> events;

	//bi-directional many-to-one association to Score
	@ManyToOne
	private Score score;

	//bi-directional many-to-one association to Hometeam
	@ManyToOne
	private Hometeam hometeam;

	//bi-directional many-to-one association to Awayteam
	@ManyToOne
	private Awayteam awayteam;

	//bi-directional many-to-one association to Round
	@ManyToOne
	private Round round;

	//bi-directional many-to-one association to Fixturestat
	@OneToMany(mappedBy="fixture")
	private List<Fixturestat> fixturestats;

	//bi-directional many-to-one association to Lineup
	@OneToMany(mappedBy="fixture")
	private List<Lineup> lineups;

	//bi-directional many-to-one association to Odd
	@OneToMany(mappedBy="fixture")
	private List<Odd> odds;

	//bi-directional many-to-one association to Playerfixstat
	@OneToMany(mappedBy="fixture")
	private List<Playerfixstat> playerfixstats;

	//bi-directional many-to-one association to Prediction
	@OneToMany(mappedBy="fixture")
	private List<Prediction> predictions;

	public Fixture() {
	}

	public int getIdFixtures() {
		return this.idFixtures;
	}

	public void setIdFixtures(int idFixtures) {
		this.idFixtures = idFixtures;
	}

	public int getElapsed() {
		return this.elapsed;
	}

	public void setElapsed(int elapsed) {
		this.elapsed = elapsed;
	}

	public String getEventDate() {
		return this.eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public int getEventTimeStamp() {
		return this.eventTimeStamp;
	}

	public void setEventTimeStamp(int eventTimeStamp) {
		this.eventTimeStamp = eventTimeStamp;
	}

	public int getFristHalfStart() {
		return this.fristHalfStart;
	}

	public void setFristHalfStart(int fristHalfStart) {
		this.fristHalfStart = fristHalfStart;
	}

	public int getGoalsAwayTeam() {
		return this.goalsAwayTeam;
	}

	public void setGoalsAwayTeam(int goalsAwayTeam) {
		this.goalsAwayTeam = goalsAwayTeam;
	}

	public int getGoalsHomeTeam() {
		return this.goalsHomeTeam;
	}

	public void setGoalsHomeTeam(int goalsHomeTeam) {
		this.goalsHomeTeam = goalsHomeTeam;
	}

	public String getReferee() {
		return this.referee;
	}

	public void setReferee(String referee) {
		this.referee = referee;
	}

	public int getSecondHalfStart() {
		return this.secondHalfStart;
	}

	public void setSecondHalfStart(int secondHalfStart) {
		this.secondHalfStart = secondHalfStart;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusShort() {
		return this.statusShort;
	}

	public void setStatusShort(String statusShort) {
		this.statusShort = statusShort;
	}

	public String getVenue() {
		return this.venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setFixture(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setFixture(null);

		return event;
	}

	public Score getScore() {
		return this.score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public Hometeam getHometeam() {
		return this.hometeam;
	}

	public void setHometeam(Hometeam hometeam) {
		this.hometeam = hometeam;
	}

	public Awayteam getAwayteam() {
		return this.awayteam;
	}

	public void setAwayteam(Awayteam awayteam) {
		this.awayteam = awayteam;
	}

	public Round getRound() {
		return this.round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public List<Fixturestat> getFixturestats() {
		return this.fixturestats;
	}

	public void setFixturestats(List<Fixturestat> fixturestats) {
		this.fixturestats = fixturestats;
	}

	public Fixturestat addFixturestat(Fixturestat fixturestat) {
		getFixturestats().add(fixturestat);
		fixturestat.setFixture(this);

		return fixturestat;
	}

	public Fixturestat removeFixturestat(Fixturestat fixturestat) {
		getFixturestats().remove(fixturestat);
		fixturestat.setFixture(null);

		return fixturestat;
	}

	public List<Lineup> getLineups() {
		return this.lineups;
	}

	public void setLineups(List<Lineup> lineups) {
		this.lineups = lineups;
	}

	public Lineup addLineup(Lineup lineup) {
		getLineups().add(lineup);
		lineup.setFixture(this);

		return lineup;
	}

	public Lineup removeLineup(Lineup lineup) {
		getLineups().remove(lineup);
		lineup.setFixture(null);

		return lineup;
	}

	public List<Odd> getOdds() {
		return this.odds;
	}

	public void setOdds(List<Odd> odds) {
		this.odds = odds;
	}

	public Odd addOdd(Odd odd) {
		getOdds().add(odd);
		odd.setFixture(this);

		return odd;
	}

	public Odd removeOdd(Odd odd) {
		getOdds().remove(odd);
		odd.setFixture(null);

		return odd;
	}

	public List<Playerfixstat> getPlayerfixstats() {
		return this.playerfixstats;
	}

	public void setPlayerfixstats(List<Playerfixstat> playerfixstats) {
		this.playerfixstats = playerfixstats;
	}

	public Playerfixstat addPlayerfixstat(Playerfixstat playerfixstat) {
		getPlayerfixstats().add(playerfixstat);
		playerfixstat.setFixture(this);

		return playerfixstat;
	}

	public Playerfixstat removePlayerfixstat(Playerfixstat playerfixstat) {
		getPlayerfixstats().remove(playerfixstat);
		playerfixstat.setFixture(null);

		return playerfixstat;
	}

	public List<Prediction> getPredictions() {
		return this.predictions;
	}

	public void setPredictions(List<Prediction> predictions) {
		this.predictions = predictions;
	}

	public Prediction addPrediction(Prediction prediction) {
		getPredictions().add(prediction);
		prediction.setFixture(this);

		return prediction;
	}

	public Prediction removePrediction(Prediction prediction) {
		getPredictions().remove(prediction);
		prediction.setFixture(null);

		return prediction;
	}

}