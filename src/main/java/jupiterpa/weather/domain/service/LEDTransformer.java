package jupiterpa.weather.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import jupiterpa.weather.domain.model.*;

public class LEDTransformer {
    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private static final Logger logger = LoggerFactory.getLogger(new LEDTransformer().getClass());
	
	public static Collection<LED> transformWeather(Weather weather) {
		Collection<LED> leds = new ArrayList<LED>();
		
		Double max = weather.getMaxTemperature();
		Boolean r = weather.isRaining();
		
		// Temperature
		logger.info(TECHNICAL," Update Temperature 0-10 ({})",max);
		leds.add(new LED(new LEDLocation(4,1), getColor(max)));

		logger.info(TECHNICAL," Update Temperature 10-20 ({})",max);
		leds.add(new LED(new LEDLocation(3,1), getColor(max-10.0)));

		logger.info(TECHNICAL," Update Temperature 20-30 ({})",max);
		leds.add(new LED(new LEDLocation(2,1), getColor(max-20.0)));

		logger.info(TECHNICAL," Update Temperature 30-40 ({})",max);
		leds.add(new LED(new LEDLocation(1,1), getColor(max-30.0)));
		
		// Rain
		logger.info(TECHNICAL," Update Raining ({})", r);
		if (r)
			leds.add(new LED(new LEDLocation(0,1), LEDColor.Yellow));
		else
			leds.add(new LED(new LEDLocation(0,1), LEDColor.Black));

		return leds;
	}
	public static Collection<LED> transformDaylight(Daylight daylight) {
		Collection<LED> leds = new ArrayList<LED>();
		
		Long sunrise = daylight.getSunrise();
		Long sunset  = daylight.getSunset();
		Long current = (new Date()).getTime();
		LEDLocation loc;
		
		logger.info(TECHNICAL," Update Sun");
		loc = new LEDLocation(0,0);
		if ( current > sunrise && current < sunset ) {
			leds.add(new LED(loc,LEDColor.Yellow));
		} else {
			leds.add(new LED(loc,LEDColor.Black));
		}

		logger.info(TECHNICAL," Update Moon");
		loc = new LEDLocation(0,2);
		if ( current <= sunrise || current >= sunset ) {
			leds.add(new LED(loc,LEDColor.Yellow));
		} else {
			leds.add(new LED(loc,LEDColor.Black));
		}
		
		return leds;
	}
	
	
	private static LEDColor getColor(Double temp) {
		if (temp < 0.0) {
			return LEDColor.Black;
		}
		if (temp > 10.0) {
			return LEDColor.Red;
		}
		return new LEDColor((int) (((double)temp / 10.0) * 255), 0, (int) ( ((10.0 - (double)temp ) / 10.0) * 255 ));
	}	
}
