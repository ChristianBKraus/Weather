package jupiterpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

import jupiterpa.client.ClientMocking;
import jupiterpa.client.LEDStripClient;
import jupiterpa.controller.Controller;
import jupiterpa.model.Color;
import jupiterpa.model.Daylight;
import jupiterpa.model.Led;
import jupiterpa.model.Location;
import jupiterpa.model.Weather;
import jupiterpa.service.LEDStripService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"mock","test"})
public class LEDStripServiceTest { 
	final String PATH = Controller.PATH; 

	@Autowired
	private LEDStripClient client;
	@Autowired
	private LEDStripService service;
    
	@Before
	public void initialize() {
        ClientMocking mock = (ClientMocking) client;
        mock.inject(new ArrayList<Led>());
	}
	
    @SuppressWarnings("unchecked")
	@Test
    public void test() throws Exception {
    	Long current = (new Date()).getTime();
    	Weather weather = new Weather(9.0,11.0,false);
    	Daylight daylight = new Daylight(current-100, current + 100);
    	service.update(weather,daylight);
    	ClientMocking test = (ClientMocking) client;
    	List<Led> leds = (List<Led>) test.getState();
    	
    	assertThat(leds, hasSize(7) );
    	for(Led led: leds) {
    		switch (led.getLocation().toString()) {
    			case "0/0":  assertEquals(Color.Yellow.toString(), led.getColor().toString()); break;
    			case "0/2":  assertEquals(Color.Black.toString(), led.getColor().toString()); break;
    			case "0/1":  assertEquals(Color.Black.toString(), led.getColor().toString()); break;
    			case "1/1":  assertEquals(Color.Black.toString(), led.getColor().toString()); break;
    			case "2/1":  assertEquals(Color.Black.toString(), led.getColor().toString()); break;
    			case "3/1":  assertEquals(new Color(25,0,229).toString(), led.getColor().toString()); break;
    			case "4/1":  assertEquals(Color.Red.toString(), led.getColor().toString()); break;
    			default: assertEquals(0,1);
    		}
    	}
    }
}