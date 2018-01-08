package jupiterpa.weather.service;

import java.io.IOException;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import jupiterpa.weather.model.Weather;

@Component
public class WeatherServiceImpl implements WeatherService {
	
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired WeatherClient client;
	Weather weather = new Weather(-300.0,false);
		
	public WeatherServiceImpl() {
	}
	
	@Override
	public Weather getWeather() {
		return weather;
	}
	
	@Override
	public void initialize() {
		
		int number = 0;
		double sum_temp = 0.0;
		double sum_rain = 0.0;
		int today = 0;
		
		logger.info(TECHNICAL,"Initialize Weather");

		String result = client.read();
		if (result == "") {
			// keep old value [potentiall dummy ones]
			return;
		}
		
		try {		
		    // Extract info
		    ObjectMapper mapper = new ObjectMapper();
			    JsonNode root = mapper.readTree(result);
			ArrayNode list = (ArrayNode) root.path("list");
			Iterator<JsonNode> it = list.elements();
			while (it.hasNext()) {
				JsonNode el = it.next();
				String dt = el.path("dt_txt").asText();
				logger.info(TECHNICAL,"dt_txt: {}", dt);
				int time = Integer.parseInt(dt.substring(11, 13));
				int day = Integer.parseInt(dt.substring(8, 10));
				if (today == 0) {
					today = day;
				}
				if ( time >= 6 && time <= 18 && day == today ) {
					Double temp = el.path("main").path("temp").asDouble();
					logger.info(TECHNICAL,"temp: {}", temp);
					sum_temp += temp;
					Double rain = el.path("rain").path("3h").asDouble();
					logger.info(TECHNICAL,"rain: {}", rain);
					sum_rain += rain;
					number += 1;
				}
			}
			
			if (number > 0) {
				if ( sum_rain / (double) number > 0.2)
					weather.setRaining(true);
				weather.setTemperature( Math.round( ( sum_temp / (double) number - 273.15 )* 100.0 ) / 100.0 );
			} else {
				weather.setRaining(false);
				weather.setTemperature(0.0);
			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
