package jupiterpa.model;

public class Weather {
	Double temperature;
	boolean raining;
	
	public Weather() {
		
	}
	public Weather(Double temp, boolean raining) {
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
	
	@Override
	public String toString() {
		return "Weather: Temperature="+temperature + " Raining=" + raining; 
	}
}
