package jupiterpa.service;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jupiterpa.client.LEDStripClient;
import jupiterpa.model.Color;
import jupiterpa.model.Daylight;
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
	public void update(Weather weather, Daylight daylight) {
		Double min = weather.getMinTemperature();
		@SuppressWarnings("unused")
		Double max = weather.getMaxTemperature();
		@SuppressWarnings("unused")
		Boolean r = weather.isRaining();
		
		Long sunrise = daylight.getSunrise();
		Long sunset  = daylight.getSunset();
		Long current = (new Date()).getTime();
		
		Location loc;
		
		logger.info(TECHNICAL," Update Jacke");
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
		
		logger.info(TECHNICAL," Update Pulli");
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
		
		logger.info(TECHNICAL," Update Sun");
		// 0/1 [Sun]
		loc = new Location(0,1);
		if ( current > sunrise && current < sunset ) {
			client.set(new Led(loc,Color.Yellow));
		} else {
			client.set(new Led(loc,Color.Black));
		}

		logger.info(TECHNICAL," Update Moon");
		// 1/1 [Moon]
		loc = new Location(1,1);
		if ( current <= sunrise || current >= sunset ) {
			client.set(new Led(loc,Color.Yellow));
		} else {
			client.set(new Led(loc,Color.Black));
		}
	}	
}
