package optimodlyon.agile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.MapManagement;

@SpringBootApplication
@EnableAutoConfiguration
public class AgileApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgileApplication.class, args);
		
		// création de l'instance partagée de MapManagementSingleton
		MapManagement instance = MapManagement.getInstance();
	}
}
