package ProxyPatternExample;

public class ProxyImage implements Image {
    private RealImage realImage;
    private String fileName;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void display() {
        // Lazy initialization and caching
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}