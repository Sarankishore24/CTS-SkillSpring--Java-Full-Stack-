public class Main {
    public static void main(String[] args) {
        SimpleInventory inv = new SimpleInventory();

        inv.add(new Product("101", "Laptop", 5, 59999.0));
        inv.add(new Product("102", "Phone", 15, 12499.0));
        inv.showAll();

        inv.update("102", 12, 12450.0);
        inv.showAll();

        inv.delete("101");
        inv.showAll();
    }
}