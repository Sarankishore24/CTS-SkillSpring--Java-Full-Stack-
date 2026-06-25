package BuilderPatternExample;

public class Main {
    public static void main(String[] args) {
        Computer gamingPC = new Computer.ComputerBuilder()
                .setCPU("Intel Core i9")
                .setRAM("32GB")
                .setStorage("2TB NVMe SSD")
                .setGraphicsCardEnabled(true)
                .setBluetoothEnabled(true)
                .build();

        Computer officePC = new Computer.ComputerBuilder()
                .setCPU("Intel Core i3")
                .setRAM("8GB")
                .setStorage("512GB SSD")
                .build();

        System.out.println(gamingPC);
        System.out.println(officePC);
    }
}