package modelA;

public class StadingBean {

	private int idTeam;
	
	private String teamName;

	private String form;

	private String groupa;

	private int points;
	
	private int idLeague;
	
	private String logo;

	public StadingBean(int idTeam, String teamName, String form, String groupa, int points, int idLeague , String logo) {
		this.idTeam = idTeam;
		this.teamName = teamName;
		this.form = form;
		this.groupa = groupa;
		this.points = points;
		this.idLeague = idLeague;
		this.logo = logo; 
	}

	public int getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getGroupa() {
		return groupa;
	}

	public void setGroupa(String groupa) {
		this.groupa = groupa;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getIdLeague() {
		return idLeague;
	}

	public void setIdLeague(int idLeague) {
		this.idLeague = idLeague;
	}
	
	

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return String.format("StadingBean [idTeam=%s, teamName=%s, form=%s, groupa=%s, points=%s, idLeague=%s , logo=%s]", idTeam,
				teamName, form, groupa, points, idLeague , logo);
	}	
	
}
