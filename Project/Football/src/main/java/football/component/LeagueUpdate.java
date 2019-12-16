package football.component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import football.controller.Param;
import football.repository.CountryRepo;
import football.repository.LeagueRepo;
import football.repository.SeasonRepo;
import model.Country;
import model.League;
import model.Season;

@Component
public class LeagueUpdate {

	Param param = new Param();

	@Autowired
	SeasonRepo sr;
	
	@Autowired
	LeagueRepo lr;
	
	@Autowired
	CountryRepo cr;
	
	@Scheduled(cron = "0 0/5 * * * ?")
	public void update () {
		int y = getYear();
		Season s = getSeason(y);
		Iterable<League> ls = apiLeague(s);
		System.out.println(ls.toString());
		System.out.println("Zavrsio sam update");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();  
		   System.out.println(dtf.format(now));  
		
	
		//kada se odradi tabela	
	} 

	
	
	public int getYear() {
			Date d = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			int i = Integer.parseInt(formatter.format(d));
			
			System.out.println("Obradjujem godinu " + i);
		
		return i ;
	}
	

	public Season getSeason(int s) {
		
		Season season = sr.getOne(s);
		return season;
		
	}
	
	

	public List<League> apiLeague(Season se) {

		String json = null;
		List<League> ls = new ArrayList<League>();
		

		try {
			HttpResponse<String> response = Unirest.get(param.getAdd() + "/leagues/season/" + se.getSeason() )
					.header("x-rapidapi-host", param.getH1()).header("x-rapidapi-key", param.getH2()).asString();
			json = response.getBody();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (json != null) {
			System.out.println("Updateing league");
			JSONParser parse = new JSONParser();
			JSONObject o;
			try {
				o = (JSONObject) parse.parse(json);

				JSONObject o1 = (JSONObject) o.get("api");

				Long result = (Long) o1.get("results");
				Integer br = result.intValue();

				JSONArray leagues = (JSONArray) o1.get("leagues");
				for (int i = 0; i < result; i++) {
					JSONObject league = (JSONObject) leagues.get(i);
					League l = new League();
					Long id = (Long) league.get("league_id");
					l.setIdLeague(id.intValue());
					String name = (String) league.get("name");
					l.setName(name);
					String country = (String) league.get("country");
					Country c = cr.findByName(country);
					l.setCountry(c);
					l.setSeasonBean(se);
					String start = (String) league.get("season_start");
					l.setSeasonStart(start);
					String end = (String) league.get("season_end");
					l.setSeasonEnd(end);
					String logo = (String) league.get("logo");
					l.setLogo(logo);
					String type = (String) league.get("type");
					l.setType(type);

					
				    l =lr.save(l);
				    ls.add(l);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		json = null;
		return ls;
	}
	

	
}
