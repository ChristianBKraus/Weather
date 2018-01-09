package jupiterpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class Scheduler {
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	WeatherService service;
	
	@Scheduled(fixedRate = 10000) //3600000)
	public void updateWeather() {
		logger.info(TECHNICAL,"Update Weather");
		service.initialize();
		logger.info(TECHNICAL,"Weather updated");
	}
}
