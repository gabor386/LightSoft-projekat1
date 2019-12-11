package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the startxi database table.
 * 
 */
@Entity
@Table(name="StartXI")
@NamedQuery(name="Startxi.findAll", query="SELECT s FROM Startxi s")
public class Startxi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idStartXI;

	//bi-directional many-to-one association to Teamplayer
	@ManyToOne
	private Teamplayer teamplayer;

	//bi-directional many-to-one association to Lineup
	@ManyToOne
	private Lineup lineup;

	public Startxi() {
	}

	public int getIdStartXI() {
		return this.idStartXI;
	}

	public void setIdStartXI(int idStartXI) {
		this.idStartXI = idStartXI;
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