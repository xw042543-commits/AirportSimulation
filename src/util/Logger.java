package util;

public class Logger {

    public static synchronized void log(String tag, String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + tag + ": " + message);
    }

    public static synchronized void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + message);
    }

}
