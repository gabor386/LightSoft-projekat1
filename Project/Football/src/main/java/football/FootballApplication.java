package football;



import org.springframework.boot.SpringApplication;




import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;




@SpringBootApplication
@EnableScheduling
//@EnableJpaAuditing
@EntityScan("model")
public class FootballApplication  {
	

	public static void main(String[] args) {
		SpringApplication.run(FootballApplication.class, args);
	}

}
