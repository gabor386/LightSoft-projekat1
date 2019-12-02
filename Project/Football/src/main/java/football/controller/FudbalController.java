package football.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.HTTP;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import football.repository.CountryRepo;
import model.Country;

@RestController
public class FudbalController {

	@Autowired
	CountryRepo cr;

	@RequestMapping("/try")
	public void apiCountry() {
		System.out.println("TRYING");
		try {
			HttpResponse<String> response = Unirest.get("http://www.api-football.com/demo/api/v2/countries")
					.header("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
					.header("x-rapidapi-key", "SIGN-UP-FOR-KEY").asString();

			System.out.println(response.getBody());
			List<Country> cs = cr.findAll();
			String drz = response.getBody();
			String[] token1 = drz.split("\\[");
			String[] token2 = token1[1].split("\\]");
			String[] token3 = token2[0].split("}");
			System.out.println("OK  1");
			System.out.println(" broj drzava " + token3.length);
			for (int i = 0; i < token3.length; i++) {
				String[] token4 = token3[i].split(",");
				Country c = new Country();
				for (int j = 0; j < token4.length; j++) {
					token4[j] = token4[j].replaceAll("\"", "").trim();
				}
				if (i > 0) {
					String name[] = token4[1].split(":");
					c.setName(name[1]);
					String[] code = token4[2].split(":");
					c.setCode(code[1]);
					if (code[1].equals("null"))
						c.setCode(null);
					String[] flag = token4[3].split(":");
					if (flag[1].equals("null"))
						c.setFlag(null);
					else {
						flag[2] = flag[2].replaceAll("\"", "");
						c.setFlag(flag[1] + ":" + flag[2]);
					}
					if (!hasCountry(name[1], cs)) {
						cr.save(c);
						System.out.println("Trying to insert");
						System.out.println(c);
					}
				} else {
					String name[] = token4[0].split(":");
					c.setName(name[1]);
					String[] code = token4[1].split(":");
					c.setCode(code[1]);
					if (code[1].equals("null"))
						c.setCode(null);
					String[] flag = token4[2].split(":");
					if (flag[1].equals("null"))
						c.setFlag(null);
					else {
						flag[2] = flag[2].replaceAll("\"", "");
						c.setFlag(flag[1] + ":" + flag[2]);
					}
					if (!hasCountry(name[1], cs)) {
						cr.save(c);
						System.out.println("Trying to insert");
						System.out.println(c);
					}
				}

			}

			System.out.println("Drzave ubacene u bazu");

			// {"country":"Brazil","code":"BR","flag":"https:\/\/media.api-football.com\/flags\/br.svg"
			// {"country":"Brazil"
			// "code":"BR"
			// "flag":"https:\/\/media.api-football.com\/flags\/br.svg"
			// ,{"country":"England","code":"GB","flag":"https:\/\/media.api-football.com\/flags\/gb.svg"
			// ,{"country":"World","code":null,"flag":null

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/try2")
	public void apiCountry2() {
		System.out.println("TRYING");
		String json = null;
		try {
			HttpResponse<String> response = Unirest.get("http://www.api-football.com/demo/api/v2/countries")
					.header("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
					.header("x-rapidapi-key", "SIGN-UP-FOR-KEY").asString();
			json = response.getBody();
			System.out.println(json);


		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (json != null) {
			JsonFactory factory = new JsonFactory();
			try {
				JsonParser parser = factory.createParser(json);

				while (!parser.isClosed()) {
					JsonToken jsonToken = parser.nextToken();
					
					if(JsonToken.FIELD_NAME.equals(jsonToken)){
				        String fieldName = parser.getCurrentName();
				        System.out.println(fieldName);
				        if("results".equals(fieldName)){
				        	jsonToken = parser.nextToken();
				        	int br = parser.getIntValue();
				        	jsonToken = parser.nextToken();
				        	jsonToken = parser.nextToken();
				        	jsonToken = parser.nextToken();
				        	for (int i=0; i<br; i++) {
				        		Country c = new Country();
				        		jsonToken = parser.nextToken();
				        		jsonToken = parser.nextToken();
				        		c.setName(parser.getValueAsString());
				        		jsonToken = parser.nextToken();
				        		jsonToken = parser.nextToken();
				        		c.setCode(parser.getValueAsString());
				        		jsonToken = parser.nextToken();
				        		jsonToken = parser.nextToken();
				        		c.setFlag(parser.getValueAsString());
				        		cr.save(c);
				        		jsonToken = parser.nextToken();
				        		jsonToken = parser.nextToken();
				        	}
				           
				        }
					}    

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean hasCountry(String name, List<Country> cs) {
		for (Country c : cs) {
			if (c.getName().equals(name))
				return true;
		}
		return false;
	}

	@PostMapping("/country")
	public Country postCountry(Country c) {

		cr.save(c);
		return c;
	}

}
