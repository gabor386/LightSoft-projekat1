package football.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

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
	@Scheduled(cron = "0 0/5 * * * ?")
	public void getApiTimeZone() {

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);

		TimeZonePOJO response = restTemplate.getForObject("http://www.api-football.com/demo/api/v2/timezone",
				TimeZonePOJO.class);
		// System.out.println(response.getApi().getTimezone());

		List<String> timeZonaPOJO = response.getApi().getTimezone();

		List<TimeZona> list = timeZoneRepo.findAll().equals(null) ? null : timeZoneRepo.findAll();

		if (list.isEmpty()) {
			System.out.println("Ovde sam 1");
			for (String s : timeZonaPOJO) {
				TimeZona j = new TimeZona();
				j.setTimeZone(s);
				timeZoneRepo.save(j);
			}
		} else {
			System.out.println("Ovde sam 2");

			try {
				ListIterator listIterator = timeZonaPOJO.listIterator();
				ListIterator listIterator1 = list.listIterator();

				while (listIterator.hasNext() && listIterator1.hasNext()) {
					
					String pom = ((String) listIterator.next());
					TimeZona pom1 = ((TimeZona) listIterator1.next());

					if(pom != null)
					pom1.setTimeZone(pom);
					if(pom1 != null)
					timeZoneRepo.save(pom1);
					
					System.out.println(pom);
					System.out.println(pom1);

				}

			} catch (NoSuchElementException e) {
				e.printStackTrace();
			}
		}
	}

}
