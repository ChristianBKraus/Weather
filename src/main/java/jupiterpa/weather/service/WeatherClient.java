package jupiterpa.weather.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Profile("default")
public class WeatherClient {
	
	public String read() {
		// HTTP Query
		RestTemplate template = new RestTemplate();
		String result = 
			template.getForObject("http://api.openweathermap.org/data/2.5/forecast?q=Mannheim,de&appid=87256645a845911683bca32ee3331851", String.class);
		return result;
	}

}
