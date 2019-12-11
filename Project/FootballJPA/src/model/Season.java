package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Season database table.
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

	//bi-directional many-to-one association to TeamPlayer
	@OneToMany(mappedBy="season")
	private List<TeamPlayer> teamPlayers;

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

	public List<TeamPlayer> getTeamPlayers() {
		return this.teamPlayers;
	}

	public void setTeamPlayers(List<TeamPlayer> teamPlayers) {
		this.teamPlayers = teamPlayers;
	}

	public TeamPlayer addTeamPlayer(TeamPlayer teamPlayer) {
		getTeamPlayers().add(teamPlayer);
		teamPlayer.setSeason(this);

		return teamPlayer;
	}

	public TeamPlayer removeTeamPlayer(TeamPlayer teamPlayer) {
		getTeamPlayers().remove(teamPlayer);
		teamPlayer.setSeason(null);

		return teamPlayer;
	}

}