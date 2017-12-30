package jupiterpa.weather.controller;

public class WeatherDTO {
	Double temperature;
	boolean raining;
	
	public WeatherDTO() {
		
	}
	public WeatherDTO(Double temp, boolean raining) {
		this.temperature = temp;
		this.raining = raining;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public boolean isRaining() {
		return raining;
	}

	public void setRaining(boolean raining) {
		this.raining = raining;
	}
	
}
