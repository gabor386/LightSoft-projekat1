package model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


/**
 * The persistent class for the league database table.
 * 
 */
@Entity
@Table(name="League")
@NamedQuery(name="League.findAll", query="SELECT l FROM League l")
public class League implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("league_id")
	private int idLeague;

	private String logo;

	private String name;

	private String seasonEnd;

	private String seasonStart;

	private String type;

	//bi-directional many-to-one association to Season
	@ManyToOne
	@JoinColumn(name="season")
	private Season seasonBean;

	//bi-directional many-to-one association to Country
	@ManyToOne
	private Country country;

	//bi-directional many-to-one association to Round
	@OneToMany(mappedBy="league")
	private List<Round> rounds;

	//bi-directional many-to-one association to Stading
	@OneToMany(mappedBy="league")
	private List<Stading> stadings;

	//bi-directional many-to-one association to Teamstatistic
	@OneToMany(mappedBy="league")
	private List<Teamstatistic> teamstatistics;

	//bi-directional many-to-one association to Topscorer
	@OneToMany(mappedBy="league")
	private List<Topscorer> topscorers;

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

	public Season getSeasonBean() {
		return this.seasonBean;
	}

	public void setSeasonBean(Season seasonBean) {
		this.seasonBean = seasonBean;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
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

	public List<Teamstatistic> getTeamstatistics() {
		return this.teamstatistics;
	}

	public void setTeamstatistics(List<Teamstatistic> teamstatistics) {
		this.teamstatistics = teamstatistics;
	}

	public Teamstatistic addTeamstatistic(Teamstatistic teamstatistic) {
		getTeamstatistics().add(teamstatistic);
		teamstatistic.setLeague(this);

		return teamstatistic;
	}

	public Teamstatistic removeTeamstatistic(Teamstatistic teamstatistic) {
		getTeamstatistics().remove(teamstatistic);
		teamstatistic.setLeague(null);

		return teamstatistic;
	}

	public List<Topscorer> getTopscorers() {
		return this.topscorers;
	}

	public void setTopscorers(List<Topscorer> topscorers) {
		this.topscorers = topscorers;
	}

	public Topscorer addTopscorer(Topscorer topscorer) {
		getTopscorers().add(topscorer);
		topscorer.setLeague(this);

		return topscorer;
	}

	public Topscorer removeTopscorer(Topscorer topscorer) {
		getTopscorers().remove(topscorer);
		topscorer.setLeague(null);

		return topscorer;
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