package jupiterpa.weather.ext.scheduler;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import jupiterpa.weather.domain.service.WeatherService;
import jupiterpa.weather.infrastructure.client.HttpContext;
import jupiterpa.weather.infrastructure.config.ApplicationConfig;

@Component
@Profile("default")
public class Scheduler {
	private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	WeatherService service;
	@Autowired
	ApplicationConfig config;

	@Scheduled(initialDelay = 0, fixedRate = 300000)
	public void updateWeather() {
		setContext("Weather");
		logger.info(TECHNICAL, "Update Weather");
		
		boolean success = service.updateWeather();
		
		if (success) {
			logger.info(TECHNICAL, "Weather updated");
		} else {
			logger.warn(TECHNICAL, "Weather update failed");
		}

	}

	@Scheduled(initialDelay = 0, fixedRate = 300100)
	public void updateDaylight() {
		setContext("Daylight");
		logger.info(TECHNICAL, "Update LedStrip");

		boolean success = service.updateDaylight();
		if (success) {
			logger.info(TECHNICAL, "Daylight updated");
		} else {
			logger.info(TECHNICAL, "Daylight update failed");
		};
	}

	private void setContext(String name) {
		if (HttpContext.getCorrelationID() == "") {
			User user = new User(config.getName(), config.getUserPassword(), new ArrayList<GrantedAuthority>());
			HttpContext.setUser(user);
			HttpContext.determineCorrelationID("Scheduler/" + name);
			MDC.put("endpoint", "Scheduler - Update " + name);
		}
	}
}
