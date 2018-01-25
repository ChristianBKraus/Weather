package jupiterpa.weather.domain.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jupiterpa.weather.domain.client.*;
import jupiterpa.weather.domain.model.*;

@Service
public class WeatherService {

	@Autowired
	WeatherClient weatherClient;
	@Autowired
	LEDStripClient ledstripClient;

	public boolean updateWeather() {
		
//		String result = client.getForecast();
//		if (result == "") {
//			// keep old value [potentiall dummy ones]
//			health.setHealth(new HealthInfo("forecastWeather",true,"not available"));
//			return;
//		}
//		health.setHealth(new HealthInfo("forecastWeather",false,"available"));
		
		String forecast = weatherClient.getForecast();
		if (forecast != "") {
			Weather weather = OpenWeatherMapAdapter.getWeather(forecast);

			Collection<Led> leds = Transformer.transformWeather(weather);
			for (Led led : leds) {
				ledstripClient.set(led);
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean updateDaylight() {

		String currentWeather = weatherClient.getCurrentWeather();
		if (currentWeather != "") {
			Daylight daylight = OpenWeatherMapAdapter.getDaylight(currentWeather);
			Collection<Led> leds = Transformer.transformDaylight(daylight);
			for (Led led : leds) {
				ledstripClient.set(led);
			}

			return true;
		} else {
			return false;
		}
	}
}
