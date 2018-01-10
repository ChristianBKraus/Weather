package jupiterpa;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import jupiterpa.client.ClientMocking;
import jupiterpa.client.WeatherClient;
import jupiterpa.client.WeatherClientMock;
import jupiterpa.controller.Controller;
import jupiterpa.service.WeatherServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"mock","test"})
public class WeatherServiceTest { 
	final String PATH = Controller.PATH; 

	@Autowired
    private MockMvc mockMvc;
	@Autowired
	private WeatherClient client;
	@Autowired
	private WeatherServiceImpl service;
    
    @Test
    public void getDefaultWeather() throws Exception {
    	service.update();
    	mockMvc.perform(get(PATH))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.minTemperature").value(3.91))
        .andExpect(jsonPath("$.maxTemperature").value(3.91))
        .andExpect(jsonPath("$.raining").value(false));
    }
    @Test
    public void getSimpleWeather() throws Exception {
    	String result = "{ \"list\": [ "
    			+ "{ "
    			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
    			+ "\"rain\": { \"3h\": 0.16 }, "
    			+ "\"main\": { \"temp\": 277.06 }"
    			+ "} ] }";
    	
        ClientMocking mock = (ClientMocking) client;
        mock.inject(result);
    	service.update();
    	
    	mockMvc.perform(get(PATH))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.minTemperature").value(3.91))
        .andExpect(jsonPath("$.maxTemperature").value(3.91))
        .andExpect(jsonPath("$.raining").value(false));
    }
    @Test
    public void getSimpleWeatherWithAdditonal() throws Exception {
    	String result = "{ \"list\": [ "
    			+ "{ "
    			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
    			+ "\"rain\": { \"3h\": 0.16, \"2h\": 0.0 }, "
    			+ "\"other\": { \"2h\": 0.16 }, "
    			+ "\"main\": { \"temp\": 277.06 }"
    			+ "} ] }";
    	
        ClientMocking mock = (ClientMocking) client;
        mock.inject(result);
    	service.update();
    	
    	mockMvc.perform(get(PATH))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.minTemperature").value(3.91))
        .andExpect(jsonPath("$.maxTemperature").value(3.91))
        .andExpect(jsonPath("$.raining").value(false));
    }
    @Test
    public void getTwoWeather() throws Exception {
    	String result = "{ \"list\": [ "
    			+ "{ "
    			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
    			+ "\"rain\": { \"3h\": 0.25 }, "
    			+ "\"main\": { \"temp\": 277.06 }"
    			+ "}, "
    			+ "{ "
    			+ "\"dt_txt\": \"2017-12-20 17:00:00\", "
    			+ "\"rain\": { \"3h\": 0.18 }, "
    			+ "\"main\": { \"temp\": 279.06 }"
    			+ "} "
    			+ "] }";
    	
        ClientMocking mock = (ClientMocking) client;
        mock.inject(result);
    	service.update();
    	
    	mockMvc.perform(get(PATH))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.minTemperature").value(3.91))
        .andExpect(jsonPath("$.maxTemperature").value(5.91))
        .andExpect(jsonPath("$.raining").value(true));
    }
    @Test
    public void getTwoAndOneOffWeather() throws Exception {
    	String result = "{ \"list\": [ "
    			+ "{ "
    			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
    			+ "\"rain\": { \"3h\": 0.25 }, "
    			+ "\"main\": { \"temp\": 277.06 }"
    			+ "}, "
    			+ "{ "
    			+ "\"dt_txt\": \"2017-12-20 17:00:00\", "
    			+ "\"rain\": { \"3h\": 0.18 }, "
    			+ "\"main\": { \"temp\": 279.06 }"
    			+ "}, "
    			+ "{ "
    			+ "\"dt_txt\": \"2017-12-20 20:00:00\", "
    			+ "\"rain\": { \"3h\": 0.18 }, "
    			+ "\"main\": { \"temp\": 280.06 }"
    			+ "} "
    			+ "] }";
    	
        ClientMocking mock = (ClientMocking) client;
        mock.inject(result);
    	service.update();
    	
    	mockMvc.perform(get(PATH))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.minTemperature").value(3.91))
        .andExpect(jsonPath("$.maxTemperature").value(5.91))
        .andExpect(jsonPath("$.raining").value(true));
    }
    
}