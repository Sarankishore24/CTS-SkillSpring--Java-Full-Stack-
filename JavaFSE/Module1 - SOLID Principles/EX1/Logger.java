package SingletonPatternExample;

public class Logger {
    private static volatile Logger instance;

    private Logger() {
        if (instance != null) {
            throw new IllegalStateException("Logger instance already created.");
        }
    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("[LOG]: " + message);
    }
}