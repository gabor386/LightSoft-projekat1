package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the teamin database table.
 * 
 */
@Entity
@NamedQuery(name="Teamin.findAll", query="SELECT t FROM Teamin t")
public class Teamin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTeamIn;

	//bi-directional many-to-one association to Team
	@ManyToOne
	private Team team;

	//bi-directional many-to-one association to Transfer
	@OneToMany(mappedBy="teamin")
	private List<Transfer> transfers;

	public Teamin() {
	}

	public int getIdTeamIn() {
		return this.idTeamIn;
	}

	public void setIdTeamIn(int idTeamIn) {
		this.idTeamIn = idTeamIn;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<Transfer> getTransfers() {
		return this.transfers;
	}

	public void setTransfers(List<Transfer> transfers) {
		this.transfers = transfers;
	}

	public Transfer addTransfer(Transfer transfer) {
		getTransfers().add(transfer);
		transfer.setTeamin(this);

		return transfer;
	}

	public Transfer removeTransfer(Transfer transfer) {
		getTransfers().remove(transfer);
		transfer.setTeamin(null);

		return transfer;
	}

}