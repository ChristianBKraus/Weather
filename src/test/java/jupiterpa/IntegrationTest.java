package jupiterpa;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import jupiterpa.client.ClientMocking;
import jupiterpa.client.LEDStripClient;
import jupiterpa.client.WeatherClient;
import jupiterpa.controller.Controller;
import jupiterpa.model.Led;
import jupiterpa.model.Color;
import jupiterpa.model.Location;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"mock","test"})
public class IntegrationTest { 
	final String PATH = Controller.PATH; 

	@Autowired
    private MockMvc mockMvc;
	@Autowired
	private WeatherClient weather;
	@Autowired
	private LEDStripClient ledStrip;
	
	@Before
	public void initialize() {
        ClientMocking mock = (ClientMocking) ledStrip;
        mock.inject(null);
	}
    
    @Test
    public void test() throws Exception {
    	ArrayList<String> list = new ArrayList<String>();
    	String forecast = "{ \"list\": [ "
    			+ "{ "
    			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
    			+ "\"rain\": { \"3h\": 0.16 }, "
    			+ "\"main\": { \"temp\": 283.15 }"
    			+ "} ] }";
    	list.add(forecast);
    	Long current_time = (new Date()).getTime();
    	String current = "{ \"sys\": { " 
    			+ "\"sunrise\": " + (current_time - 100) + ", " 
    			+ "\"sunset\": " + (current_time + 100) 
    			+ "} }";
    	list.add(current);
    	
        ClientMocking mock = (ClientMocking) weather;
        mock.inject(list);

    	mockMvc.perform( put(PATH + "/update").content("").contentType(APPLICATION_JSON_UTF8) )
        .andExpect(status().isOk());
    	
    	ClientMocking test = (ClientMocking) ledStrip;
    	
    	@SuppressWarnings("unchecked")
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