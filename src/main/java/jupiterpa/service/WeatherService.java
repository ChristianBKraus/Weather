package jupiterpa.service;

import jupiterpa.model.Daylight;
import jupiterpa.model.Weather;

public interface WeatherService {
	public void update();
	public Weather getWeather();
	public Daylight getDaylight();
}
