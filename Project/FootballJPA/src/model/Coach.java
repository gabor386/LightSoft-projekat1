package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the coach database table.
 * 
 */
@Entity
@NamedQuery(name="Coach.findAll", query="SELECT c FROM Coach c")
public class Coach implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idCoach;

	private int age;

	@Column(name="brith_country")
	private String brithCountry;

	@Column(name="brith_date")
	private String brithDate;

	private String brithPlace;

	private String fristName;

	private String height;

	private String lastName;

	private String name;

	private String nationality;

	private String weight;

	//bi-directional many-to-one association to Career
	@OneToMany(mappedBy="coach")
	private List<Career> careers;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="idTeam")
	private Team team;

	//bi-directional many-to-one association to Country
	@ManyToOne
	private Country country;

	//bi-directional many-to-one association to Sideline
	@OneToMany(mappedBy="coach")
	private List<Sideline> sidelines;

	//bi-directional many-to-one association to Trophy
	@OneToMany(mappedBy="coach")
	private List<Trophy> trophies;

	public Coach() {
	}

	public int getIdCoach() {
		return this.idCoach;
	}

	public void setIdCoach(int idCoach) {
		this.idCoach = idCoach;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBrithCountry() {
		return this.brithCountry;
	}

	public void setBrithCountry(String brithCountry) {
		this.brithCountry = brithCountry;
	}

	public String getBrithDate() {
		return this.brithDate;
	}

	public void setBrithDate(String brithDate) {
		this.brithDate = brithDate;
	}

	public String getBrithPlace() {
		return this.brithPlace;
	}

	public void setBrithPlace(String brithPlace) {
		this.brithPlace = brithPlace;
	}

	public String getFristName() {
		return this.fristName;
	}

	public void setFristName(String fristName) {
		this.fristName = fristName;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public List<Career> getCareers() {
		return this.careers;
	}

	public void setCareers(List<Career> careers) {
		this.careers = careers;
	}

	public Career addCareer(Career career) {
		getCareers().add(career);
		career.setCoach(this);

		return career;
	}

	public Career removeCareer(Career career) {
		getCareers().remove(career);
		career.setCoach(null);

		return career;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public List<Sideline> getSidelines() {
		return this.sidelines;
	}

	public void setSidelines(List<Sideline> sidelines) {
		this.sidelines = sidelines;
	}

	public Sideline addSideline(Sideline sideline) {
		getSidelines().add(sideline);
		sideline.setCoach(this);

		return sideline;
	}

	public Sideline removeSideline(Sideline sideline) {
		getSidelines().remove(sideline);
		sideline.setCoach(null);

		return sideline;
	}

	public List<Trophy> getTrophies() {
		return this.trophies;
	}

	public void setTrophies(List<Trophy> trophies) {
		this.trophies = trophies;
	}

	public Trophy addTrophy(Trophy trophy) {
		getTrophies().add(trophy);
		trophy.setCoach(this);

		return trophy;
	}

	public Trophy removeTrophy(Trophy trophy) {
		getTrophies().remove(trophy);
		trophy.setCoach(null);

		return trophy;
	}

}