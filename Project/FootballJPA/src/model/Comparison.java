package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Comparison database table.
 * 
 */
@Entity
@NamedQuery(name="Comparison.findAll", query="SELECT c FROM Comparison c")
public class Comparison implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idComparison;

	private String away;

	private String comparisonName;

	private String home;

	//bi-directional many-to-one association to Prediction
	@ManyToOne
	@JoinColumn(name="Prediction_idPrediction")
	private Prediction prediction;

	public Comparison() {
	}

	public int getIdComparison() {
		return this.idComparison;
	}

	public void setIdComparison(int idComparison) {
		this.idComparison = idComparison;
	}

	public String getAway() {
		return this.away;
	}

	public void setAway(String away) {
		this.away = away;
	}

	public String getComparisonName() {
		return this.comparisonName;
	}

	public void setComparisonName(String comparisonName) {
		this.comparisonName = comparisonName;
	}

	public String getHome() {
		return this.home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public Prediction getPrediction() {
		return this.prediction;
	}

	public void setPrediction(Prediction prediction) {
		this.prediction = prediction;
	}

}