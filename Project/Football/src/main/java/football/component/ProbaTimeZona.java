package football.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import football.repository.TimezonaRepo;
import model.TimeZona;

@Component
public class ProbaTimeZona {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	TimezonaRepo timeZoneRepo;

	@SuppressWarnings("rawtypes")
	@Scheduled(cron = "0 0/2 * * * ?")
	public void getApiTimeZone() {

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);

		TimeZonePOJO response = restTemplate.getForObject("http://www.api-football.com/demo/api/v2/timezone",
				TimeZonePOJO.class);
		// System.out.println(response.getApi().getTimezone());

		List<String> timeZonaPOJO = (ArrayList<String>) response.getApi().getTimezone();

		List<TimeZona> list = timeZoneRepo.findAll().equals(null) ? null : timeZoneRepo.findAll();

		ListIterator<String> listIterator = timeZonaPOJO.listIterator();
		ListIterator<TimeZona> listIterator1 = list.listIterator();

		if (list.isEmpty()) {
			System.out.println("Tabela u bazi je skroz prazna");
			for (String s : timeZonaPOJO) {
				TimeZona j = new TimeZona();
				j.setTimeZone(s);
				timeZoneRepo.save(j);
			}

		} else if (!list.isEmpty() && timeZonaPOJO.size() > list.size()) {
			
			 
			 for (TimeZona t : list) {	 
				 timeZonaPOJO.remove(t.getTimeZone());
			}
			System.out.println(timeZonaPOJO);
			
			
//			System.out.println("Ovde sam");
//             int size = timeZonaPOJO.size();
//            
//			for (int j =0; j < size; j++) {
//				if(j< list.size()) {
//					TimeZona t = list.get(j);
//					if(!t.getTimeZone().equals(timeZonaPOJO.get(j))) {
//						t.setTimeZone(timeZonaPOJO.get(j));
//						timeZoneRepo.save(t);
//					}
//					
//					
//				}else {
//					TimeZona tz = new TimeZona();
//					tz.setTimeZone(timeZonaPOJO.get(j));
//					timeZoneRepo.save(tz);
//				}
//				
//			}
		} else {
			System.out.println("Azuriram");

			try {

				while (listIterator.hasNext() && listIterator1.hasNext()) {

					String pom = ((String) listIterator.next());
					TimeZona pom1 = ((TimeZona) listIterator1.next());

						pom1.setTimeZone(pom);
						timeZoneRepo.save(pom1);		   

				}

			} catch (NoSuchElementException e) {
				e.printStackTrace();
			}
		}
	}
	
}
