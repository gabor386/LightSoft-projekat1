package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the assist database table.
 * 
 */
@Entity
@Table(name="Assist")
@NamedQuery(name="Assist.findAll", query="SELECT a FROM Assist a")
public class Assist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idAssist;

	//bi-directional many-to-one association to Teamplayer
	@ManyToOne
	private Teamplayer teamplayer;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="assist")
	private List<Event> events;

	public Assist() {
	}

	public int getIdAssist() {
		return this.idAssist;
	}

	public void setIdAssist(int idAssist) {
		this.idAssist = idAssist;
	}

	public Teamplayer getTeamplayer() {
		return this.teamplayer;
	}

	public void setTeamplayer(Teamplayer teamplayer) {
		this.teamplayer = teamplayer;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setAssist(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setAssist(null);

		return event;
	}

}