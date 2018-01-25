package jupiterpa.weather;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import jupiterpa.weather.client.ClientMocking;
import jupiterpa.weather.domain.client.*;
import jupiterpa.weather.domain.model.*;
import jupiterpa.weather.ext.controller.Controller;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles="ADMIN")
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
    	ClientMocking test = (ClientMocking) ledStrip;

    	ArrayList<String> list = new ArrayList<String>();
    	String forecast = "{ \"list\": [ "
    			+ "{ "
    			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
    			+ "\"rain\": { \"3h\": 0.16 }, "
    			+ "\"main\": { \"temp\": 284.15 }"
    			+ "} ] }";
    	list.add(forecast);
    	Long current_time = (new Date()).getTime();
    	String current = "{ \"sys\": { " 
    			+ "\"sunrise\": " + (current_time - 1000) + ", " 
    			+ "\"sunset\": " + (current_time + 1000) 
    			+ "} }";
    	list.add(current);
    	
        ClientMocking mock = (ClientMocking) weather;
        mock.inject(list);
        test.inject(new ArrayList<LED>());

    	mockMvc.perform( put(PATH + "/update").content("").contentType(APPLICATION_JSON_UTF8) )
        .andExpect(status().isOk());
    	
    	
    	@SuppressWarnings("unchecked")
		List<LED> leds = (List<LED>) test.getState();
    	
    	assertThat(leds, hasSize(7) );
    	for(LED led: leds) {
    		System.out.println(led);
    		System.out.println(led.getLocation().toString());
    		switch (led.getLocation().toString()) {
    			case "0/0":  assertEquals(LEDColor.Yellow.toString(), led.getColor().toString()); break;
    			case "0/2":  assertEquals(LEDColor.Black.toString(), led.getColor().toString()); break;
    			case "0/1":  assertEquals(LEDColor.Black.toString(), led.getColor().toString()); break;
    			case "1/1":  assertEquals(LEDColor.Black.toString(), led.getColor().toString()); break;
    			case "2/1":  assertEquals(LEDColor.Black.toString(), led.getColor().toString()); break;
    			case "3/1":  assertEquals(new LEDColor(25,0,229).toString(), led.getColor().toString()); break;
    			case "4/1":  assertEquals(LEDColor.Red.toString(), led.getColor().toString()); break;
    			default: assertEquals(0,1);
    		}
    	}
    	
    }
}