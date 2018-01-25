package jupiterpa.weather.ext.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import jupiterpa.weather.domain.client.LEDStripClient;
import jupiterpa.weather.domain.model.*;
import jupiterpa.weather.infrastructure.client.ClientBase;

@Component
@Profile("default")
public class LEDStripClientImpl extends ClientBase<LED> implements LEDStripClient {
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@HystrixCommand(fallbackMethod = "defaultSet")
	public void set(LED led) {
		put("ledStrip","/ledstrip",led);
	}
	
	public void defaultSet(LED led) {
		logger.warn(TECHNICAL, "SET LEDStrip failed");
	}
	
	public LED getLed(int row, int column) {
		return get("ledStrip","/ledstrip/"+row+"/"+column,LED.class);
	}

}
