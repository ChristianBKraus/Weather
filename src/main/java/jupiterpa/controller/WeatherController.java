package jupiterpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jupiterpa.model.Weather;
import jupiterpa.service.WeatherService;


@RequestMapping(path = WeatherController.PATH)
@RestController
public class WeatherController {
    public static final String PATH ="/weather";
    
    @Autowired
    WeatherService service;
    
    @GetMapping("")
    public Weather get() {  
    	return service.getWeather();
    }

}
