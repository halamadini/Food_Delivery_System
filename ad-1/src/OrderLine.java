public class OrderLine {
    private MenuItem item;
    private int quantity;

    public OrderLine(MenuItem item, int quantity) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        this.item = item;
        this.quantity = quantity;
    }

    public double getLineTotal() {
        return item.getPrice() * quantity;
    }
}
