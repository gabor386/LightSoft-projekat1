package football.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	//@Scheduled(cron = "0 0/5 * * * ?")
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

		for (String s : timeZonaPOJO) {

			TimeZona timeZona = new TimeZona();
			timeZona.setTimeZone(s);
			timeZoneRepo.save(timeZona);

		}

	}

}
