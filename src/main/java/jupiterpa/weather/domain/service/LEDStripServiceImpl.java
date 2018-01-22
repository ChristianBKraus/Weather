package jupiterpa.weather.domain.service;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jupiterpa.weather.domain.client.*;
import jupiterpa.weather.domain.model.*;

@Component
public class LEDStripServiceImpl implements LEDStripService {
	
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired LEDStripClient client;
		
	public LEDStripServiceImpl() {
	}

	@Override
	public void update(Weather weather, Daylight daylight) {
		updateWeather(weather);
		updateDaylight(daylight);
	}
	
	private void updateWeather(Weather weather) {
		Double max = weather.getMaxTemperature();
		Boolean r = weather.isRaining();
		
		// Temperature
		logger.info(TECHNICAL," Update Temperature 0-10 ({})",max);
		client.set(new Led(new Location(4,1), getColor(max)));

		logger.info(TECHNICAL," Update Temperature 10-20 ({})",max);
		client.set(new Led(new Location(3,1), getColor(max-10.0)));

		logger.info(TECHNICAL," Update Temperature 20-30 ({})",max);
		client.set(new Led(new Location(2,1), getColor(max-20.0)));

		logger.info(TECHNICAL," Update Temperature 30-40 ({})",max);
		client.set(new Led(new Location(1,1), getColor(max-30.0)));
		
		// Rain
		logger.info(TECHNICAL," Update Raining ({})", r);
		if (r)
			client.set(new Led(new Location(0,1), Color.Yellow));
		else
			client.set(new Led(new Location(0,1), Color.Black));
		
	}
	private Color getColor(Double temp) {
		if (temp < 0.0) {
			return Color.Black;
		}
		if (temp > 10.0) {
			return Color.Red;
		}
		return new Color((int) (((double)temp / 10.0) * 255), 0, (int) ( ((10.0 - (double)temp ) / 10.0) * 255 ));
	}
	
	private void updateDaylight(Daylight daylight) {
		Long sunrise = daylight.getSunrise();
		Long sunset  = daylight.getSunset();
		Long current = (new Date()).getTime();
		Location loc;
		
		logger.info(TECHNICAL," Update Sun");
		loc = new Location(0,0);
		if ( current > sunrise && current < sunset ) {
			client.set(new Led(loc,Color.Yellow));
		} else {
			client.set(new Led(loc,Color.Black));
		}

		logger.info(TECHNICAL," Update Moon");
		loc = new Location(0,2);
		if ( current <= sunrise || current >= sunset ) {
			client.set(new Led(loc,Color.Yellow));
		} else {
			client.set(new Led(loc,Color.Black));
		}
	}
}
