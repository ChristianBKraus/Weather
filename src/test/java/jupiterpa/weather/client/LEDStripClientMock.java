package jupiterpa.weather.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jupiterpa.weather.domain.client.LEDStripClient;
import jupiterpa.weather.domain.model.LED;

@Component
@Profile("mock")
public class LEDStripClientMock implements LEDStripClient, ClientMocking {
	
	List<LED> leds = new ArrayList<LED>();
	
	@SuppressWarnings("unchecked")
	public void inject(Object result) {
		if (result != null)
		  leds = (List<LED>) result;
	}
	public Object getState() {
		return leds;
	}

	public void set(LED led) {
		leds.add(led);
	}
	@Override
	public void defaultSet(LED led) {
		// TODO Auto-generated method stub
		
	}

}
