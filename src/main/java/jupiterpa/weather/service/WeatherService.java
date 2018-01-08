package jupiterpa.weather.service;

import jupiterpa.weather.model.Weather;

public interface WeatherService {
	public void initialize();
	public Weather getWeather();
}
