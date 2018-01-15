package jupiterpa.service;

import jupiterpa.model.Daylight;
import jupiterpa.model.Weather;

public interface WeatherService {
	public void update(boolean enforce);
	public Weather getWeather();
	public Daylight getDaylight();
}
