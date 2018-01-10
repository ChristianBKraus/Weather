package jupiterpa.client;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
@Profile("default")
public class WeatherClientImpl implements WeatherClient {
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final RestTemplate template = new RestTemplate();
 	
	@HystrixCommand(fallbackMethod = "defaultRead")
	public String read() {

		URI uri = URI.create("http://api.openweathermap.org/data/2.5/forecast"
				+ "?q=Mannheim,de"
				+ "&appid=87256645a845911683bca32ee3331851");
		
		String result = 
			template.getForObject(uri, String.class);
		
		return result;
	}
	
	public String defaultRead() {
		String result = "";
		
		logger.warn(TECHNICAL, "GET Weather failed");
		
//		result = "{ \"list\": [ "
//				+ "{ "
//				+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
//				+ "\"rain\": { \"3h\": 0.16 }, "
//				+ "\"main\": { \"temp\": 277.06 }"
//				+ "} ] }";
		
		return result;
	}

}