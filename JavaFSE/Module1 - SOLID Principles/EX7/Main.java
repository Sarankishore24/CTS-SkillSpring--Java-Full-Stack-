package ObserverPatternExample;

public class Main {
    public static void main(String[] args) {
        StockMarket appleStock = new StockMarket("AAPL", 175.50);

        Observer mobileUser = new MobileApp("iPhone Client");
        Observer webDashboard = new WebApp("dashboard.marketwatch.com");

        appleStock.register(mobileUser);
        appleStock.register(webDashboard);

        System.out.println("--- First Price Update ---");
        appleStock.setPrice(178.20);

        System.out.println("\n--- Second Price Update (Deregistering Web Dashboard) ---");
        appleStock.deregister(webDashboard);
        appleStock.setPrice(180.00);
    }
}