package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the topscorers database table.
 * 
 */
@Entity
@Table(name="topscorers")
@NamedQuery(name="Topscorer.findAll", query="SELECT t FROM Topscorer t")
public class Topscorer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTopScorers;

	//bi-directional many-to-one association to League
	@ManyToOne
	private League league;

	//bi-directional many-to-one association to Teamplayer
	@ManyToOne
	private Teamplayer teamplayer;

	public Topscorer() {
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

	public Teamplayer getTeamplayer() {
		return this.teamplayer;
	}

	public void setTeamplayer(Teamplayer teamplayer) {
		this.teamplayer = teamplayer;
	}

}