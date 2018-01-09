package jupiterpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.scheduling.annotation.EnableScheduling;

import jupiterpa.service.WeatherService;

@EnableCircuitBreaker
@EnableScheduling
@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired WeatherService service;
	
	public static void main(String args[]){
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		service.initialize();
	}
}
