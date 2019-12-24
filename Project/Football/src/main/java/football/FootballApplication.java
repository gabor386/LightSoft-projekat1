package football;



import org.springframework.boot.SpringApplication;




import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;




@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
@EntityScan("model")
public class FootballApplication  {
	

	public static void main(String[] args) {
		SpringApplication.run(FootballApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	

}
