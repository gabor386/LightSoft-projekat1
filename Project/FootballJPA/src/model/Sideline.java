package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sideline database table.
 * 
 */
@Entity
@NamedQuery(name="Sideline.findAll", query="SELECT s FROM Sideline s")
public class Sideline implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSideLine;

	private String end;

	private String start;

	private String type;

	//bi-directional many-to-one association to Teamplayer
	@ManyToOne
	private Teamplayer teamplayer;

	//bi-directional many-to-one association to Coach
	@ManyToOne
	private Coach coach;

	public Sideline() {
	}

	public int getIdSideLine() {
		return this.idSideLine;
	}

	public void setIdSideLine(int idSideLine) {
		this.idSideLine = idSideLine;
	}

	public String getEnd() {
		return this.end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getStart() {
		return this.start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Teamplayer getTeamplayer() {
		return this.teamplayer;
	}

	public void setTeamplayer(Teamplayer teamplayer) {
		this.teamplayer = teamplayer;
	}

	public Coach getCoach() {
		return this.coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}

}