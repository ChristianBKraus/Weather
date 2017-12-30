package jupiterpa.weather.service;

public interface Weather {
	public void initialize();
	public Double getTemperature();
	public Boolean isRaining();
}
