package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SideLine database table.
 * 
 */
@Entity
@NamedQuery(name="SideLine.findAll", query="SELECT s FROM SideLine s")
public class SideLine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSideLine;

	private String end;

	private String start;

	private String type;

	//bi-directional many-to-one association to Coach
	@ManyToOne
	@JoinColumn(name="Coach_idCoach")
	private Coach coach;

	//bi-directional many-to-one association to TeamPlayer
	@ManyToOne
	@JoinColumn(name="TeamPlayer_idTeamPlayer")
	private TeamPlayer teamPlayer;

	public SideLine() {
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

	public Coach getCoach() {
		return this.coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	public TeamPlayer getTeamPlayer() {
		return this.teamPlayer;
	}

	public void setTeamPlayer(TeamPlayer teamPlayer) {
		this.teamPlayer = teamPlayer;
	}

}