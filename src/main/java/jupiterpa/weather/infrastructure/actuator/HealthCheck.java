package jupiterpa.weather.infrastructure.actuator;


import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import static org.springframework.boot.actuate.health.Health.*;

@Component
public class HealthCheck implements HealthIndicator, InterfaceHealth {
	
	Map<String,HealthInfo> health = new HashMap<String,HealthInfo>();
  
    @Override
    public Health health() {
    	boolean h = true;
    	if (health.size() == 0) {
    		return Health.down().withDetail("service", "down").build();
    		
    	} else {
    		for( HealthInfo info: health.values()) {
    			if (info.error == true)
    				h = false;
    		}
    		Builder b; 
    		if (h == true) {
    			b = Health.up();
    		} else {
    			b = Health.down();
    		}
    		for ( HealthInfo info: health.values()) {
    			b = b.withDetail(info.name, info.message);
    		}
    		return b.build();
    	}
    } 
	@Override
	public void setHealth(HealthInfo info) {
		health.put(info.getName(),info);
	}
}