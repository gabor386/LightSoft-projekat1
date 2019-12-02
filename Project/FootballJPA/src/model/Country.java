package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the country database table.
 * 
 */
@Entity
@NamedQuery(name="Country.findAll", query="SELECT c FROM Country c")
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCountry;

	private String code;

	private String flag;
	
	
	private String name;
	
	

	//bi-directional many-to-one association to Coach
	@OneToMany(mappedBy="country")
	private List<Coach> coaches;

	//bi-directional many-to-one association to League
	@OneToMany(mappedBy="country")
	private List<League> leagues;

	//bi-directional many-to-one association to Team
	@OneToMany(mappedBy="country")
	private List<Team> teams;

	public Country() {
	}

	public int getIdCountry() {
		return this.idCountry;
	}
	
	
	public void setIdCountry(int idCountry) {
		this.idCountry = idCountry;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Coach> getCoaches() {
		return this.coaches;
	}

	public void setCoaches(List<Coach> coaches) {
		this.coaches = coaches;
	}

	public Coach addCoach(Coach coach) {
		getCoaches().add(coach);
		coach.setCountry(this);

		return coach;
	}

	public Coach removeCoach(Coach coach) {
		getCoaches().remove(coach);
		coach.setCountry(null);

		return coach;
	}

	public List<League> getLeagues() {
		return this.leagues;
	}

	public void setLeagues(List<League> leagues) {
		this.leagues = leagues;
	}

	public League addLeague(League league) {
		getLeagues().add(league);
		league.setCountry(this);

		return league;
	}

	public League removeLeague(League league) {
		getLeagues().remove(league);
		league.setCountry(null);

		return league;
	}

	public List<Team> getTeams() {
		return this.teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public Team addTeam(Team team) {
		getTeams().add(team);
		team.setCountry(this);

		return team;
	}

	public Team removeTeam(Team team) {
		getTeams().remove(team);
		team.setCountry(null);

		return team;
	}
	
	
}