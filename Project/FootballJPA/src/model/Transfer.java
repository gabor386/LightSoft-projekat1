package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the transfer database table.
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
	private Player player;

	//bi-directional many-to-one association to Teamin
	@ManyToOne
	private Teamin teamin;

	//bi-directional many-to-one association to Teamout
	@ManyToOne
	private Teamout teamout;

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

	public Teamin getTeamin() {
		return this.teamin;
	}

	public void setTeamin(Teamin teamin) {
		this.teamin = teamin;
	}

	public Teamout getTeamout() {
		return this.teamout;
	}

	public void setTeamout(Teamout teamout) {
		this.teamout = teamout;
	}

}