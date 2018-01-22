package jupiterpa.weather.infrastructure.actuator;

public class HealthInfo {
	String name;
	boolean error;
	String message;
	
	public HealthInfo(String name, boolean error, String message) {
		this.name = name;
		this.error = error;
		this.message = message;
	}
	@Override 
	public String toString() {
		return "Health (" + name + "): " + error + " - " + message;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
