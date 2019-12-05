package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the League database table.
 * 
 */
@Entity
@NamedQuery(name="League.findAll", query="SELECT l FROM League l")
public class League implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idLeague;

	private String logo;

	private String name;

	private String seasonEnd;

	private String seasonStart;

	private String type;

	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="Country_idCountry")
	private Country country;

	//bi-directional many-to-one association to Season
	@ManyToOne
	@JoinColumn(name="season")
	private Season seasonBean;

	//bi-directional many-to-one association to Round
	@OneToMany(mappedBy="league")
	private List<Round> rounds;

	//bi-directional many-to-one association to Stading
	@OneToMany(mappedBy="league")
	private List<Stading> stadings;

	//bi-directional many-to-one association to TeamStatistic
	@OneToMany(mappedBy="league")
	private List<TeamStatistic> teamStatistics;

	//bi-directional many-to-one association to TopScorer
	@OneToMany(mappedBy="league")
	private List<TopScorer> topScorers;

	//bi-directional many-to-one association to Trophy
	@OneToMany(mappedBy="league")
	private List<Trophy> trophies;

	public League() {
	}

	public int getIdLeague() {
		return this.idLeague;
	}

	public void setIdLeague(int idLeague) {
		this.idLeague = idLeague;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeasonEnd() {
		return this.seasonEnd;
	}

	public void setSeasonEnd(String seasonEnd) {
		this.seasonEnd = seasonEnd;
	}

	public String getSeasonStart() {
		return this.seasonStart;
	}

	public void setSeasonStart(String seasonStart) {
		this.seasonStart = seasonStart;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Season getSeasonBean() {
		return this.seasonBean;
	}

	public void setSeasonBean(Season seasonBean) {
		this.seasonBean = seasonBean;
	}

	public List<Round> getRounds() {
		return this.rounds;
	}

	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}

	public Round addRound(Round round) {
		getRounds().add(round);
		round.setLeague(this);

		return round;
	}

	public Round removeRound(Round round) {
		getRounds().remove(round);
		round.setLeague(null);

		return round;
	}

	public List<Stading> getStadings() {
		return this.stadings;
	}

	public void setStadings(List<Stading> stadings) {
		this.stadings = stadings;
	}

	public Stading addStading(Stading stading) {
		getStadings().add(stading);
		stading.setLeague(this);

		return stading;
	}

	public Stading removeStading(Stading stading) {
		getStadings().remove(stading);
		stading.setLeague(null);

		return stading;
	}

	public List<TeamStatistic> getTeamStatistics() {
		return this.teamStatistics;
	}

	public void setTeamStatistics(List<TeamStatistic> teamStatistics) {
		this.teamStatistics = teamStatistics;
	}

	public TeamStatistic addTeamStatistic(TeamStatistic teamStatistic) {
		getTeamStatistics().add(teamStatistic);
		teamStatistic.setLeague(this);

		return teamStatistic;
	}

	public TeamStatistic removeTeamStatistic(TeamStatistic teamStatistic) {
		getTeamStatistics().remove(teamStatistic);
		teamStatistic.setLeague(null);

		return teamStatistic;
	}

	public List<TopScorer> getTopScorers() {
		return this.topScorers;
	}

	public void setTopScorers(List<TopScorer> topScorers) {
		this.topScorers = topScorers;
	}

	public TopScorer addTopScorer(TopScorer topScorer) {
		getTopScorers().add(topScorer);
		topScorer.setLeague(this);

		return topScorer;
	}

	public TopScorer removeTopScorer(TopScorer topScorer) {
		getTopScorers().remove(topScorer);
		topScorer.setLeague(null);

		return topScorer;
	}

	public List<Trophy> getTrophies() {
		return this.trophies;
	}

	public void setTrophies(List<Trophy> trophies) {
		this.trophies = trophies;
	}

	public Trophy addTrophy(Trophy trophy) {
		getTrophies().add(trophy);
		trophy.setLeague(this);

		return trophy;
	}

	public Trophy removeTrophy(Trophy trophy) {
		getTrophies().remove(trophy);
		trophy.setLeague(null);

		return trophy;
	}

}