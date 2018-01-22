package jupiterpa.weather.domain.service;

import jupiterpa.weather.domain.model.*;

public interface WeatherService {
	public void update(boolean enforce);
	public Weather getWeather();
	public Daylight getDaylight();
}
