package jupiterpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jupiterpa.model.Daylight;
import jupiterpa.model.Weather;
import jupiterpa.service.WeatherService;


@RequestMapping(path = Controller.PATH)
@RestController
public class Controller {
    public static final String PATH ="/weather";
    
    @Autowired
    WeatherService service;
    
    @GetMapping("")
    public Weather get() {  
    	return service.getWeather();
    }
    @GetMapping("/daylight")
    public Daylight getdaylight() {  
    	return service.getDaylight();
    }
    
    @PutMapping("/update")
    public void update() {
    	service.update();
    }

}
