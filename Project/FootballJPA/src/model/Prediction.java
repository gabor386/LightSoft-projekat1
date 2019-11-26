package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the prediction database table.
 * 
 */
@Entity
@NamedQuery(name="Prediction.findAll", query="SELECT p FROM Prediction p")
public class Prediction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPrediction;

	private String advice;

	private String goalsAway;

	private String goalsHome;

	private String matchWinner;

	private String underOver;

	private String winningPercenteAway;

	private String winningPercenteDraws;

	private String winningPercenteHome;

	//bi-directional many-to-one association to Comparison
	@OneToMany(mappedBy="prediction")
	private List<Comparison> comparisons;

	//bi-directional many-to-one association to Fixture
	@ManyToOne
	@JoinColumn(name="Fixtures_idFixtures")
	private Fixture fixture;

	public Prediction() {
	}

	public int getIdPrediction() {
		return this.idPrediction;
	}

	public void setIdPrediction(int idPrediction) {
		this.idPrediction = idPrediction;
	}

	public String getAdvice() {
		return this.advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getGoalsAway() {
		return this.goalsAway;
	}

	public void setGoalsAway(String goalsAway) {
		this.goalsAway = goalsAway;
	}

	public String getGoalsHome() {
		return this.goalsHome;
	}

	public void setGoalsHome(String goalsHome) {
		this.goalsHome = goalsHome;
	}

	public String getMatchWinner() {
		return this.matchWinner;
	}

	public void setMatchWinner(String matchWinner) {
		this.matchWinner = matchWinner;
	}

	public String getUnderOver() {
		return this.underOver;
	}

	public void setUnderOver(String underOver) {
		this.underOver = underOver;
	}

	public String getWinningPercenteAway() {
		return this.winningPercenteAway;
	}

	public void setWinningPercenteAway(String winningPercenteAway) {
		this.winningPercenteAway = winningPercenteAway;
	}

	public String getWinningPercenteDraws() {
		return this.winningPercenteDraws;
	}

	public void setWinningPercenteDraws(String winningPercenteDraws) {
		this.winningPercenteDraws = winningPercenteDraws;
	}

	public String getWinningPercenteHome() {
		return this.winningPercenteHome;
	}

	public void setWinningPercenteHome(String winningPercenteHome) {
		this.winningPercenteHome = winningPercenteHome;
	}

	public List<Comparison> getComparisons() {
		return this.comparisons;
	}

	public void setComparisons(List<Comparison> comparisons) {
		this.comparisons = comparisons;
	}

	public Comparison addComparison(Comparison comparison) {
		getComparisons().add(comparison);
		comparison.setPrediction(this);

		return comparison;
	}

	public Comparison removeComparison(Comparison comparison) {
		getComparisons().remove(comparison);
		comparison.setPrediction(null);

		return comparison;
	}

	public Fixture getFixture() {
		return this.fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

}