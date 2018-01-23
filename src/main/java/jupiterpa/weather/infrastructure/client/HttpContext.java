package jupiterpa.weather.infrastructure.client;

import java.nio.charset.Charset;
import java.util.UUID;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.codec.Base64;

public class HttpContext {
	
	public final static String CORRELATION_ID = "CorrelationID";
	public final static String AUTHENTIATION = "Authentication";
	
	private static ThreadLocal<String> id = new ThreadLocal<String>();
	private static ThreadLocal<User> user = new ThreadLocal<User>();

	
	public static void setCorrelationID(String new_id) {
		id.set(new_id);
	}
	public static String getCorrelationID() {
		return id.get();
	}
	public static void determineCorrelationID(String prefix) {
		String corrID = prefix + " - " + UUID.randomUUID();
		setCorrelationID(corrID);
	}
	public static String getAuthentication() {
		String userName = user.get().getUsername();
		String password = user.get().getPassword();
		String auth = userName + ":" + password;
		byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + new String( encodedAuth );
		return authHeader;
	}
	public static void setUser(User new_user) {
		user.set(new_user);
	}
	
}
