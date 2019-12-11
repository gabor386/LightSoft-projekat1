package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the timezona database table.
 * 
 */
@Entity
@Table(name="TimeZona")
@NamedQuery(name="Timezona.findAll", query="SELECT t FROM Timezona t")
public class Timezona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTimeZona;

	private String timeZone;

	public Timezona() {
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