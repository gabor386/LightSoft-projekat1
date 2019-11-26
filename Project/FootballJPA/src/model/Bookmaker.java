package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the bookmaker database table.
 * 
 */
@Entity
@NamedQuery(name="Bookmaker.findAll", query="SELECT b FROM Bookmaker b")
public class Bookmaker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idBookmaker;

	private String name;

	//bi-directional many-to-one association to Bet
	@OneToMany(mappedBy="bookmaker")
	private List<Bet> bets;

	public Bookmaker() {
	}

	public int getIdBookmaker() {
		return this.idBookmaker;
	}

	public void setIdBookmaker(int idBookmaker) {
		this.idBookmaker = idBookmaker;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Bet> getBets() {
		return this.bets;
	}

	public void setBets(List<Bet> bets) {
		this.bets = bets;
	}

	public Bet addBet(Bet bet) {
		getBets().add(bet);
		bet.setBookmaker(this);

		return bet;
	}

	public Bet removeBet(Bet bet) {
		getBets().remove(bet);
		bet.setBookmaker(null);

		return bet;
	}

}