package ObserverPatternExample;

public class MobileApp implements Observer {
    private String appName;

    public MobileApp(String appName) {
        this.appName = appName;
    }

    @Override
    public void update(String stockSymbol, double price) {
        System.out.println("[Mobile App - " + appName + "] Notification: " + stockSymbol + " is now $" + price);
    }
}