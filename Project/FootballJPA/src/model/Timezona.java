package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TimeZona database table.
 * 
 */
@Entity
@NamedQuery(name="TimeZona.findAll", query="SELECT t FROM TimeZona t")
public class TimeZona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTimeZona;

	private String timeZone;

	public TimeZona() {
	}

	public int getIdTimeZona() {
		return this.idTimeZona;
	}

	public void setIdTimeZona(int idTimeZona) {
		this.idTimeZona = idTimeZona;
	}

	public String getTimeZone() {
		return this.timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

}