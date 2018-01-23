package jupiterpa.weather.domain.service;

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

import jupiterpa.weather.infrastructure.client.HttpContext;
import jupiterpa.weather.infrastructure.config.ApplicationConfig;

@Component
@Profile("default")
public class Scheduler {
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired  WeatherService weatherService;
	@Autowired  LEDStripService ledStripService;
	@Autowired  ApplicationConfig config;
	
	@Scheduled(initialDelay = 0, fixedRate = 3600000)
	public void updateWeather() {
		setContext("Weather");
		logger.info(TECHNICAL,"Update Weather");		
		weatherService.update(false);
		logger.info(TECHNICAL,"Weather updated");
		updateLEDStrip();
	}
	
	@Scheduled(fixedRate = 250000) 
	public void updateLEDStrip() {
		setContext("LedStrip");
		logger.info(TECHNICAL,"Update LedStrip");
		ledStripService.update(weatherService.getWeather(), weatherService.getDaylight());
		logger.info(TECHNICAL,"LEDStrip updated");
	}
	
	private void setContext(String name) {
		User user = new User(config.getName(), config.getUserPassword(), new ArrayList<GrantedAuthority>());
		HttpContext.setUser(user);
		HttpContext.determineCorrelationID("Scheduler/" + name);
        MDC.put("endpoint","Scheduler - Update "+ name );		
	}
}
