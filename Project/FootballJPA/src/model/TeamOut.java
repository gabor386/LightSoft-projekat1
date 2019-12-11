package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TeamOut database table.
 * 
 */
@Entity
@NamedQuery(name="TeamOut.findAll", query="SELECT t FROM TeamOut t")
public class TeamOut implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTeamOut;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="Team_idTeam")
	private Team team;

	//bi-directional many-to-one association to Transfer
	@OneToMany(mappedBy="teamOut")
	private List<Transfer> transfers;

	public TeamOut() {
	}

	public int getIdTeamOut() {
		return this.idTeamOut;
	}

	public void setIdTeamOut(int idTeamOut) {
		this.idTeamOut = idTeamOut;
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
		transfer.setTeamOut(this);

		return transfer;
	}

	public Transfer removeTransfer(Transfer transfer) {
		getTransfers().remove(transfer);
		transfer.setTeamOut(null);

		return transfer;
	}

}