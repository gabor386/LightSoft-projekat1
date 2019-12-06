package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Transfer database table.
 * 
 */
@Entity
@NamedQuery(name="Transfer.findAll", query="SELECT t FROM Transfer t")
public class Transfer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTransfer;

	private String lastUpdate;

	private String transferDate;

	private String type;

	//bi-directional many-to-one association to Player
	@ManyToOne
	@JoinColumn(name="Player_idPlayer")
	private Player player;

	//bi-directional many-to-one association to TeamIn
	@ManyToOne
	@JoinColumn(name="TeamIn_idTeamIn")
	private TeamIn teamIn;

	//bi-directional many-to-one association to TeamOut
	@ManyToOne
	@JoinColumn(name="TeamOut_idTeamOut")
	private TeamOut teamOut;

	public Transfer() {
	}

	public int getIdTransfer() {
		return this.idTransfer;
	}

	public void setIdTransfer(int idTransfer) {
		this.idTransfer = idTransfer;
	}

	public String getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getTransferDate() {
		return this.transferDate;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public TeamIn getTeamIn() {
		return this.teamIn;
	}

	public void setTeamIn(TeamIn teamIn) {
		this.teamIn = teamIn;
	}

	public TeamOut getTeamOut() {
		return this.teamOut;
	}

	public void setTeamOut(TeamOut teamOut) {
		this.teamOut = teamOut;
	}

}