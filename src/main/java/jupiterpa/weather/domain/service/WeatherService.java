package jupiterpa.weather.domain.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jupiterpa.weather.domain.client.*;
import jupiterpa.weather.domain.model.*;

import jupiterpa.weather.infrastructure.actuator.*;

@Service
public class WeatherService {

	@Autowired
	WeatherClient weatherClient;
	@Autowired
	LEDStripClient ledstripClient;
	@Autowired 
	InterfaceHealth health;

	public boolean updateWeather() {
		
		String forecast = weatherClient.getForecast();
		if (forecast != "") {
			Weather weather = OpenWeatherMapAdapter.getWeather(forecast);

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
