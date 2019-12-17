package football.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.repository.PlayerRepo;
import football.repository.SeasonRepo;
import football.repository.TeamRepo;
import football.repository.TeamplayerRepo;
import model.Player;
import model.Season;
import model.Team;
import model.TeamPlayer;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
public class SquadPlayer {

	@Autowired
	PlayerRepo pr;
	
	@Autowired
	TeamplayerRepo tpr;
	
	@Autowired
	TeamRepo tr;

	@Autowired
	SeasonRepo sr;

	private Param param = new Param();

	@RequestMapping(value="squadplayer")
	public List<Player> getApiSquad(){
		List<Team> teams=tr.findAll();
		List<Season> seasons=sr.findAll();
		String json;
		
		List<Player> listSquadPlayer=new ArrayList<Player>();
		
		for(Team t: teams) {
				for(Season s: seasons) {
		
		try {
			
					
					  HttpResponse<String> response =
					  Unirest.get(param.getAdd()+"/players/squad/"+t.getIdTeam() +"/"+s.getSeason())
					  .header("x-rapidapi-host", param.getH1()) .header("x-rapidapi-key",
					  param.getH2()).asString(); json=response.getBody();
					 
			
			/*HttpResponse<String> response = Unirest.get(param.getAdd()+"/players/squad/1/2018")
					.header("x-rapidapi-host", param.getH1())
					.header("x-rapidapi-key", param.getH2()).asString();
			json=response.getBody();
			*/
			System.out.println(json);
			
			JSONParser parse = new JSONParser();
			JSONObject o = (JSONObject)parse.parse(json);
			JSONObject o1 =  (JSONObject) o.get("api");
			
			Long result=(Long)o1.get("results");
			Integer intigerFixturs=result.intValue();
			
			JSONArray nizPlayers = (JSONArray) o1.get("players");
			
			for(int i=0;i<result;i++) {
				JSONObject objectPlayers=(JSONObject)nizPlayers.get(i);
				
				Player player=new Player();
				//player Id
				Long playerIdPom=(Long)objectPlayers.get("player_id");
				Integer playerId=playerIdPom.intValue();
				player.setIdPlayer(playerId);
				
				//player Name
				player.setPlayerName((String) objectPlayers.get("player_name"));
				
				//player First Name
				player.setFristName((String)objectPlayers.get("firstname"));
				
				//player Last Name
				player.setLastName((String)objectPlayers.get("lastname"));
				
				//number
				
				//position
				player.setPosition((String)objectPlayers.get("position"));
				
				//age
				Long playerAgePom=(Long)objectPlayers.get("age");
				Integer playerAge=playerAgePom.intValue();
				player.setAge(playerAge);
				
				//birth date
				player.setBrithDate((String)objectPlayers.get("birth_date"));
				
				//birth place
				player.setBrithPlace((String)objectPlayers.get("birth_place"));
				
				//birth country
				player.setBrithCountry((String)objectPlayers.get("birth_country"));
				
				//nationality
				player.setNationality((String)objectPlayers.get("nationality"));
				
				//heigth
				String heigthPom=(String)objectPlayers.get("height");
				if(heigthPom!=null && heigthPom!="") {
					player.setHeight(heigthPom);
				}
				
				//weigth
				String weigthPom=(String)objectPlayers.get("weight");
				if(weigthPom!=null && weigthPom!="") {
					player.setWeight(weigthPom);
				}
				
				// save u bazi
				
				  pr.save(player);
				  
				  TeamPlayer tp=new TeamPlayer();
				  
				  tp.setPlayer(player); tp.setSeason(s);
				  
				  tp.setTeam(t);
				  
				  tpr.save(tp);
				 
				
				listSquadPlayer.add(player);
				
				
			}
			
			
			
		} catch (UnirestException | ParseException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}
		}
		return listSquadPlayer;
	}

}
