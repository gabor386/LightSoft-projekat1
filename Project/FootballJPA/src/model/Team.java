package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the team database table.
 * 
 */
@Entity
@NamedQuery(name="Team.findAll", query="SELECT t FROM Team t")
public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idTeam;

	private int capacity;

	private int founded;

	private String logo;

	private String stadionAddress;

	private String stadionCity;

	private String stadionName;

	private String stadionSurface;

	private String teamName;

	//bi-directional many-to-one association to Awayteam
	@OneToMany(mappedBy="team")
	private List<Awayteam> awayteams;

	//bi-directional many-to-one association to Career
	@OneToMany(mappedBy="team")
	private List<Career> careers;

	//bi-directional many-to-one association to Coach
	@OneToMany(mappedBy="team")
	private List<Coach> coaches;

	//bi-directional many-to-one association to Hometeam
	@OneToMany(mappedBy="team")
	private List<Hometeam> hometeams;

	//bi-directional many-to-one association to Lastfivestat
	@OneToMany(mappedBy="team")
	private List<Lastfivestat> lastfivestats;

	//bi-directional many-to-one association to Lineup
	@OneToMany(mappedBy="team")
	private List<Lineup> lineups;

	//bi-directional many-to-one association to Stading
	@OneToMany(mappedBy="team")
	private List<Stading> stadings;

	//bi-directional many-to-one association to Country
	@ManyToOne
	private Country country;

	//bi-directional many-to-one association to Teamin
	@OneToMany(mappedBy="team")
	private List<Teamin> teamins;

	//bi-directional many-to-one association to Teamout
	@OneToMany(mappedBy="team")
	private List<Teamout> teamouts;

	//bi-directional many-to-one association to Teamplayer
	@OneToMany(mappedBy="team")
	private List<Teamplayer> teamplayers;

	//bi-directional many-to-one association to Teamstatistic
	@OneToMany(mappedBy="team")
	private List<Teamstatistic> teamstatistics;

	public Team() {
	}

	public int getIdTeam() {
		return this.idTeam;
	}

	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getFounded() {
		return this.founded;
	}

	public void setFounded(int founded) {
		this.founded = founded;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getStadionAddress() {
		return this.stadionAddress;
	}

	public void setStadionAddress(String stadionAddress) {
		this.stadionAddress = stadionAddress;
	}

	public String getStadionCity() {
		return this.stadionCity;
	}

	public void setStadionCity(String stadionCity) {
		this.stadionCity = stadionCity;
	}

	public String getStadionName() {
		return this.stadionName;
	}

	public void setStadionName(String stadionName) {
		this.stadionName = stadionName;
	}

	public String getStadionSurface() {
		return this.stadionSurface;
	}

	public void setStadionSurface(String stadionSurface) {
		this.stadionSurface = stadionSurface;
	}

	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public List<Awayteam> getAwayteams() {
		return this.awayteams;
	}

	public void setAwayteams(List<Awayteam> awayteams) {
		this.awayteams = awayteams;
	}

	public Awayteam addAwayteam(Awayteam awayteam) {
		getAwayteams().add(awayteam);
		awayteam.setTeam(this);

		return awayteam;
	}

	public Awayteam removeAwayteam(Awayteam awayteam) {
		getAwayteams().remove(awayteam);
		awayteam.setTeam(null);

		return awayteam;
	}

	public List<Career> getCareers() {
		return this.careers;
	}

	public void setCareers(List<Career> careers) {
		this.careers = careers;
	}

	public Career addCareer(Career career) {
		getCareers().add(career);
		career.setTeam(this);

		return career;
	}

	public Career removeCareer(Career career) {
		getCareers().remove(career);
		career.setTeam(null);

		return career;
	}

	public List<Coach> getCoaches() {
		return this.coaches;
	}

	public void setCoaches(List<Coach> coaches) {
		this.coaches = coaches;
	}

	public Coach addCoach(Coach coach) {
		getCoaches().add(coach);
		coach.setTeam(this);

		return coach;
	}

	public Coach removeCoach(Coach coach) {
		getCoaches().remove(coach);
		coach.setTeam(null);

		return coach;
	}

	public List<Hometeam> getHometeams() {
		return this.hometeams;
	}

	public void setHometeams(List<Hometeam> hometeams) {
		this.hometeams = hometeams;
	}

	public Hometeam addHometeam(Hometeam hometeam) {
		getHometeams().add(hometeam);
		hometeam.setTeam(this);

		return hometeam;
	}

	public Hometeam removeHometeam(Hometeam hometeam) {
		getHometeams().remove(hometeam);
		hometeam.setTeam(null);

		return hometeam;
	}

	public List<Lastfivestat> getLastfivestats() {
		return this.lastfivestats;
	}

	public void setLastfivestats(List<Lastfivestat> lastfivestats) {
		this.lastfivestats = lastfivestats;
	}

	public Lastfivestat addLastfivestat(Lastfivestat lastfivestat) {
		getLastfivestats().add(lastfivestat);
		lastfivestat.setTeam(this);

		return lastfivestat;
	}

	public Lastfivestat removeLastfivestat(Lastfivestat lastfivestat) {
		getLastfivestats().remove(lastfivestat);
		lastfivestat.setTeam(null);

		return lastfivestat;
	}

	public List<Lineup> getLineups() {
		return this.lineups;
	}

	public void setLineups(List<Lineup> lineups) {
		this.lineups = lineups;
	}

	public Lineup addLineup(Lineup lineup) {
		getLineups().add(lineup);
		lineup.setTeam(this);

		return lineup;
	}

	public Lineup removeLineup(Lineup lineup) {
		getLineups().remove(lineup);
		lineup.setTeam(null);

		return lineup;
	}

	public List<Stading> getStadings() {
		return this.stadings;
	}

	public void setStadings(List<Stading> stadings) {
		this.stadings = stadings;
	}

	public Stading addStading(Stading stading) {
		getStadings().add(stading);
		stading.setTeam(this);

		return stading;
	}

	public Stading removeStading(Stading stading) {
		getStadings().remove(stading);
		stading.setTeam(null);

		return stading;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public List<Teamin> getTeamins() {
		return this.teamins;
	}

	public void setTeamins(List<Teamin> teamins) {
		this.teamins = teamins;
	}

	public Teamin addTeamin(Teamin teamin) {
		getTeamins().add(teamin);
		teamin.setTeam(this);

		return teamin;
	}

	public Teamin removeTeamin(Teamin teamin) {
		getTeamins().remove(teamin);
		teamin.setTeam(null);

		return teamin;
	}

	public List<Teamout> getTeamouts() {
		return this.teamouts;
	}

	public void setTeamouts(List<Teamout> teamouts) {
		this.teamouts = teamouts;
	}

	public Teamout addTeamout(Teamout teamout) {
		getTeamouts().add(teamout);
		teamout.setTeam(this);

		return teamout;
	}

	public Teamout removeTeamout(Teamout teamout) {
		getTeamouts().remove(teamout);
		teamout.setTeam(null);

		return teamout;
	}

	public List<Teamplayer> getTeamplayers() {
		return this.teamplayers;
	}

	public void setTeamplayers(List<Teamplayer> teamplayers) {
		this.teamplayers = teamplayers;
	}

	public Teamplayer addTeamplayer(Teamplayer teamplayer) {
		getTeamplayers().add(teamplayer);
		teamplayer.setTeam(this);

		return teamplayer;
	}

	public Teamplayer removeTeamplayer(Teamplayer teamplayer) {
		getTeamplayers().remove(teamplayer);
		teamplayer.setTeam(null);

		return teamplayer;
	}

	public List<Teamstatistic> getTeamstatistics() {
		return this.teamstatistics;
	}

	public void setTeamstatistics(List<Teamstatistic> teamstatistics) {
		this.teamstatistics = teamstatistics;
	}

	public Teamstatistic addTeamstatistic(Teamstatistic teamstatistic) {
		getTeamstatistics().add(teamstatistic);
		teamstatistic.setTeam(this);

		return teamstatistic;
	}

	public Teamstatistic removeTeamstatistic(Teamstatistic teamstatistic) {
		getTeamstatistics().remove(teamstatistic);
		teamstatistic.setTeam(null);

		return teamstatistic;
	}

}