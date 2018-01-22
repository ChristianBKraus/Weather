package jupiterpa.weather.domain.service;

import jupiterpa.weather.domain.model.*;

public interface LEDStripService {
	public void update(Weather weather, Daylight daylight);
}
