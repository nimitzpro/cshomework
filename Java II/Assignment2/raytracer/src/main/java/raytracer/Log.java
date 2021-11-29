package raytracer;

/** Logs the tasks
 * 
 */

public class Log {

	/**
	 * 
	 * @param msg a message
	 */

	public static void error(String msg) {
		System.err.println("ERROR: " + msg);
	}

	/**
	 * 
	 * @param msg a message
	 */

	public static void info(String msg) {
		System.out.println(msg);
	}

	/**
	 * 
	 * @param msg a message
	 */

	public static void debug(String msg) {
		if(Main.DEBUG) System.out.println(msg);
	}
}
