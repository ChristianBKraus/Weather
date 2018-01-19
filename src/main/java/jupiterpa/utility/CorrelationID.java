package jupiterpa.utility;

public class CorrelationID {
	private static ThreadLocal<String> id = new ThreadLocal<String>();
	public final static String CORRELATION_ID = "CorrelationID";
	
	public static void set(String new_id) {
		id.set(new_id);
	}
	public static String get() {
		return id.get();
	}
}
