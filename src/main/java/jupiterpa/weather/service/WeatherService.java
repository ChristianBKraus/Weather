package jupiterpa.weather.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Component
//@Profile("default")
public class WeatherService implements Weather {
	
	@Autowired WeatherClient client;
	Double temperature = 24.0;
	Boolean raining = false;
		
	public WeatherService() {
//		initialize();
	}
	
	@Override
	public Double getTemperature() {
		return temperature;
	}

	@Override
	public Boolean isRaining() {
		return raining;
	}  

	@Override
	public void initialize() {
		
		int number = 0;
		double sum_temp = 0.0;
		double sum_rain = 0.0;
		int today = 0;

		String result = client.read();
		
		try {		
		    // Extract info
		    ObjectMapper mapper = new ObjectMapper();
			    JsonNode root = mapper.readTree(result);
			ArrayNode list = (ArrayNode) root.path("list");
			Iterator<JsonNode> it = list.elements();
			while (it.hasNext()) {
				JsonNode el = it.next();
				String dt = el.path("dt_txt").asText();
				int time = Integer.parseInt(dt.substring(11, 2));
				int day = Integer.parseInt(dt.substring(8, 2));
				if (today == 0) {
					today = day;
				}
				if ( time >= 6 && time <= 18 && day == today ) {
					Double temp = el.path("main").path("temp").asDouble();
					sum_temp += temp;
					Double rain = el.path("rain").asDouble();
					sum_rain += rain;
					number += 1;
				}
			}
			
			if (number > 0) {
				if ( sum_rain / (double) number > 0.2)
					raining = true;
				temperature = sum_temp / (double) number;
			} else {
				raining = false;
				temperature = 0.0;
			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
