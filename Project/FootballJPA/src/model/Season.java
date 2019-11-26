package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the season database table.
 * 
 */
@Entity
@NamedQuery(name="Season.findAll", query="SELECT s FROM Season s")
public class Season implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int season;

	private String seasonText;

	//bi-directional many-to-one association to League
	@OneToMany(mappedBy="seasonBean")
	private List<League> leagues;

	//bi-directional many-to-one association to Teamplayer
	@OneToMany(mappedBy="season")
	private List<Teamplayer> teamplayers;

	public Season() {
	}

	public int getSeason() {
		return this.season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public String getSeasonText() {
		return this.seasonText;
	}

	public void setSeasonText(String seasonText) {
		this.seasonText = seasonText;
	}

	public List<League> getLeagues() {
		return this.leagues;
	}

	public void setLeagues(List<League> leagues) {
		this.leagues = leagues;
	}

	public League addLeague(League league) {
		getLeagues().add(league);
		league.setSeasonBean(this);

		return league;
	}

	public League removeLeague(League league) {
		getLeagues().remove(league);
		league.setSeasonBean(null);

		return league;
	}

	public List<Teamplayer> getTeamplayers() {
		return this.teamplayers;
	}

	public void setTeamplayers(List<Teamplayer> teamplayers) {
		this.teamplayers = teamplayers;
	}

	public Teamplayer addTeamplayer(Teamplayer teamplayer) {
		getTeamplayers().add(teamplayer);
		teamplayer.setSeason(this);

		return teamplayer;
	}

	public Teamplayer removeTeamplayer(Teamplayer teamplayer) {
		getTeamplayers().remove(teamplayer);
		teamplayer.setSeason(null);

		return teamplayer;
	}

}