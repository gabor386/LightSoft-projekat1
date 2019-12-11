package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the StartXI database table.
 * 
 */
@Entity
@NamedQuery(name="StartXI.findAll", query="SELECT s FROM StartXI s")
public class StartXI implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idStartXI;

	//bi-directional many-to-one association to LineUp
	@ManyToOne
	@JoinColumn(name="LineUp_idLineUp")
	private LineUp lineUp;

	//bi-directional many-to-one association to TeamPlayer
	@ManyToOne
	@JoinColumn(name="TeamPlayer_idTeamPlayer")
	private TeamPlayer teamPlayer;

	public StartXI() {
	}

	public int getIdStartXI() {
		return this.idStartXI;
	}

	public void setIdStartXI(int idStartXI) {
		this.idStartXI = idStartXI;
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