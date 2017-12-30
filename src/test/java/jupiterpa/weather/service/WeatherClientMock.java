package jupiterpa.weather.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mock")
public class WeatherClientMock implements WeatherClient {
	
	String result = "{ \"list\": [ "
			+ "{ "
			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
			+ "\"rain\": { \"3h\": 0.16 }, "
			+ "\"main\": { \"temp\": 277.06 }"
			+ "} ] }";
	public void inject(String result) {
		this.result = result;
	}

	public String read() {
		return result;
	}

}
