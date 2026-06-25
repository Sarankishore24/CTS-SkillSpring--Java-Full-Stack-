public class Product {
    public String productId;
    public String productName;
    public int quantity;
    public double price;

    public Product(String productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}