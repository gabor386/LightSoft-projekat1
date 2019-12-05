package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Team database table.
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

	//bi-directional many-to-one association to AwayTeam
	@OneToMany(mappedBy="team")
	private List<AwayTeam> awayTeams;

	//bi-directional many-to-one association to Career
	@OneToMany(mappedBy="team")
	private List<Career> careers;

	//bi-directional many-to-one association to Coach
	@OneToMany(mappedBy="team")
	private List<Coach> coaches;

	//bi-directional many-to-one association to HomeTeam
	@OneToMany(mappedBy="team")
	private List<HomeTeam> homeTeams;

	//bi-directional many-to-one association to LastFiveStat
	@OneToMany(mappedBy="team")
	private List<LastFiveStat> lastFiveStats;

	//bi-directional many-to-one association to LineUp
	@OneToMany(mappedBy="team")
	private List<LineUp> lineUps;

	//bi-directional many-to-one association to Stading
	@OneToMany(mappedBy="team")
	private List<Stading> stadings;

	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="Country_idCountry")
	private Country country;

	//bi-directional many-to-one association to TeamIn
	@OneToMany(mappedBy="team")
	private List<TeamIn> teamIns;

	//bi-directional many-to-one association to TeamOut
	@OneToMany(mappedBy="team")
	private List<TeamOut> teamOuts;

	//bi-directional many-to-one association to TeamPlayer
	@OneToMany(mappedBy="team")
	private List<TeamPlayer> teamPlayers;

	//bi-directional many-to-one association to TeamStatistic
	@OneToMany(mappedBy="team")
	private List<TeamStatistic> teamStatistics;

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

	public List<AwayTeam> getAwayTeams() {
		return this.awayTeams;
	}

	public void setAwayTeams(List<AwayTeam> awayTeams) {
		this.awayTeams = awayTeams;
	}

	public AwayTeam addAwayTeam(AwayTeam awayTeam) {
		getAwayTeams().add(awayTeam);
		awayTeam.setTeam(this);

		return awayTeam;
	}

	public AwayTeam removeAwayTeam(AwayTeam awayTeam) {
		getAwayTeams().remove(awayTeam);
		awayTeam.setTeam(null);

		return awayTeam;
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

	public List<HomeTeam> getHomeTeams() {
		return this.homeTeams;
	}

	public void setHomeTeams(List<HomeTeam> homeTeams) {
		this.homeTeams = homeTeams;
	}

	public HomeTeam addHomeTeam(HomeTeam homeTeam) {
		getHomeTeams().add(homeTeam);
		homeTeam.setTeam(this);

		return homeTeam;
	}

	public HomeTeam removeHomeTeam(HomeTeam homeTeam) {
		getHomeTeams().remove(homeTeam);
		homeTeam.setTeam(null);

		return homeTeam;
	}

	public List<LastFiveStat> getLastFiveStats() {
		return this.lastFiveStats;
	}

	public void setLastFiveStats(List<LastFiveStat> lastFiveStats) {
		this.lastFiveStats = lastFiveStats;
	}

	public LastFiveStat addLastFiveStat(LastFiveStat lastFiveStat) {
		getLastFiveStats().add(lastFiveStat);
		lastFiveStat.setTeam(this);

		return lastFiveStat;
	}

	public LastFiveStat removeLastFiveStat(LastFiveStat lastFiveStat) {
		getLastFiveStats().remove(lastFiveStat);
		lastFiveStat.setTeam(null);

		return lastFiveStat;
	}

	public List<LineUp> getLineUps() {
		return this.lineUps;
	}

	public void setLineUps(List<LineUp> lineUps) {
		this.lineUps = lineUps;
	}

	public LineUp addLineUp(LineUp lineUp) {
		getLineUps().add(lineUp);
		lineUp.setTeam(this);

		return lineUp;
	}

	public LineUp removeLineUp(LineUp lineUp) {
		getLineUps().remove(lineUp);
		lineUp.setTeam(null);

		return lineUp;
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

	public List<TeamIn> getTeamIns() {
		return this.teamIns;
	}

	public void setTeamIns(List<TeamIn> teamIns) {
		this.teamIns = teamIns;
	}

	public TeamIn addTeamIn(TeamIn teamIn) {
		getTeamIns().add(teamIn);
		teamIn.setTeam(this);

		return teamIn;
	}

	public TeamIn removeTeamIn(TeamIn teamIn) {
		getTeamIns().remove(teamIn);
		teamIn.setTeam(null);

		return teamIn;
	}

	public List<TeamOut> getTeamOuts() {
		return this.teamOuts;
	}

	public void setTeamOuts(List<TeamOut> teamOuts) {
		this.teamOuts = teamOuts;
	}

	public TeamOut addTeamOut(TeamOut teamOut) {
		getTeamOuts().add(teamOut);
		teamOut.setTeam(this);

		return teamOut;
	}

	public TeamOut removeTeamOut(TeamOut teamOut) {
		getTeamOuts().remove(teamOut);
		teamOut.setTeam(null);

		return teamOut;
	}

	public List<TeamPlayer> getTeamPlayers() {
		return this.teamPlayers;
	}

	public void setTeamPlayers(List<TeamPlayer> teamPlayers) {
		this.teamPlayers = teamPlayers;
	}

	public TeamPlayer addTeamPlayer(TeamPlayer teamPlayer) {
		getTeamPlayers().add(teamPlayer);
		teamPlayer.setTeam(this);

		return teamPlayer;
	}

	public TeamPlayer removeTeamPlayer(TeamPlayer teamPlayer) {
		getTeamPlayers().remove(teamPlayer);
		teamPlayer.setTeam(null);

		return teamPlayer;
	}

	public List<TeamStatistic> getTeamStatistics() {
		return this.teamStatistics;
	}

	public void setTeamStatistics(List<TeamStatistic> teamStatistics) {
		this.teamStatistics = teamStatistics;
	}

	public TeamStatistic addTeamStatistic(TeamStatistic teamStatistic) {
		getTeamStatistics().add(teamStatistic);
		teamStatistic.setTeam(this);

		return teamStatistic;
	}

	public TeamStatistic removeTeamStatistic(TeamStatistic teamStatistic) {
		getTeamStatistics().remove(teamStatistic);
		teamStatistic.setTeam(null);

		return teamStatistic;
	}

}