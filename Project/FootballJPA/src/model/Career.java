package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Career database table.
 * 
 */
@Entity
@NamedQuery(name="Career.findAll", query="SELECT c FROM Career c")
public class Career implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCareer;

	private String end;

	private String start;

	//bi-directional many-to-one association to Coach
	@ManyToOne
	@JoinColumn(name="Coach_idCoach")
	private Coach coach;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="Team_idTeam")
	private Team team;

	public Career() {
	}

	public int getIdCareer() {
		return this.idCareer;
	}

	public void setIdCareer(int idCareer) {
		this.idCareer = idCareer;
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

	public Coach getCoach() {
		return this.coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}