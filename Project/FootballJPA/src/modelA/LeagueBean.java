package modelA;


public class LeagueBean {

	private String name;
	private int id;
	private String logo;

	public LeagueBean(String name , int id , String logo) {
		this.name = name;
		this.id = id;
		this.logo = logo;
	}


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getL() {
		return name;
	}

	public void setL(String name) {
		this.name = name;
	}
	
	


	public String getLogo() {
		return logo;
	}


	public void setLogo(String logo) {
		this.logo = logo;
	}


	@Override
	public String toString() {
		return String.format("LeagueBean [name=%s, id=%s , logo=%s]", name, id , logo);
	}




	
}
