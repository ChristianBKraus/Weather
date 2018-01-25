package jupiterpa.weather;

import java.util.Collection;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

import jupiterpa.weather.domain.model.*;
import jupiterpa.weather.domain.service.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"mock","test"})
public class TransformerTest { 
	@Test
    public void testWeather() throws Exception {
    	Weather weather = new Weather(9.0,11.0,false);

    	Collection<Led> leds = Transformer.transformWeather(weather);
    	
    	assertThat(leds, hasSize(5) );
    	for(Led led: leds) {
    		switch (led.getLocation().toString()) {
    			case "0/1":  assertEquals(Color.Black.toString(), led.getColor().toString()); break;
    			case "1/1":  assertEquals(Color.Black.toString(), led.getColor().toString()); break;
    			case "2/1":  assertEquals(Color.Black.toString(), led.getColor().toString()); break;
    			case "3/1":  assertEquals(new Color(25,0,229).toString(), led.getColor().toString()); break;
    			case "4/1":  assertEquals(Color.Red.toString(), led.getColor().toString()); break;
    			default: assertEquals(0,1);
    		}
    	}
    }
	@Test
  public void testDaylight() throws Exception {
  	Long current = (new Date()).getTime();
 	Daylight daylight = new Daylight(current-100, current + 100);
  	
  	Collection<Led> leds = Transformer.transformDaylight(daylight);
  	
  	assertThat(leds, hasSize(2) );
  	for(Led led: leds) {
  		switch (led.getLocation().toString()) {
  			case "0/0":  assertEquals(Color.Yellow.toString(), led.getColor().toString()); break;
  			case "0/2":  assertEquals(Color.Black.toString(), led.getColor().toString()); break;
  			default: assertEquals(0,1);
  		}
  	}
  }
}