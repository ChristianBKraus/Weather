package jupiterpa.weather.client;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jupiterpa.weather.domain.client.WeatherClient;

@Component
@Profile("mock")
public class WeatherClientMock implements WeatherClient, ClientMocking {
	
	String forecast = "{ \"list\": [ "
			+ "{ "
			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
			+ "\"rain\": { \"3h\": 0.16 }, "
			+ "\"main\": { \"temp\": 277.06 }"
			+ "} ] }";
	String current = "";
	@SuppressWarnings("unchecked")
	public void inject(Object result) {
		List<String> list = (List<String>) result;
		this.forecast = list.get(0);
		if (list.size() > 1)
  		  this.current = list.get(1);
	}
	public Object getState() {
		return null;
	}

	@Override
	public String getForecast() {
		return forecast;
	}
	@Override
	public String getCurrentWeather() {
		return current;
	}

}
