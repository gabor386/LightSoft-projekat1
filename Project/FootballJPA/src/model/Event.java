package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Event database table.
 * 
 */
@Entity
@NamedQuery(name="Event.findAll", query="SELECT e FROM Event e")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEvent;

	private String detail;

	private int elapsed;

	private String type;

	//bi-directional many-to-one association to Assist
	@ManyToOne
	@JoinColumn(name="Assist_idAssist")
	private Assist assist;

	//bi-directional many-to-one association to Fixture
	@ManyToOne
	@JoinColumn(name="Fixtures_idFixtures")
	private Fixture fixture;

	//bi-directional many-to-one association to TeamPlayer
	@ManyToOne
	@JoinColumn(name="TeamPlayer_idTeamPlayer")
	private TeamPlayer teamPlayer;

	public Event() {
	}

	public int getIdEvent() {
		return this.idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getElapsed() {
		return this.elapsed;
	}

	public void setElapsed(int elapsed) {
		this.elapsed = elapsed;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Assist getAssist() {
		return this.assist;
	}

	public void setAssist(Assist assist) {
		this.assist = assist;
	}

	public Fixture getFixture() {
		return this.fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public TeamPlayer getTeamPlayer() {
		return this.teamPlayer;
	}

	public void setTeamPlayer(TeamPlayer teamPlayer) {
		this.teamPlayer = teamPlayer;
	}

}