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

import football.repository.ComparisonRepo;
import football.repository.FixtureRepo;
import football.repository.PredictionRepo;
import model.Comparison;
import model.Fixture;
import model.Prediction;

@RestController
@RequestMapping(value="/predictionController")
public class PredictionController {

	private Param param=new Param();
	
	@Autowired
	FixtureRepo fixtureRepo;
	
	@Autowired
	PredictionRepo predictionRepo;
	
	@Autowired
	ComparisonRepo comparisonRepo;
	
	
	@RequestMapping(value="/savePrediction", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
	public void apiPrediction() {
	
		List<Fixture> fixtures = fixtureRepo.findAll();

		for (Fixture f : fixtures) {
			
			List<Comparison> retComparisons = new ArrayList<Comparison>();
			
			HttpResponse<String> response = null;
			try {
				response = Unirest.get(param.getAdd() + "/predictions/" + f.getIdFixtures())
						.header("x-rapidapi-host", param.getH1())
						.header("x-rapidapi-key", param.getH2()).asString();
			} catch (UnirestException e1) {
				e1.printStackTrace();
			}

			String json = response.getBody();

			JSONParser parse = new JSONParser();
			JSONObject o;
			
			Prediction prediction = new Prediction();
			
			
			try {
				o = (JSONObject)parse.parse(json);
				JSONObject o1 =  (JSONObject) o.get("api");
				
				JSONArray n1 = (JSONArray) o1.get("predictions");
				
				for(int i=0;i<n1.size();i++){
					
					JSONObject o2 = (JSONObject)n1.get(i);
			
					
					String advice = (String) o2.get("advice");
					prediction.setAdvice(advice == null ? null : advice);
					System.out.println(" Naziv kluba " + advice);
					
					String matchWinner = (String) o2.get("match_winner");
					prediction.setMatchWinner(matchWinner == null ? null : matchWinner);
					
					String underOver = (String) o2.get("under_over");
					prediction.setUnderOver(underOver == null ? null : underOver);
					
					String goalsHome = (String) o2.get("goals_home");
					prediction.setGoalsHome(goalsHome == null ? null : goalsHome);
					
					String goalsAway = (String) o2.get("goals_away");
					prediction.setGoalsAway(goalsAway == null ? null : goalsAway);
					
					JSONObject o3 =  (JSONObject) o2.get("winning_percent");
					
					String home = (String) o3.get("home");
					prediction.setWinningPercenteHome(home == null ? null : home);
					
					String away = (String) o3.get("away");
					prediction.setWinningPercenteAway(away == null ? null : away);
					
					String draws = (String) o3.get("draws");
					prediction.setWinningPercenteDraws(draws == null ? null : draws);
					
					
					prediction.setFixture(f);
					
				   predictionRepo.save(prediction);
				   
				  
				   Comparison forme = new Comparison();
				  
					
                  JSONObject comparation = (JSONObject) o2.get("comparison");
					
					//Formaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
                     
             
					JSONObject o4 = (JSONObject) comparation.get("forme");
					
					
		    		forme.setComparisonName("forme");

					 home = (String) o4.get("home");
					forme.setHome(home == null ? null : home);

					 away = (String) o4.get("away");
					 forme.setAway(away == null ? null : away);
					 
					 forme.setPrediction(prediction);
					
					 retComparisons.add(forme);
					
					//Napaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd
                    
					 Comparison att = new Comparison(); 

					JSONObject o5 = (JSONObject) comparation.get("att");
					 att.setComparisonName("att");
					

					home = (String) o5.get("home");
					att.setHome(home == null ? null : home);

					away = (String) o5.get("away");
					att.setAway(away == null ? null : away);
					
					att.setPrediction(prediction);
					
					 retComparisons.add(att);
					
					      
					
					//Odbranaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
                    
					 Comparison def = new Comparison(); 

					JSONObject o6 = (JSONObject) comparation.get("def");
					
				
					def.setComparisonName("def");

					 home = (String) o6.get("home");
					 def.setHome(home == null ? null : home);

					away = (String) o6.get("away");
					 def.setAway(away == null ? null : away);
					
					 def.setPrediction(prediction);

					        retComparisons.add(def);
					
					
					//FishLawwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
  
					   Comparison fishLaw  = new Comparison(); 

					JSONObject o7 = (JSONObject) comparation.get("fish_law");
					
					fishLaw.setComparisonName("fish_law");

					home = (String) o7.get("home");
				     fishLaw.setHome(home == null ? null : home);

					away = (String) o7.get("away");
					fishLaw.setAway(away == null ? null : away);
					
					fishLaw.setPrediction(prediction);

					     retComparisons.add(fishLaw);
					
                    
					
					//H2Hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh

					     Comparison h2h  = new Comparison();
                    
					JSONObject o8 = (JSONObject) comparation.get("h2h");
					h2h.setComparisonName("h2h");
					

					home = (String) o8.get("home");
					h2h.setHome(home == null ? null : home);

					away = (String) o8.get("away");
					h2h.setAway(away == null ? null : away);
					
					h2h.setPrediction(prediction);
					
					    retComparisons.add(h2h);
					
                   
					
					//Goals H2Hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh

                 
					    Comparison goalsH2h  = new Comparison();
                  

					JSONObject o9 = (JSONObject) comparation.get("goals_h2h");
					
					goalsH2h.setComparisonName("goals_h2h");

					home = (String) o9.get("home");
					goalsH2h.setHome(home == null ? null : home);

					away = (String) o9.get("away");
					goalsH2h.setAway(away == null ? null : away);

					goalsH2h.setPrediction(prediction);
					  
					   retComparisons.add(goalsH2h);
					    
					    
					comparisonRepo.saveAll(retComparisons);	
					
				}
				
				
				
			} catch (ParseException e) {
				
				e.printStackTrace();
			}	
					
		}
		
		
	}
	
	
}
