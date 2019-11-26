package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the bet database table.
 * 
 */
@Entity
@NamedQuery(name="Bet.findAll", query="SELECT b FROM Bet b")
public class Bet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idBet;

	private String odd;

	private String values;

	//bi-directional many-to-one association to Bookmaker
	@ManyToOne
	private Bookmaker bookmaker;

	//bi-directional many-to-one association to Label
	@ManyToOne
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

	public String getOdd() {
		return this.odd;
	}

	public void setOdd(String odd) {
		this.odd = odd;
	}

	public String getValues() {
		return this.values;
	}

	public void setValues(String values) {
		this.values = values;
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