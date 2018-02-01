package jupiterpa.weather.domain.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Daylight {
	
	@Id
	public String id;
	
	Long sunset;
	Long sunrise;
	
	public Daylight() {
		id = "current";
	}
	
	public Daylight(Long sunrise, Long sunset) {
		id = "current";
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
