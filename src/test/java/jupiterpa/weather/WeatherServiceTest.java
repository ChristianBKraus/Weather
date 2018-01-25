//package jupiterpa.weather;
//
//import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.ArrayList;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import jupiterpa.weather.client.ClientMocking;
//import jupiterpa.weather.domain.client.WeatherClient;
//import jupiterpa.weather.domain.model.Weather;
//import jupiterpa.weather.domain.service.WeatherServiceImpl;
//import jupiterpa.weather.ext.controller.Controller;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@WithMockUser(roles="USER")
//@ActiveProfiles({"mock","test"})
//public class WeatherServiceTest { 
//	final String PATH = Controller.PATH; 
//
//	@Autowired
//    private MockMvc mockMvc;
//	@Autowired
//	private WeatherClient client;
//	@Autowired
//	private WeatherServiceImpl service;
//    
//    @Test
//    public void getDefaultWeather() throws Exception {
//    	service.update(false);
//    	mockMvc.perform(get(PATH)) 
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//        .andExpect(jsonPath("$.minTemperature").value(3.91))
//        .andExpect(jsonPath("$.maxTemperature").value(3.91))
//        .andExpect(jsonPath("$.raining").value(false));
//    }
//    @Test
//    public void getSimpleWeather() throws Exception {
//    	String result = "{ \"list\": [ "
//    			+ "{ "
//    			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
//    			+ "\"rain\": { \"3h\": 0.16 }, "
//    			+ "\"main\": { \"temp\": 277.06 }"
//    			+ "} ] }";
//    	ArrayList<String> list = new ArrayList<String>();
//    	list.add(result);
//    	
//        ClientMocking mock = (ClientMocking) client;
//        mock.inject(list);
//    	service.update(false);
//    	
//    	mockMvc.perform(get(PATH))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//        .andExpect(jsonPath("$.minTemperature").value(3.91))
//        .andExpect(jsonPath("$.maxTemperature").value(3.91))
//        .andExpect(jsonPath("$.raining").value(false));
//    }
//    @Test
//    public void getSimpleWeatherWithAdditonal() throws Exception {
//    	String result = "{ \"list\": [ "
//    			+ "{ "
//    			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
//    			+ "\"rain\": { \"3h\": 0.16, \"2h\": 0.0 }, "
//    			+ "\"other\": { \"2h\": 0.16 }, "
//    			+ "\"main\": { \"temp\": 277.06 }"
//    			+ "} ] }";
//    	ArrayList<String> list = new ArrayList<String>();
//    	list.add(result);
//    	
//        ClientMocking mock = (ClientMocking) client;
//        mock.inject(list);
//    	service.update(false);
//    	
//    	mockMvc.perform(get(PATH))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//        .andExpect(jsonPath("$.minTemperature").value(3.91))
//        .andExpect(jsonPath("$.maxTemperature").value(3.91))
//        .andExpect(jsonPath("$.raining").value(false));
//    }
//    @Test
//    public void getTwoWeather() throws Exception {
//    	String result = "{ \"list\": [ "
//    			+ "{ "
//    			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
//    			+ "\"rain\": { \"3h\": 0.25 }, "
//    			+ "\"main\": { \"temp\": 277.06 }"
//    			+ "}, "
//    			+ "{ "
//    			+ "\"dt_txt\": \"2017-12-20 17:00:00\", "
//    			+ "\"rain\": { \"3h\": 0.18 }, "
//    			+ "\"main\": { \"temp\": 279.06 }"
//    			+ "} "
//    			+ "] }";
//    	ArrayList<String> list = new ArrayList<String>();
//    	list.add(result);
//    	
//        ClientMocking mock = (ClientMocking) client;
//        mock.inject(list);
//    	service.update(false);
//    	
//    	mockMvc.perform(get(PATH))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//        .andExpect(jsonPath("$.minTemperature").value(3.91))
//        .andExpect(jsonPath("$.maxTemperature").value(5.91))
//        .andExpect(jsonPath("$.raining").value(true));
//    }
//    @Test
//    public void getTwoAndOneOffWeather() throws Exception {
//    	String result = "{ \"list\": [ "
//    			+ "{ "
//    			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
//    			+ "\"rain\": { \"3h\": 0.25 }, "
//    			+ "\"main\": { \"temp\": 277.06 }"
//    			+ "}, "
//    			+ "{ "
//    			+ "\"dt_txt\": \"2017-12-20 17:00:00\", "
//    			+ "\"rain\": { \"3h\": 0.18 }, "
//    			+ "\"main\": { \"temp\": 279.06 }"
//    			+ "}, "
//    			+ "{ "
//    			+ "\"dt_txt\": \"2017-12-20 20:00:00\", "
//    			+ "\"rain\": { \"3h\": 0.18 }, "
//    			+ "\"main\": { \"temp\": 280.06 }"
//    			+ "} "
//    			+ "] }";
//    	ArrayList<String> list = new ArrayList<String>();
//    	list.add(result);
//    	
//        ClientMocking mock = (ClientMocking) client;
//        mock.inject(list);
//    	service.update(false);
//    	
//    	mockMvc.perform(get(PATH))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//        .andExpect(jsonPath("$.minTemperature").value(3.91))
//        .andExpect(jsonPath("$.maxTemperature").value(5.91))
//        .andExpect(jsonPath("$.raining").value(true));
//    }
//    @Test
//    public void getNoWeather() throws Exception {
//    	Weather old_weather = service.getWeather();
//    
//    	String result = "";
//    	ArrayList<String> list = new ArrayList<String>();
//    	list.add(result);
//    	
//        ClientMocking mock = (ClientMocking) client;
//        mock.inject(list);
//    	service.update(false);
//    	
//    	mockMvc.perform(get(PATH))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//        .andExpect(jsonPath("$.minTemperature").value(old_weather.getMinTemperature()))
//        .andExpect(jsonPath("$.maxTemperature").value(old_weather.getMaxTemperature()))
//        .andExpect(jsonPath("$.raining").value(old_weather.isRaining()));
//
//    	service.update(true);
//		old_weather = new Weather(-300.0,-300.0,false);
//    	mockMvc.perform(get(PATH))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//        .andExpect(jsonPath("$.minTemperature").value(old_weather.getMinTemperature()))
//        .andExpect(jsonPath("$.maxTemperature").value(old_weather.getMaxTemperature()))
//        .andExpect(jsonPath("$.raining").value(old_weather.isRaining()));
//    }
//    @Test
//    public void getDaylight() throws Exception {
//    	ArrayList<String> list = new ArrayList<String>();
//    	String forecast = "{ \"list\": [ "
//    			+ "{ "
//    			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
//    			+ "\"rain\": { \"3h\": 0.16 }, "
//    			+ "\"main\": { \"temp\": 277.06 }"
//    			+ "} ] }";
//    	list.add(forecast);
//    	String current = "{ \"sys\": { " 
//    			+ "\"sunrise\": 1513667947," 
//    			+ "\"sunset\": 1513697300 "
//    			+ "} }";
//    	list.add(current);
//    	
//        ClientMocking mock = (ClientMocking) client;
//        mock.inject(list);
//    	service.update(false);
//    	
//    	mockMvc.perform(get(PATH + "/daylight" ))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//        .andExpect(jsonPath("$.sunrise").value(1513667947))
//        .andExpect(jsonPath("$.sunset").value(1513697300));
//    }
//    
//}