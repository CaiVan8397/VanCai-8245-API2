
package utilities;


import org.testng.log4testng.Logger;

public class Log {
    public Log() {
    }

    private static final Logger Log = Logger.getLogger(Log.class);


    public static void info(String message) {
        Log.info(message);
    }

    public static void step(String message) {
        Log.info(message);
    }
}