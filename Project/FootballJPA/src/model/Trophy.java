package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the trophy database table.
 * 
 */
@Entity
@NamedQuery(name="Trophy.findAll", query="SELECT t FROM Trophy t")
public class Trophy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTrophy;

	private String place;

	private String trophycol;

	//bi-directional many-to-one association to League
	@ManyToOne
	private League league;

	//bi-directional many-to-one association to Coach
	@ManyToOne
	private Coach coach;

	//bi-directional many-to-one association to Winner
	@OneToMany(mappedBy="trophy")
	private List<Winner> winners;

	public Trophy() {
	}

	public int getIdTrophy() {
		return this.idTrophy;
	}

	public void setIdTrophy(int idTrophy) {
		this.idTrophy = idTrophy;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getTrophycol() {
		return this.trophycol;
	}

	public void setTrophycol(String trophycol) {
		this.trophycol = trophycol;
	}

	public League getLeague() {
		return this.league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public Coach getCoach() {
		return this.coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	public List<Winner> getWinners() {
		return this.winners;
	}

	public void setWinners(List<Winner> winners) {
		this.winners = winners;
	}

	public Winner addWinner(Winner winner) {
		getWinners().add(winner);
		winner.setTrophy(this);

		return winner;
	}

	public Winner removeWinner(Winner winner) {
		getWinners().remove(winner);
		winner.setTrophy(null);

		return winner;
	}

}