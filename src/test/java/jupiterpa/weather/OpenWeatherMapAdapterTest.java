package jupiterpa.weather;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;

import jupiterpa.weather.domain.model.Daylight;
import jupiterpa.weather.domain.model.Weather;
import jupiterpa.weather.domain.service.OpenWeatherMapAdapter;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"mock","test"})
public class OpenWeatherMapAdapterTest { 
	
	private String default_forecast = 
			"{ \"list\": [ "
			+ "{ "
			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
			+ "\"rain\": { \"3h\": 0.16 }, "
			+ "\"main\": { \"temp\": 277.06 }"
			+ "} ] }";

    
    @Test
    public void getDefaultWeather() {
    	String forecast = default_forecast;
    	Weather weather = OpenWeatherMapAdapter.getWeather(forecast);
    	assertEquals((Double)3.91,weather.getMinTemperature());
    	assertEquals((Double)3.91,weather.getMaxTemperature());
    	assertEquals(false,weather.isRaining());
    }
    @Test
    public void getSimpleWeatherWithAdditonal() throws Exception {
    	// additional attribute in query result without effect
    	String forecast = "{ \"list\": [ "
    			+ "{ "
    			+ "\"dt_txt\": \"2017-12-20 15:00:00\", "
    			+ "\"rain\": { \"3h\": 0.16, \"2h\": 0.0 }, "
    			+ "\"other\": { \"2h\": 0.16 }, "
    			+ "\"main\": { \"temp\": 277.06 }"
    			+ "} ] }";
    	Weather weather = OpenWeatherMapAdapter.getWeather(forecast);
    	assertEquals((Double)3.91,weather.getMinTemperature());
    	assertEquals((Double)3.91,weather.getMaxTemperature());
    	assertEquals(false,weather.isRaining());
    }
    @Test
    public void getTwoWeather() throws Exception {
    	String forecast = "{ \"list\": [ "
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
    	Weather weather = OpenWeatherMapAdapter.getWeather(forecast);
    	assertEquals((Double)3.91,weather.getMinTemperature());
    	assertEquals((Double)5.91,weather.getMaxTemperature());
    	assertEquals(true,weather.isRaining());
    }
    @Test
    public void getTwoAndOneOffWeather() throws Exception {
    	String forecast = "{ \"list\": [ "
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
    	Weather weather = OpenWeatherMapAdapter.getWeather(forecast);
    	assertEquals((Double)3.91,weather.getMinTemperature());
    	assertEquals((Double)5.91,weather.getMaxTemperature());
    	assertEquals(true,weather.isRaining());
    }
    @Test
    public void getNoWeather() throws Exception {
    	String forecast = "";
    	Weather weather = OpenWeatherMapAdapter.getWeather(forecast);
    	Weather default_weather = new Weather(-300.0,-300.0,false);
    	assertEquals(default_weather.toString(),weather.toString());
    }
    @Test
    public void getDaylight() throws Exception {
    	String current = 
    			"{ \"sys\": { " 
    			+ "\"sunrise\": 1513667947," 
    			+ "\"sunset\": 1513697300 "
    			+ "} }";
    	Daylight daylight = OpenWeatherMapAdapter.getDaylight(current);
    	assertEquals((Long)1513667947L,daylight.getSunrise());
        assertEquals((Long)1513697300L,daylight.getSunset());
    }
    
}