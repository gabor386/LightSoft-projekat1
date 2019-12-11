package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Player database table.
 * 
 */
@Entity
@NamedQuery(name="Player.findAll", query="SELECT p FROM Player p")
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idPlayer;

	private int age;

	private String brithCountry;

	private String brithDate;

	private String brithPlace;

	private String fristName;

	private String height;

	private String lastName;

	private String nationality;

	private int number;

	private String playerName;

	private String position;

	private String weight;

	//bi-directional many-to-one association to TeamPlayer
	@OneToMany(mappedBy="player")
	private List<TeamPlayer> teamPlayers;

	//bi-directional many-to-one association to Transfer
	@OneToMany(mappedBy="player")
	private List<Transfer> transfers;

	//bi-directional many-to-one association to Winner
	@OneToMany(mappedBy="player")
	private List<Winner> winners;

	public Player() {
	}

	public int getIdPlayer() {
		return this.idPlayer;
	}

	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBrithCountry() {
		return this.brithCountry;
	}

	public void setBrithCountry(String brithCountry) {
		this.brithCountry = brithCountry;
	}

	public String getBrithDate() {
		return this.brithDate;
	}

	public void setBrithDate(String brithDate) {
		this.brithDate = brithDate;
	}

	public String getBrithPlace() {
		return this.brithPlace;
	}

	public void setBrithPlace(String brithPlace) {
		this.brithPlace = brithPlace;
	}

	public String getFristName() {
		return this.fristName;
	}

	public void setFristName(String fristName) {
		this.fristName = fristName;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public List<TeamPlayer> getTeamPlayers() {
		return this.teamPlayers;
	}

	public void setTeamPlayers(List<TeamPlayer> teamPlayers) {
		this.teamPlayers = teamPlayers;
	}

	public TeamPlayer addTeamPlayer(TeamPlayer teamPlayer) {
		getTeamPlayers().add(teamPlayer);
		teamPlayer.setPlayer(this);

		return teamPlayer;
	}

	public TeamPlayer removeTeamPlayer(TeamPlayer teamPlayer) {
		getTeamPlayers().remove(teamPlayer);
		teamPlayer.setPlayer(null);

		return teamPlayer;
	}

	public List<Transfer> getTransfers() {
		return this.transfers;
	}

	public void setTransfers(List<Transfer> transfers) {
		this.transfers = transfers;
	}

	public Transfer addTransfer(Transfer transfer) {
		getTransfers().add(transfer);
		transfer.setPlayer(this);

		return transfer;
	}

	public Transfer removeTransfer(Transfer transfer) {
		getTransfers().remove(transfer);
		transfer.setPlayer(null);

		return transfer;
	}

	public List<Winner> getWinners() {
		return this.winners;
	}

	public void setWinners(List<Winner> winners) {
		this.winners = winners;
	}

	public Winner addWinner(Winner winner) {
		getWinners().add(winner);
		winner.setPlayer(this);

		return winner;
	}

	public Winner removeWinner(Winner winner) {
		getWinners().remove(winner);
		winner.setPlayer(null);

		return winner;
	}

}