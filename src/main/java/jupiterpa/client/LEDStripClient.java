package jupiterpa.client;

import jupiterpa.model.Led;

public interface LEDStripClient {
	public void set(Led led);
	public void defaultSet(Led led);
}
