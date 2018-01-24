package jupiterpa.weather.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import jupiterpa.weather.domain.model.*;

public class WeatherTransformer {
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private static final Logger logger = LoggerFactory.getLogger(new WeatherTransformer().getClass());
	
	public static Collection<Led> transformWeather(Weather weather) {
		Collection<Led> leds = new ArrayList<Led>();
		
		Double max = weather.getMaxTemperature();
		Boolean r = weather.isRaining();
		
		// Temperature
		logger.info(TECHNICAL," Update Temperature 0-10 ({})",max);
		leds.add(new Led(new Location(4,1), getColor(max)));

		logger.info(TECHNICAL," Update Temperature 10-20 ({})",max);
		leds.add(new Led(new Location(3,1), getColor(max-10.0)));

		logger.info(TECHNICAL," Update Temperature 20-30 ({})",max);
		leds.add(new Led(new Location(2,1), getColor(max-20.0)));

		logger.info(TECHNICAL," Update Temperature 30-40 ({})",max);
		leds.add(new Led(new Location(1,1), getColor(max-30.0)));
		
		// Rain
		logger.info(TECHNICAL," Update Raining ({})", r);
		if (r)
			leds.add(new Led(new Location(0,1), Color.Yellow));
		else
			leds.add(new Led(new Location(0,1), Color.Black));

		return leds;
	}
	public static Collection<Led> transformDaylight(Daylight daylight) {
		Collection<Led> leds = new ArrayList<Led>();
		
		Long sunrise = daylight.getSunrise();
		Long sunset  = daylight.getSunset();
		Long current = (new Date()).getTime();
		Location loc;
		
		logger.info(TECHNICAL," Update Sun");
		loc = new Location(0,0);
		if ( current > sunrise && current < sunset ) {
			leds.add(new Led(loc,Color.Yellow));
		} else {
			leds.add(new Led(loc,Color.Black));
		}

		logger.info(TECHNICAL," Update Moon");
		loc = new Location(0,2);
		if ( current <= sunrise || current >= sunset ) {
			leds.add(new Led(loc,Color.Yellow));
		} else {
			leds.add(new Led(loc,Color.Black));
		}
		
		return leds;
	}
	
	
	private static Color getColor(Double temp) {
		if (temp < 0.0) {
			return Color.Black;
		}
		if (temp > 10.0) {
			return Color.Red;
		}
		return new Color((int) (((double)temp / 10.0) * 255), 0, (int) ( ((10.0 - (double)temp ) / 10.0) * 255 ));
	}	
}
