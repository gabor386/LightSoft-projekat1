package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Bet database table.
 * 
 */
@Entity
@NamedQuery(name="Bet.findAll", query="SELECT b FROM Bet b")
public class Bet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idBet;

	private String betValues;

	private String odd;

	//bi-directional many-to-one association to Bookmaker
	@ManyToOne
	@JoinColumn(name="Bookmaker_idBookmaker")
	private Bookmaker bookmaker;

	//bi-directional many-to-one association to Label
	@ManyToOne
	@JoinColumn(name="Label_idLabel")
	private Label label;

	//bi-directional many-to-one association to Odd
	@ManyToOne
	@JoinColumn(name="Odds_idOdds")
	private Odd oddBean;

	public Bet() {
	}

	public int getIdBet() {
		return this.idBet;
	}

	public void setIdBet(int idBet) {
		this.idBet = idBet;
	}

	public String getBetValues() {
		return this.betValues;
	}

	public void setBetValues(String betValues) {
		this.betValues = betValues;
	}

	public String getOdd() {
		return this.odd;
	}

	public void setOdd(String odd) {
		this.odd = odd;
	}

	public Bookmaker getBookmaker() {
		return this.bookmaker;
	}

	public void setBookmaker(Bookmaker bookmaker) {
		this.bookmaker = bookmaker;
	}

	public Label getLabel() {
		return this.label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public Odd getOddBean() {
		return this.oddBean;
	}

	public void setOddBean(Odd oddBean) {
		this.oddBean = oddBean;
	}

}