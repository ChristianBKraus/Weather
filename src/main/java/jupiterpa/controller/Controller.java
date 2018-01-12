package jupiterpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jupiterpa.model.Daylight;
import jupiterpa.model.Weather;
import jupiterpa.service.LEDStripService;
import jupiterpa.service.WeatherService;


@RequestMapping(path = Controller.PATH)
@RestController
public class Controller {
    public static final String PATH ="/weather";
    
    @Autowired
    WeatherService weatherService;
    @Autowired
    LEDStripService ledStripService;
    
    @GetMapping("")
    public Weather get() {  
    	return weatherService.getWeather();
    }
    @GetMapping("/daylight")
    public Daylight getdaylight() {  
    	return weatherService.getDaylight();
    }
    
    @PutMapping("/update")
    public void update() {
    	weatherService.update();
    	ledStripService.update(weatherService.getWeather(), weatherService.getDaylight());
    }

}
