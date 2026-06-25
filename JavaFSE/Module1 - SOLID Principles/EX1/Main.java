package SingletonPatternExample;

public class Main {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        logger1.log("Testing first logger reference.");
        logger2.log("Testing second logger reference.");

        if (logger1 == logger2) {
            System.out.println("SUCCESS: Both variables reference the exact same Logger instance.");
        } else {
            System.out.println("FAILURE: Multiple instances exist!");
        }
    }
}