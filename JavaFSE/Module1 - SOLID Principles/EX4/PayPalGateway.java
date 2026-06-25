package AdapterPatternExample;

public class PayPalGateway {
    public void makePayment(double amountInUSD) {
        System.out.println("Processing payment via PayPal: $" + amountInUSD);
    }
}