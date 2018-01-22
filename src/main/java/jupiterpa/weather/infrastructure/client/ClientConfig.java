package jupiterpa.weather.infrastructure.client;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="")
public class ClientConfig {
	public static class ClientConfigEntry {
		private String name;
		private String host;
		private String port;
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public String getPort() {
			return port;
		}
		public void setPort(String port) {
			this.port = port;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	private List<ClientConfigEntry> clients;
	
	public List<ClientConfigEntry> getClients() {
		return clients;
	}
	public void setClients(List<ClientConfigEntry> clients) {
		this.clients = clients;
	}
	
	
}
