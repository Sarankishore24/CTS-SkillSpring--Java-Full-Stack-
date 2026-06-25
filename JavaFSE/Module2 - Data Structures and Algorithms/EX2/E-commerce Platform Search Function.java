import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Product {
    private int productId;
    private String productName;
    private String category;

    public Product(int productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}

public class SearchFunction {

    public static Product linearSearch(List<Product> products, String targetName) {
        for (Product product : products) {
            if (product.getProductName().equalsIgnoreCase(targetName)) {
                return product;
            }
        }
        return null;
    }

    public static Product binarySearch(List<Product> sortedProducts, String targetName) {
        int low = 0;
        int high = sortedProducts.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            Product midProduct = sortedProducts.get(mid);
            int comparison = midProduct.getProductName().compareToIgnoreCase(targetName);

            if (comparison == 0) {
                return midProduct;
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        List<Product> productsList = new ArrayList<>();
        productsList.add(new Product(101, "Laptop", "Electronics"));
        productsList.add(new Product(102, "Shoes", "Apparel"));
        productsList.add(new Product(103, "Watch", "Accessories"));

        Product resultLinear = linearSearch(productsList, "Shoes");

        List<Product> sortedProductsList = new ArrayList<>(productsList);
        Collections.sort(sortedProductsList, Comparator.comparing(Product::getProductName, String.CASE_INSENSITIVE_ORDER));

        Product resultBinary = binarySearch(sortedProductsList, "Shoes");
    }
}