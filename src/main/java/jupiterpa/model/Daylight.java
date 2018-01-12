package jupiterpa.model;

import java.util.Date;

public class Daylight {
	Long sunset;
	Long sunrise;
	
	public Daylight(Long sunrise, Long sunset) {
		this.sunset = sunset;
		this.sunrise = sunrise;
	}
	
	@Override 
	public String toString() {
		return "Sun: " + (new Date(sunrise)).toString() + " - " + (new Date(sunset)).toString();
	}

	public Long getSunset() {
		return sunset;
	}

	public void setSunset(Long sunset) {
		this.sunset = sunset;
	}

	public Long getSunrise() {
		return sunrise;
	}

	public void setSunrise(Long sunrise) {
		this.sunrise = sunrise;
	}
	
}
