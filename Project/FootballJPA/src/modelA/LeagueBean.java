package modelA;


public class LeagueBean {

	private String name;
	private int id;

	public LeagueBean(String name , int id) {
		this.name = name;
		this.id = id;
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


	@Override
	public String toString() {
		return String.format("LeagueBean [name=%s, id=%s]", name, id);
	}




	
}
