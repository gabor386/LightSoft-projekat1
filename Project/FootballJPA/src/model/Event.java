package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the event database table.
 * 
 */
@Entity
@Table(name="Event")
@NamedQuery(name="Event.findAll", query="SELECT e FROM Event e")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEvent;

	private String detail;

	private int elapsed;

	private String type;

	//bi-directional many-to-one association to Fixture
	@ManyToOne
	@JoinColumn(name="Fixtures_idFixtures")
	private Fixture fixture;

	//bi-directional many-to-one association to Teamplayer
	@ManyToOne
	private Teamplayer teamplayer;

	//bi-directional many-to-one association to Assist
	@ManyToOne
	private Assist assist;

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

	public Fixture getFixture() {
		return this.fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public Teamplayer getTeamplayer() {
		return this.teamplayer;
	}

	public void setTeamplayer(Teamplayer teamplayer) {
		this.teamplayer = teamplayer;
	}

	public Assist getAssist() {
		return this.assist;
	}

	public void setAssist(Assist assist) {
		this.assist = assist;
	}

}