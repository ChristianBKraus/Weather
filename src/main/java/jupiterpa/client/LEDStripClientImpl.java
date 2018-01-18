package jupiterpa.client;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import jupiterpa.config.ClientConfig;
import jupiterpa.model.Led;

@Component
@Profile("default")
public class LEDStripClientImpl implements LEDStripClient {
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ClientConfig config;
	
	private final RestTemplate template = new RestTemplate();
 	
	@HystrixCommand(fallbackMethod = "defaultSet")
	public void set(Led led) {
		for (ClientConfig.ClientConfigEntry entry : config.getClients()) {
			if (entry.getName().matches("ledStrip")) {
				URI uri = URI.create("http://"+entry.getHost()+":"+entry.getPort()+"/ledstrip");
				logger.info(TECHNICAL,"Calling LEDStripService {}", uri.toString());
				template.put(uri, led);
			}
		}
	}
	
	public void defaultSet(Led led) {
		logger.warn(TECHNICAL, "SET LEDStrip failed");
	}

}
