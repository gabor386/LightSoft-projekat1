package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TopScorers database table.
 * 
 */
@Entity
@Table(name="TopScorers")
@NamedQuery(name="TopScorer.findAll", query="SELECT t FROM TopScorer t")
public class TopScorer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTopScorers;

	//bi-directional many-to-one association to League
	@ManyToOne
	@JoinColumn(name="League_idLeague")
	private League league;

	//bi-directional many-to-one association to TeamPlayer
	@ManyToOne
	@JoinColumn(name="TeamPlayer_idTeamPlayer")
	private TeamPlayer teamPlayer;

	public TopScorer() {
	}

	public int getIdTopScorers() {
		return this.idTopScorers;
	}

	public void setIdTopScorers(int idTopScorers) {
		this.idTopScorers = idTopScorers;
	}

	public League getLeague() {
		return this.league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public TeamPlayer getTeamPlayer() {
		return this.teamPlayer;
	}

	public void setTeamPlayer(TeamPlayer teamPlayer) {
		this.teamPlayer = teamPlayer;
	}

}