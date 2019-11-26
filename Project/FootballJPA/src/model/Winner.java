package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the winner database table.
 * 
 */
@Entity
@NamedQuery(name="Winner.findAll", query="SELECT w FROM Winner w")
public class Winner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idWinner;

	//bi-directional many-to-one association to Player
	@ManyToOne
	private Player player;

	//bi-directional many-to-one association to Trophy
	@ManyToOne
	private Trophy trophy;

	public Winner() {
	}

	public int getIdWinner() {
		return this.idWinner;
	}

	public void setIdWinner(int idWinner) {
		this.idWinner = idWinner;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Trophy getTrophy() {
		return this.trophy;
	}

	public void setTrophy(Trophy trophy) {
		this.trophy = trophy;
	}

}