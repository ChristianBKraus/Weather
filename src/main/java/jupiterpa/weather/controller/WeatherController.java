package jupiterpa.weather.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jupiterpa.weather.service.Weather;


@RequestMapping(path = WeatherController.PATH)
@RestController
public class WeatherController {
    public static final String PATH ="/weather";
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    Weather service;
    
    @GetMapping("")
    public WeatherDTO get() {
    	logger.info(TECHNICAL,"Service: {}", service);
    	logger.info(TECHNICAL,"Temperature: {}", service.getTemperature());
    	logger.info(TECHNICAL,"Raining: {}", service.isRaining());
    	WeatherDTO dto = new WeatherDTO(service.getTemperature(),service.isRaining());
    	return dto;
    }

}
