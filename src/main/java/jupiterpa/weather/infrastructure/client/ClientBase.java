package jupiterpa.weather.infrastructure.client;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import jupiterpa.weather.infrastructure.client.ClientConfig.ClientConfigEntry;
import jupiterpa.weather.infrastructure.config.ApplicationConfig;

public class ClientBase<Entity> {

    private static final Marker TECHNICAL = MarkerFactory.getMarker("TECHNICAL");
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected ClientConfig config;
	@Autowired
	protected ApplicationConfig appl;
	
	protected final RestTemplate template = new RestTemplate();
	
	protected Entity get(String clientName, String path, Class<Entity> classType) {
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		URI uri = getUri(clientName,path);
		
		ResponseEntity<Entity> response =  
			template.exchange(uri, HttpMethod.GET, request, classType);
		if ( response == null )
			return null;
		return response.getBody(); 
	}
 	
	protected void put(String clientName, String path, Entity entity) {
		HttpHeaders headers = getHeaders();		
		URI uri = getUri(clientName,path);
		if (headers == null || uri == null) return;
		
		// Request
		HttpEntity<Entity> request = new HttpEntity<>(entity,headers);
		template.put(uri, request);		
	}
	
	@SuppressWarnings("unchecked")
	protected Entity post(String clientName, String path, Entity entity) {
		HttpHeaders headers = getHeaders();		
		URI uri = getUri(clientName,path);
		if (headers == null || uri == null) return null;
		
		// Request
		HttpEntity<Entity> request = new HttpEntity<>(entity,headers);
		return (Entity) template.postForObject(uri, request, entity.getClass());
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
	private URI getUri(String clientName, String path) {
		// Read Configuration
		ClientConfigEntry entry = getClient(clientName);
		if (entry == null) {
			return null;
		}
		
		// Build URI
		URI uri = URI.create("http://"+entry.getHost()+":"+entry.getPort()+path);

		//Logging
		logger.info(TECHNICAL,"Calling GET {} {} [{}]", clientName, uri.toString(), HttpContext.getCorrelationID());

		return uri;
	}
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		
		// Correlation ID
		headers.add(HttpContext.CORRELATION_ID, HttpContext.getCorrelationID() );
		
		// Authorization
		
		headers.add(HttpContext.AUTHENTIATION, HttpContext.getAuthentication() );
			
		return headers;
	}	
}
