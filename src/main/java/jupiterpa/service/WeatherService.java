package jupiterpa.service;

import jupiterpa.model.Weather;

public interface WeatherService {
	public void initialize();
	public Weather getWeather();
}
