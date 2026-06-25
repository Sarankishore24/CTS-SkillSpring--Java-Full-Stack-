package ObserverPatternExample;

public class WebApp implements Observer {
    private String websiteUrl;

    public WebApp(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    @Override
    public void update(String stockSymbol, double price) {
        System.out.println("[Web App Push - " + websiteUrl + "]: Stock Update -> " + stockSymbol + ": $" + price);
    }
}