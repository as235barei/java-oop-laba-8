public class PurchasableItem {
    private String name;
    private int quantity;
    private String supplier;

    public PurchasableItem(String name, int quantity, String supplier) {
        this.name = name;
        this.quantity = quantity;
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "PurchasableItem { " +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", supplier='" + supplier + '\'' +
                " }";
    }

}

