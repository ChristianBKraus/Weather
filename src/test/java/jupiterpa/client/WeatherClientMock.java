package jupiterpa.client;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jupiterpa.client.WeatherClient;

@Component
@Profile("mock")
public class WeatherClientMock implements WeatherClient, ClientMocking {
	
	String result = "{ \"list\": [ "
			+ "{ "
			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
			+ "\"rain\": { \"3h\": 0.16 }, "
			+ "\"main\": { \"temp\": 277.06 }"
			+ "} ] }";
	public void inject(Object result) {
		this.result = (String) result;
	}
	public Object getState() {
		return null;
	}

	public String read() {
		return result;
	}

}
