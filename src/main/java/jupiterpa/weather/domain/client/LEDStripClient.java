package jupiterpa.weather.domain.client;

import jupiterpa.weather.domain.model.Led;

public interface LEDStripClient {
	public void set(Led led);
	public void defaultSet(Led led);
}
