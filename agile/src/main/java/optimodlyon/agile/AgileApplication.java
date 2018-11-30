package optimodlyon.agile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import optimodlyon.agile.models.CityMap;

@SpringBootApplication
@EnableAutoConfiguration
public class AgileApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgileApplication.class, args);
		
		// création de l'instance partagée de CityMapSingleton
		CityMap instance = CityMap.getInstance();
	}
}
