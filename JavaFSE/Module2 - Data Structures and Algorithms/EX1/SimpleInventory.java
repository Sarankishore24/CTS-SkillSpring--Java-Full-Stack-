import java.util.ArrayList;

public class SimpleInventory {
    private ArrayList<Product> list = new ArrayList<>();

    public void add(Product p) {
        list.add(p);
        System.out.println("Added: " + p.productName);
    }

    public void update(String id, int newQty, double newPrice) {
        for (Product p : list) {
            if (p.productId.equals(id)) {
                p.quantity = newQty;
                p.price = newPrice;
                System.out.println("Updated: " + p.productName);
                return;
            }
        }
        System.out.println("Product not found.");
    }

    public void delete(String id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).productId.equals(id)) {
                System.out.println("Deleted: " + list.get(i).productName);
                list.remove(i);
                return;
            }
        }
        System.out.println("Product not found.");
    }

    public void showAll() {
        System.out.println("\n--- Current Inventory ---");
        for (Product p : list) {
            System.out.println(p.productId + " | " + p.productName + " | Qty: " + p.quantity + " | $" + p.price);
        }
    }
}