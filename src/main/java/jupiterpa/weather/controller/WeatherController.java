package jupiterpa.weather.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jupiterpa.weather.model.Weather;
import jupiterpa.weather.service.WeatherService;


@RequestMapping(path = WeatherController.PATH)
@RestController
public class WeatherController {
    public static final String PATH ="/weather";
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    WeatherService service;
    
    @GetMapping("")
    public Weather get() {
    	logger.info(TECHNICAL,"Service: {}", service);
    	service.initialize();
    	Weather weather = service.getWeather();
    	logger.info(TECHNICAL,"Temperature: {}", weather.getTemperature());
    	logger.info(TECHNICAL,"Raining: {}", weather.isRaining());
    	return weather;
    }

}
