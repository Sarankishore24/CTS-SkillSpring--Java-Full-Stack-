package ProxyPatternExample;

public class Main {
    public static void main(String[] args) {
        Image image = new ProxyImage("high_res_photo.png");

        System.out.println("--- First Call: Image will be loaded and displayed ---");
        image.display(); 

        System.out.println("\n--- Second Call: Image will be displayed from cache ---");
        image.display(); 
    }
}