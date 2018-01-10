package jupiterpa.service;

import java.util.List;

import jupiterpa.model.Led;
import jupiterpa.model.Weather;

public interface LEDStripService {
	public void update(Weather weather);
}
