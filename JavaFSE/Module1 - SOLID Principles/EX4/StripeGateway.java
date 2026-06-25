package AdapterPatternExample;

public class StripeGateway {
    public void charge(double amountInCents) {
        System.out.println("Processing payment via Stripe: " + amountInCents + " cents");
    }
}