import java.util.ArrayList;
import java.util.Scanner;

public class MeasuringDevice {
    private String deviceName;
    private ArrayList<PurchasableItem> items;

    public MeasuringDevice(String deviceName, ArrayList<PurchasableItem> items) {
        this.deviceName = deviceName;
        this.items = items;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public ArrayList<PurchasableItem> getItems() {
        return items;
    }

    public void addNewItem(PurchasableItem item) {
        System.out.println("Success adding new item" + item);
        items.add(item);
    }

}
