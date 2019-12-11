package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TeamPlayer database table.
 * 
 */
@Entity
@NamedQuery(name="TeamPlayer.findAll", query="SELECT t FROM TeamPlayer t")
public class TeamPlayer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTeamPlayer;

	//bi-directional many-to-one association to Assist
	@OneToMany(mappedBy="teamPlayer")
	private List<Assist> assists;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="teamPlayer")
	private List<Event> events;

	//bi-directional many-to-one association to PlayerFixStat
	@OneToMany(mappedBy="teamPlayer")
	private List<PlayerFixStat> playerFixStats;

	//bi-directional many-to-one association to PlayerStatistic
	@OneToMany(mappedBy="teamPlayer")
	private List<PlayerStatistic> playerStatistics;

	//bi-directional many-to-one association to SideLine
	@OneToMany(mappedBy="teamPlayer")
	private List<SideLine> sideLines;

	//bi-directional many-to-one association to StartXI
	@OneToMany(mappedBy="teamPlayer")
	private List<StartXI> startXis;

	//bi-directional many-to-one association to Substitute
	@OneToMany(mappedBy="teamPlayer")
	private List<Substitute> substitutes;

	//bi-directional many-to-one association to Player
	@ManyToOne
	@JoinColumn(name="Player_idPlayer")
	private Player player;

	//bi-directional many-to-one association to Season
	@ManyToOne
	@JoinColumn(name="Season_season")
	private Season season;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="Team_idTeam")
	private Team team;

	//bi-directional many-to-one association to TopScorer
	@OneToMany(mappedBy="teamPlayer")
	private List<TopScorer> topScorers;

	public TeamPlayer() {
	}

	public int getIdTeamPlayer() {
		return this.idTeamPlayer;
	}

	public void setIdTeamPlayer(int idTeamPlayer) {
		this.idTeamPlayer = idTeamPlayer;
	}

	public List<Assist> getAssists() {
		return this.assists;
	}

	public void setAssists(List<Assist> assists) {
		this.assists = assists;
	}

	public Assist addAssist(Assist assist) {
		getAssists().add(assist);
		assist.setTeamPlayer(this);

		return assist;
	}

	public Assist removeAssist(Assist assist) {
		getAssists().remove(assist);
		assist.setTeamPlayer(null);

		return assist;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setTeamPlayer(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setTeamPlayer(null);

		return event;
	}

	public List<PlayerFixStat> getPlayerFixStats() {
		return this.playerFixStats;
	}

	public void setPlayerFixStats(List<PlayerFixStat> playerFixStats) {
		this.playerFixStats = playerFixStats;
	}

	public PlayerFixStat addPlayerFixStat(PlayerFixStat playerFixStat) {
		getPlayerFixStats().add(playerFixStat);
		playerFixStat.setTeamPlayer(this);

		return playerFixStat;
	}

	public PlayerFixStat removePlayerFixStat(PlayerFixStat playerFixStat) {
		getPlayerFixStats().remove(playerFixStat);
		playerFixStat.setTeamPlayer(null);

		return playerFixStat;
	}

	public List<PlayerStatistic> getPlayerStatistics() {
		return this.playerStatistics;
	}

	public void setPlayerStatistics(List<PlayerStatistic> playerStatistics) {
		this.playerStatistics = playerStatistics;
	}

	public PlayerStatistic addPlayerStatistic(PlayerStatistic playerStatistic) {
		getPlayerStatistics().add(playerStatistic);
		playerStatistic.setTeamPlayer(this);

		return playerStatistic;
	}

	public PlayerStatistic removePlayerStatistic(PlayerStatistic playerStatistic) {
		getPlayerStatistics().remove(playerStatistic);
		playerStatistic.setTeamPlayer(null);

		return playerStatistic;
	}

	public List<SideLine> getSideLines() {
		return this.sideLines;
	}

	public void setSideLines(List<SideLine> sideLines) {
		this.sideLines = sideLines;
	}

	public SideLine addSideLine(SideLine sideLine) {
		getSideLines().add(sideLine);
		sideLine.setTeamPlayer(this);

		return sideLine;
	}

	public SideLine removeSideLine(SideLine sideLine) {
		getSideLines().remove(sideLine);
		sideLine.setTeamPlayer(null);

		return sideLine;
	}

	public List<StartXI> getStartXis() {
		return this.startXis;
	}

	public void setStartXis(List<StartXI> startXis) {
		this.startXis = startXis;
	}

	public StartXI addStartXi(StartXI startXi) {
		getStartXis().add(startXi);
		startXi.setTeamPlayer(this);

		return startXi;
	}

	public StartXI removeStartXi(StartXI startXi) {
		getStartXis().remove(startXi);
		startXi.setTeamPlayer(null);

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
		substitute.setTeamPlayer(this);

		return substitute;
	}

	public Substitute removeSubstitute(Substitute substitute) {
		getSubstitutes().remove(substitute);
		substitute.setTeamPlayer(null);

		return substitute;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Season getSeason() {
		return this.season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<TopScorer> getTopScorers() {
		return this.topScorers;
	}

	public void setTopScorers(List<TopScorer> topScorers) {
		this.topScorers = topScorers;
	}

	public TopScorer addTopScorer(TopScorer topScorer) {
		getTopScorers().add(topScorer);
		topScorer.setTeamPlayer(this);

		return topScorer;
	}

	public TopScorer removeTopScorer(TopScorer topScorer) {
		getTopScorers().remove(topScorer);
		topScorer.setTeamPlayer(null);

		return topScorer;
	}

}