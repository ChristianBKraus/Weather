package jupiterpa.weather.ext.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.*;
import jupiterpa.weather.domain.model.*;
import jupiterpa.weather.domain.service.*;


@RequestMapping(path = Controller.PATH)
@RestController
@Api(value="weather", description="Weather Adapter")
public class Controller {
    public static final String PATH ="/weather";
    
    @Autowired WeatherRepo weatherRepo;
    @Autowired DaylightRepo daylightRepo;
    @Autowired WeatherService service;
    
    @GetMapping("")
    @ApiOperation(value = "View todays Weather", response = Weather.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved Weather")
    })
    public Weather get() {  
    	List<Weather> list = weatherRepo.findAll();
    	if (list.size() > 0)
    		return list.get(0);
    	else
    		return null;
    }
    
    @GetMapping("/daylight")
    @ApiOperation(value = "View todays Sunrise and Sunset", response = Daylight.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved Daylight Hours")
    })
    public Daylight getdaylight() {  
    	List<Daylight> list = daylightRepo.findAll();
    	if (list.size() > 0)
    		return list.get(0);
    	else 
    		return null;
    }
    
    @PutMapping("/update")
    @ApiOperation(value = "Trigger an update of buffered information")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Updated Successfully")
    })
    public void update() {
    	service.updateWeather();
    	service.updateDaylight();
    }

}
