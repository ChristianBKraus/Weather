package jupiterpa.weather.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jupiterpa.weather.domain.client.LEDStripClient;
import jupiterpa.weather.domain.model.Led;

@Component
@Profile("mock")
public class LEDStripClientMock implements LEDStripClient, ClientMocking {
	
	List<Led> leds = new ArrayList<Led>();
	
	@SuppressWarnings("unchecked")
	public void inject(Object result) {
		if (result != null)
		  leds = (List<Led>) result;
	}
	public Object getState() {
		return leds;
	}

	public void set(Led led) {
		leds.add(led);
	}
	@Override
	public void defaultSet(Led led) {
		// TODO Auto-generated method stub
		
	}

}
