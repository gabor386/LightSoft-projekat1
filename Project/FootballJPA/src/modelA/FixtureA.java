package modelA;

import java.io.Serializable;
import java.util.Date;

public class FixtureA implements Serializable{

	
	private int idFixture;
	
	private Date date;
	
	private int idRound;
	private String round; 
	
	private int idHomeTeam;
	private String homeTeam;
	private String homeLogo;
	
	private int idAwayTeam;
	private String awayTeam;
	private String awayLogo;
	
	private String score;
	
	private String status;
	private String statusShort;
	
	
	
	public int getIdFixture() {
		return idFixture;
	}
	public void setIdFixture(int idFixture) {
		this.idFixture = idFixture;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getIdRound() {
		return idRound;   
	}
	public void setIdRound(int idRound) {
		this.idRound = idRound;
	}
	public String getRound() {
		return round;
	}
	public void setRound(String round) {
		this.round = round;
	}
	public int getIdHomeTeam() {
		return idHomeTeam;
	}
	public void setIdHomeTeam(int idHomeTeam) {
		this.idHomeTeam = idHomeTeam;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getHomeLogo() {
		return homeLogo;
	}
	public void setHomeLogo(String homeLogo) {
		this.homeLogo = homeLogo;
	}
	public int getIdAwayTeam() {
		return idAwayTeam;
	}
	public void setIdAwayTeam(int idAwayTeam) {
		this.idAwayTeam = idAwayTeam;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	public String getAwayLogo() {
		return awayLogo;
	}
	public void setAwayLogo(String awayLogo) {
		this.awayLogo = awayLogo;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusShort() {
		return statusShort;
	}
	public void setStatusShort(String statusShort) {
		this.statusShort = statusShort;
	}
	
	public FixtureA(int idFixture, Date date, int idRound, String round, int idHomeTeam, String homeTeam,
			String homeLogo, int idAwayTeam, String awayTeam, String awayLogo, String score, String status,
			String statusShort) {
		
		this.idFixture = idFixture;
		this.date = date;
		this.idRound = idRound;
		this.round = round;
		this.idHomeTeam = idHomeTeam;
		this.homeTeam = homeTeam;
		this.homeLogo = homeLogo;
		this.idAwayTeam = idAwayTeam;
		this.awayTeam = awayTeam;
		this.awayLogo = awayLogo;
		this.score = score;
		this.status = status;
		this.statusShort = statusShort;
	}
	
	
}
