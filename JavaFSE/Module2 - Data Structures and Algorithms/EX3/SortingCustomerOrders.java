import java.util.ArrayList;
import java.util.List;

class Order {
    private int orderId;
    private String customerName;
    private double totalPrice;

    public Order(int orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + orderId +
                ", name='" + customerName + '\'' +
                ", price=$" + totalPrice +
                '}';
    }
}

public class OrderSorter {

    public static void bubbleSort(List<Order> orders) {
        int n = orders.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (orders.get(j).getTotalPrice() > orders.get(j + 1).getTotalPrice()) {
                    Order temp = orders.get(j);
                    orders.set(j, orders.get(j + 1));
                    orders.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    private static int partition(List<Order> orders, int low, int high) {
        double pivot = orders.get(high).getTotalPrice();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (orders.get(j).getTotalPrice() <= pivot) {
                i++;
                Order temp = orders.get(i);
                orders.set(i, orders.get(j));
                orders.set(j, temp);
            }
        }

        Order temp = orders.get(i + 1);
        orders.set(i + 1, orders.get(high));
        orders.set(high, temp);

        return i + 1;
    }

    public static void quickSort(List<Order> orders, int low, int high) {
        if (low < high) {
            int pi = partition(orders, low, high);

            quickSort(orders, low, pi - 1);
            quickSort(orders, pi + 1, high);
        }
    }

    public static void main(String[] args) {
        List<Order> ordersForBubble = new ArrayList<>();
        ordersForBubble.add(new Order(1, "Alice", 250.50));
        ordersForBubble.add(new Order(2, "Bob", 120.00));
        ordersForBubble.add(new Order(3, "Charlie", 450.00));
        ordersForBubble.add(new Order(4, "Diana", 85.25));

        List<Order> ordersForQuick = new ArrayList<>(ordersForBubble);

        bubbleSort(ordersForBubble);

        quickSort(ordersForQuick, 0, ordersForQuick.size() - 1);
    }
}