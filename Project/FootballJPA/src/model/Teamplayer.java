package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the teamplayer database table.
 * 
 */
@Entity
@NamedQuery(name="Teamplayer.findAll", query="SELECT t FROM Teamplayer t")
public class Teamplayer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTeamPlayer;

	//bi-directional many-to-one association to Assist
	@OneToMany(mappedBy="teamplayer")
	private List<Assist> assists;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="teamplayer")
	private List<Event> events;

	//bi-directional many-to-one association to Playerfixstat
	@OneToMany(mappedBy="teamplayer")
	private List<Playerfixstat> playerfixstats;

	//bi-directional many-to-one association to Playerstatistic
	@OneToMany(mappedBy="teamplayer")
	private List<Playerstatistic> playerstatistics;

	//bi-directional many-to-one association to Sideline
	@OneToMany(mappedBy="teamplayer")
	private List<Sideline> sidelines;

	//bi-directional many-to-one association to Startxi
	@OneToMany(mappedBy="teamplayer")
	private List<Startxi> startxis;

	//bi-directional many-to-one association to Substitute
	@OneToMany(mappedBy="teamplayer")
	private List<Substitute> substitutes;

	//bi-directional many-to-one association to Player
	@ManyToOne
	private Player player;

	//bi-directional many-to-one association to Team
	@ManyToOne
	private Team team;

	//bi-directional many-to-one association to Season
	@ManyToOne
	private Season season;

	//bi-directional many-to-one association to Topscorer
	@OneToMany(mappedBy="teamplayer")
	private List<Topscorer> topscorers;

	public Teamplayer() {
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
		assist.setTeamplayer(this);

		return assist;
	}

	public Assist removeAssist(Assist assist) {
		getAssists().remove(assist);
		assist.setTeamplayer(null);

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
		event.setTeamplayer(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setTeamplayer(null);

		return event;
	}

	public List<Playerfixstat> getPlayerfixstats() {
		return this.playerfixstats;
	}

	public void setPlayerfixstats(List<Playerfixstat> playerfixstats) {
		this.playerfixstats = playerfixstats;
	}

	public Playerfixstat addPlayerfixstat(Playerfixstat playerfixstat) {
		getPlayerfixstats().add(playerfixstat);
		playerfixstat.setTeamplayer(this);

		return playerfixstat;
	}

	public Playerfixstat removePlayerfixstat(Playerfixstat playerfixstat) {
		getPlayerfixstats().remove(playerfixstat);
		playerfixstat.setTeamplayer(null);

		return playerfixstat;
	}

	public List<Playerstatistic> getPlayerstatistics() {
		return this.playerstatistics;
	}

	public void setPlayerstatistics(List<Playerstatistic> playerstatistics) {
		this.playerstatistics = playerstatistics;
	}

	public Playerstatistic addPlayerstatistic(Playerstatistic playerstatistic) {
		getPlayerstatistics().add(playerstatistic);
		playerstatistic.setTeamplayer(this);

		return playerstatistic;
	}

	public Playerstatistic removePlayerstatistic(Playerstatistic playerstatistic) {
		getPlayerstatistics().remove(playerstatistic);
		playerstatistic.setTeamplayer(null);

		return playerstatistic;
	}

	public List<Sideline> getSidelines() {
		return this.sidelines;
	}

	public void setSidelines(List<Sideline> sidelines) {
		this.sidelines = sidelines;
	}

	public Sideline addSideline(Sideline sideline) {
		getSidelines().add(sideline);
		sideline.setTeamplayer(this);

		return sideline;
	}

	public Sideline removeSideline(Sideline sideline) {
		getSidelines().remove(sideline);
		sideline.setTeamplayer(null);

		return sideline;
	}

	public List<Startxi> getStartxis() {
		return this.startxis;
	}

	public void setStartxis(List<Startxi> startxis) {
		this.startxis = startxis;
	}

	public Startxi addStartxi(Startxi startxi) {
		getStartxis().add(startxi);
		startxi.setTeamplayer(this);

		return startxi;
	}

	public Startxi removeStartxi(Startxi startxi) {
		getStartxis().remove(startxi);
		startxi.setTeamplayer(null);

		return startxi;
	}

	public List<Substitute> getSubstitutes() {
		return this.substitutes;
	}

	public void setSubstitutes(List<Substitute> substitutes) {
		this.substitutes = substitutes;
	}

	public Substitute addSubstitute(Substitute substitute) {
		getSubstitutes().add(substitute);
		substitute.setTeamplayer(this);

		return substitute;
	}

	public Substitute removeSubstitute(Substitute substitute) {
		getSubstitutes().remove(substitute);
		substitute.setTeamplayer(null);

		return substitute;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Season getSeason() {
		return this.season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public List<Topscorer> getTopscorers() {
		return this.topscorers;
	}

	public void setTopscorers(List<Topscorer> topscorers) {
		this.topscorers = topscorers;
	}

	public Topscorer addTopscorer(Topscorer topscorer) {
		getTopscorers().add(topscorer);
		topscorer.setTeamplayer(this);

		return topscorer;
	}

	public Topscorer removeTopscorer(Topscorer topscorer) {
		getTopscorers().remove(topscorer);
		topscorer.setTeamplayer(null);

		return topscorer;
	}

}