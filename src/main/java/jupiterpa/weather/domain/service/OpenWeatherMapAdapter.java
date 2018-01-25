package jupiterpa.weather.domain.service;

import java.io.IOException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import jupiterpa.weather.domain.model.*;

public class OpenWeatherMapAdapter {
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private static final Logger logger = LoggerFactory.getLogger(new OpenWeatherMapAdapter().getClass());
	
	public static Weather getWeather(String forecast) {
		Weather weather = new Weather(-300.0,-300.0,false);
		if (forecast == "") {
			return weather;
		}

		int number = 0;
		double min_temp = 0.0;
		double max_temp = 0.0;
		double sum_rain = 0.0;
		int today = 0;
		
		logger.info(TECHNICAL,"Initialize Forecast Weather");
		
		try {		
		    // Extract info
		    ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(forecast);
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
					if (temp > max_temp || max_temp == 0.0) max_temp = temp;
					if (temp < min_temp || min_temp == 0.0) min_temp = temp;
					Double rain = el.path("rain").path("3h").asDouble();
					logger.info(TECHNICAL,"rain: {}", rain);
					sum_rain += rain;
					number += 1;
				}
			}
			
			if (number > 0) {				
				if ( sum_rain / (double) number > 0.2) {
					logger.info(TECHNICAL,"resulting rain: yes");
					weather.setRaining(true);
				} else {
					logger.info(TECHNICAL,"resulting rain: no");
					weather.setRaining(false);
				}
				min_temp = Math.round( (min_temp - 273.15) * 100.0 ) / 100.0;
				max_temp = Math.round( (max_temp - 273.15) * 100.0 ) / 100.0;
				weather.setMinTemperature( min_temp );
				weather.setMaxTemperature( max_temp );
				logger.info(TECHNICAL,"resulting min Temp: {}", min_temp);
				logger.info(TECHNICAL,"resulting max Temp: {}", max_temp);
			} else {
				weather.setRaining(false);
				weather.setMinTemperature( -300.0 );
				weather.setMaxTemperature( -300.0 );
				logger.warn(TECHNICAL,"No data found");
			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return weather;
	}
	
	public static Daylight getDaylight(String currentWeather) {
		Daylight daylight = new Daylight(0L,0L);
		
		logger.info(TECHNICAL,"Initialize Current Weather");

//		String result = client.getCurrentWeather();
//		if (result == "") {
//			// keep old value [potentiall dummy ones]
//			health.setHealth(new HealthInfo("currentWeather",true,"not available"));
//			return;
//		}
//		health.setHealth(new HealthInfo("currentWeather",false,"available"));
		
		
		try {		
		    // Extract info
		    ObjectMapper mapper = new ObjectMapper();
			    JsonNode root = mapper.readTree(currentWeather);
			Long sunrise = root.path("sys").path("sunrise").asLong();
			Long sunset = root.path("sys").path("sunset").asLong();
			daylight.setSunrise(sunrise);
			daylight.setSunset(sunset);
			
			logger.info(TECHNICAL, "Resulting {}", daylight);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return daylight;
	}
}
