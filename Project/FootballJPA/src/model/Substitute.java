package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the substitute database table.
 * 
 */
@Entity
@NamedQuery(name="Substitute.findAll", query="SELECT s FROM Substitute s")
public class Substitute implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSubstitute;

	//bi-directional many-to-one association to Teamplayer
	@ManyToOne
	private Teamplayer teamplayer;

	//bi-directional many-to-one association to Lineup
	@ManyToOne
	private Lineup lineup;

	public Substitute() {
	}

	public int getIdSubstitute() {
		return this.idSubstitute;
	}

	public void setIdSubstitute(int idSubstitute) {
		this.idSubstitute = idSubstitute;
	}

	public Teamplayer getTeamplayer() {
		return this.teamplayer;
	}

	public void setTeamplayer(Teamplayer teamplayer) {
		this.teamplayer = teamplayer;
	}

	public Lineup getLineup() {
		return this.lineup;
	}

	public void setLineup(Lineup lineup) {
		this.lineup = lineup;
	}

}