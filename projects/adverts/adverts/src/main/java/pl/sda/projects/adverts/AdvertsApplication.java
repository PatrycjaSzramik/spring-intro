package pl.sda.projects.adverts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.sda.projects.adverts.model.domain.User;

@SpringBootApplication
public class AdvertsApplication {

	public static void main(String[] args) {

		User user =new User();

		SpringApplication.run(AdvertsApplication.class, args);
	}



}
