package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Fixtures database table.
 * 
 */
@Entity
@Table(name="Fixtures")
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

	//bi-directional many-to-one association to FixtureStat
	@OneToMany(mappedBy="fixture")
	private List<FixtureStat> fixtureStats;

	//bi-directional many-to-one association to AwayTeam
	@ManyToOne
	@JoinColumn(name="AwayTeam_idAwayTeam")
	private AwayTeam awayTeam;

	//bi-directional many-to-one association to HomeTeam
	@ManyToOne
	@JoinColumn(name="HomeTeam_idHomeTeam")
	private HomeTeam homeTeam;

	//bi-directional many-to-one association to Round
	@ManyToOne
	@JoinColumn(name="Round_idRound")
	private Round round;

	//bi-directional many-to-one association to Score
	@ManyToOne
	@JoinColumn(name="Score_idScore")
	private Score score;

	//bi-directional many-to-one association to LineUp
	@OneToMany(mappedBy="fixture")
	private List<LineUp> lineUps;

	//bi-directional many-to-one association to Odd
	@OneToMany(mappedBy="fixture")
	private List<Odd> odds;

	//bi-directional many-to-one association to PlayerFixStat
	@OneToMany(mappedBy="fixture")
	private List<PlayerFixStat> playerFixStats;

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

	public List<FixtureStat> getFixtureStats() {
		return this.fixtureStats;
	}

	public void setFixtureStats(List<FixtureStat> fixtureStats) {
		this.fixtureStats = fixtureStats;
	}

	public FixtureStat addFixtureStat(FixtureStat fixtureStat) {
		getFixtureStats().add(fixtureStat);
		fixtureStat.setFixture(this);

		return fixtureStat;
	}

	public FixtureStat removeFixtureStat(FixtureStat fixtureStat) {
		getFixtureStats().remove(fixtureStat);
		fixtureStat.setFixture(null);

		return fixtureStat;
	}

	public AwayTeam getAwayTeam() {
		return this.awayTeam;
	}

	public void setAwayTeam(AwayTeam awayTeam) {
		this.awayTeam = awayTeam;
	}

	public HomeTeam getHomeTeam() {
		return this.homeTeam;
	}

	public void setHomeTeam(HomeTeam homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Round getRound() {
		return this.round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public Score getScore() {
		return this.score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public List<LineUp> getLineUps() {
		return this.lineUps;
	}

	public void setLineUps(List<LineUp> lineUps) {
		this.lineUps = lineUps;
	}

	public LineUp addLineUp(LineUp lineUp) {
		getLineUps().add(lineUp);
		lineUp.setFixture(this);

		return lineUp;
	}

	public LineUp removeLineUp(LineUp lineUp) {
		getLineUps().remove(lineUp);
		lineUp.setFixture(null);

		return lineUp;
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

	public List<PlayerFixStat> getPlayerFixStats() {
		return this.playerFixStats;
	}

	public void setPlayerFixStats(List<PlayerFixStat> playerFixStats) {
		this.playerFixStats = playerFixStats;
	}

	public PlayerFixStat addPlayerFixStat(PlayerFixStat playerFixStat) {
		getPlayerFixStats().add(playerFixStat);
		playerFixStat.setFixture(this);

		return playerFixStat;
	}

	public PlayerFixStat removePlayerFixStat(PlayerFixStat playerFixStat) {
		getPlayerFixStats().remove(playerFixStat);
		playerFixStat.setFixture(null);

		return playerFixStat;
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