package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Odds database table.
 * 
 */
@Entity
@Table(name="Odds")
@NamedQuery(name="Odd.findAll", query="SELECT o FROM Odd o")
public class Odd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idOdds;

	private int updateAt;

	//bi-directional many-to-one association to Bet
	@OneToMany(mappedBy="oddBean")
	private List<Bet> bets;

	//bi-directional many-to-one association to Fixture
	@ManyToOne
	@JoinColumn(name="Fixtures_idFixtures")
	private Fixture fixture;

	public Odd() {
	}

	public int getIdOdds() {
		return this.idOdds;
	}

	public void setIdOdds(int idOdds) {
		this.idOdds = idOdds;
	}

	public int getUpdateAt() {
		return this.updateAt;
	}

	public void setUpdateAt(int updateAt) {
		this.updateAt = updateAt;
	}

	public List<Bet> getBets() {
		return this.bets;
	}

	public void setBets(List<Bet> bets) {
		this.bets = bets;
	}

	public Bet addBet(Bet bet) {
		getBets().add(bet);
		bet.setOddBean(this);

		return bet;
	}

	public Bet removeBet(Bet bet) {
		getBets().remove(bet);
		bet.setOddBean(null);

		return bet;
	}

	public Fixture getFixture() {
		return this.fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

}