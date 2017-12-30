package jupiterpa.weather.service;

public interface WeatherService {
	public void initialize();
	public Double getTemperature();
	public Boolean isRaining();
}
