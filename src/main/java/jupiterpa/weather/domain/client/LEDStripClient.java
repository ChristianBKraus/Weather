package jupiterpa.weather.domain.client;

import jupiterpa.weather.domain.model.LED;

public interface LEDStripClient {
	public void set(LED led);
	public void defaultSet(LED led);
}
