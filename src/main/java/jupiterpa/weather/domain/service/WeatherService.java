package jupiterpa.weather.domain.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jupiterpa.weather.domain.client.*;
import jupiterpa.weather.domain.model.*;

import jupiterpa.infrastructure.actuator.*;

@Service
public class WeatherService {
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	

	@Autowired WeatherClient  weatherClient;
	@Autowired LEDStripClient ledstripClient;
	
	@Autowired WeatherRepo  weatherRepo;
	@Autowired DaylightRepo daylightRepo;
	
	@Autowired InterfaceHealth health;

	public boolean updateWeather() {
		
		String forecast = weatherClient.getForecast();
		if (forecast != "") {
			Weather weather = OpenWeatherMapAdapter.getWeather(forecast);

			Weather saved_weather = weatherRepo.save(weather);
			logger.info(TECHNICAL,"Weather saved: {}", saved_weather);
			
			Collection<LED> leds = LEDTransformer.transformWeather(weather);
			for (LED led : leds) {
				ledstripClient.set(led);
			}
			
			health.setHealth(new HealthInfo("Weather",true,"not available"));
			return true;
		} else {
			health.setHealth(new HealthInfo("Weather",false,"available"));
			return false;
		}
	}

	public boolean updateDaylight() {

		String currentWeather = weatherClient.getCurrentWeather();
		if (currentWeather != "") {
			Daylight daylight = OpenWeatherMapAdapter.getDaylight(currentWeather);
			
			daylightRepo.save(daylight);
			
			Collection<LED> leds = LEDTransformer.transformDaylight(daylight);
			for (LED led : leds) {
				ledstripClient.set(led);
			}

			health.setHealth(new HealthInfo("Daylight",true,"not available"));
			return true;
		} else {
			health.setHealth(new HealthInfo("Daylight",false,"available"));
			return false;
		}
	}
}
