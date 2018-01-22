package jupiterpa.weather.infrastructure.client;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import jupiterpa.weather.infrastructure.aop.CorrelationID;
import jupiterpa.weather.infrastructure.client.ClientConfig.ClientConfigEntry;

public class ClientBase<Entity> {

    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected ClientConfig config;
	
	protected final RestTemplate template = new RestTemplate();
 	
	protected void put(String clientName, String path, Entity entity) {
		ClientConfigEntry entry = getClient(clientName);
		if (entry != null) {
			// URI
			URI uri = URI.create("http://"+entry.getHost()+":"+entry.getPort()+path);
				
			// Correlation ID
			HttpHeaders headers = new HttpHeaders();
			headers.add(CorrelationID.CORRELATION_ID, CorrelationID.get());

			//Logging
			logger.info(TECHNICAL,"Calling {} {} [{}]", clientName, uri.toString(), CorrelationID.get());

			// Request
			HttpEntity<Entity> request = new HttpEntity<>(entity,headers);
			template.put(uri, request);
		}
	}
	@SuppressWarnings("unchecked")
	protected Entity post(String clientName, String path, Entity entity) {
		ClientConfigEntry entry = getClient(clientName);
		if (entry != null) {
			// URI
			URI uri = URI.create("http://"+entry.getHost()+":"+entry.getPort()+path);
				
			// Correlation ID
			HttpHeaders headers = new HttpHeaders();
			headers.add(CorrelationID.CORRELATION_ID, CorrelationID.get());

			//Logging
			logger.info(TECHNICAL,"Calling {} {} [{}]", clientName, uri.toString(), CorrelationID.get());

			// Request
			HttpEntity<Entity> request = new HttpEntity<>(entity,headers);
			return (Entity) template.postForObject(uri, request, entity.getClass());
		}
		return null;
	}
	private ClientConfigEntry getClient(String clientName) {
		ClientConfigEntry found_entry = null;

		for (ClientConfig.ClientConfigEntry entry : config.getClients()) {
			if (entry.getName().matches(clientName)) {
				found_entry = entry;
			}
		}
		
		return found_entry;
	}
}
