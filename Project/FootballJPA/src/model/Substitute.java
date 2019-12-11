package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Substitute database table.
 * 
 */
@Entity
@NamedQuery(name="Substitute.findAll", query="SELECT s FROM Substitute s")
public class Substitute implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSubstitute;

	//bi-directional many-to-one association to LineUp
	@ManyToOne
	@JoinColumn(name="LineUp_idLineUp")
	private LineUp lineUp;

	//bi-directional many-to-one association to TeamPlayer
	@ManyToOne
	@JoinColumn(name="TeamPlayer_idTeamPlayer")
	private TeamPlayer teamPlayer;

	public Substitute() {
	}

	public int getIdSubstitute() {
		return this.idSubstitute;
	}

	public void setIdSubstitute(int idSubstitute) {
		this.idSubstitute = idSubstitute;
	}

	public LineUp getLineUp() {
		return this.lineUp;
	}

	public void setLineUp(LineUp lineUp) {
		this.lineUp = lineUp;
	}

	public TeamPlayer getTeamPlayer() {
		return this.teamPlayer;
	}

	public void setTeamPlayer(TeamPlayer teamPlayer) {
		this.teamPlayer = teamPlayer;
	}

}