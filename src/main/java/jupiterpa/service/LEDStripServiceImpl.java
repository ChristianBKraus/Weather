package jupiterpa.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import jupiterpa.client.LEDStripClient;
import jupiterpa.client.WeatherClient;
import jupiterpa.model.Color;
import jupiterpa.model.Led;
import jupiterpa.model.Location;
import jupiterpa.model.Weather;

@Component
public class LEDStripServiceImpl implements LEDStripService {
	
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired LEDStripClient client;
		
	public LEDStripServiceImpl() {
	}

	@Override
	public void update(Weather weather) {
		Double min = weather.getMinTemperature();
		Double max = weather.getMaxTemperature();
		Boolean r = weather.isRaining();
		Location loc;
		
		// 0/0 [Jacke]
		loc = new Location(0,0);
		if ( min < 5.0 ) {
		  client.set(new Led(loc,Color.Red));
		} else 
		if ( min < 10.0) {
			client.set(new Led(loc,Color.Yellow));
		} else {
			client.set(new Led(loc,Color.Black));
		}
		
		// 1/0 [Pulli]
		loc = new Location(1,0);
		if ( min < 8.0 ) {
			  client.set(new Led(loc,Color.Red));
			} else 
			if ( min < 15.0) {
				client.set(new Led(loc,Color.Yellow));
			} else {
				client.set(new Led(loc,Color.Black));
			}
	}	
}
