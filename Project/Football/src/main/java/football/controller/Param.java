package football.controller;

public class Param {
	private String add;
	private String h1;
	private String h2;
	
	public Param() {
//		add="https://server1.api-football.com";
//		h1= "https://server1.api-football.com";
//		h2="2437ee009238740cb9bf18ba3efd23ed";
		
		add = "http://www.api-football.com/demo/api/v2";
		h1 = "api-football-v1.p.rapidapi.com";
		h2 = "SIGN-UP-FOR-KEY";
	}
	
	public String getAdd() {
		return add;
	}

	public String getH1() {
		return h1;
	}

	public String getH2() {
		return h2;
	}
	
	
	
}
