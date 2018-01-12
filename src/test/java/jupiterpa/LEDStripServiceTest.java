package jupiterpa;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
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
import jupiterpa.service.LEDStripServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"mock","test"})
public class LEDStripServiceTest { 
	final String PATH = Controller.PATH; 

	@Autowired
	private LEDStripClient client;
	@Autowired
	private LEDStripServiceImpl service;
    
    @SuppressWarnings("unchecked")
	@Test
    public void test() throws Exception {
    	Long current = (new Date()).getTime();
    	Weather weather = new Weather(10.0,10.0,false);
    	Daylight daylight = new Daylight(current-100, current + 100);
    	service.update(weather,daylight);
    	ClientMocking test = (ClientMocking) client;
    	List<Led> leds = (List<Led>) test.getState();
    	
    	assertThat(leds, hasSize(4) );
    	Led led = leds.get(0);
    	assertEquals((new Location(0,0)).toString(), led.getLocation().toString());
    	assertEquals(Color.Black.toString(), led.getColor().toString());

    	led = leds.get(1);
    	assertEquals((new Location(1,0)).toString(), led.getLocation().toString());
    	assertEquals(Color.Yellow.toString(), led.getColor().toString());

    	led = leds.get(2);
    	assertEquals((new Location(0,1)).toString(), led.getLocation().toString());
    	assertEquals(Color.Yellow.toString(), led.getColor().toString());

    	led = leds.get(3);
    	assertEquals((new Location(1,1)).toString(), led.getLocation().toString());
    	assertEquals(Color.Black.toString(), led.getColor().toString());
    }
}