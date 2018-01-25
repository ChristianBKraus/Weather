package jupiterpa.weather.domain.model;

import org.springframework.data.annotation.Id;

public class Weather {
	
	@Id
	public String id;
	Double minTemperature;
	Double maxTemperature;
	boolean raining;
	
	public Weather() {
		
	}
	public Weather(Double temp_min, Double temp_max, boolean raining) {
		this.minTemperature = temp_min;
		this.maxTemperature = temp_max;
		this.raining = raining;
	}
	

	public Double getMinTemperature() {
		return minTemperature;
	}
	public void setMinTemperature(Double minTemperature) {
		this.minTemperature = minTemperature;
	}
	public Double getMaxTemperature() {
		return maxTemperature;
	}
	public void setMaxTemperature(Double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}
	public boolean isRaining() {
		return raining;
	}

	public void setRaining(boolean raining) {
		this.raining = raining;
	}
	
	@Override
	public String toString() {
		return "Weather: Temperature=" + minTemperature + " - " + maxTemperature + " Raining=" + raining; 
	}
}
