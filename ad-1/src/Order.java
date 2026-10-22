import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int counter = 1000;

    private int orderNumber;
    private String customerName;
    private List<OrderLine> lines;
    private OrderStatus status;
    private DeliveryOption delivery;

    public Order(String customerName, DeliveryOption delivery) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }
        if (delivery == null) {
            throw new IllegalArgumentException("Delivery option cannot be null.");
        }

        this.orderNumber = ++counter;
        this.customerName = customerName;
        this.delivery = delivery;
        this.lines = new ArrayList<OrderLine>();
        this.status = OrderStatus.NEW;
    }

    public void addItem(MenuItem item) {
        addItem(item, 1);
    }

    public void addItem(MenuItem item, int qty) {
        if (status != OrderStatus.NEW) {
            throw new IllegalStateException("Items can only be added while the order is NEW.");
        }

        OrderLine line = new OrderLine(item, qty);
        lines.add(line);
    }

    public double getSubtotal() {
        double subtotal = 0.0;

        for (OrderLine line : lines) {
            subtotal += line.getLineTotal();
        }

        return subtotal;
    }

    public double getTotal() {
        return getSubtotal() + delivery.getFee();
    }

    public void pay(PaymentMethod paymentMethod) {
        if (status != OrderStatus.NEW) {
            throw new IllegalStateException("Only a NEW order can be paid.");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method cannot be null.");
        }

        double amountPaid = paymentMethod.pay(getTotal());
        System.out.printf("Amount paid: $%.2f%n", amountPaid);
        status = OrderStatus.PAID;
    }

    public void deliver() {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException("Only a PAID order can be delivered.");
        }

        status = OrderStatus.DELIVERED;
    }

    public void cancel() {
        if (status != OrderStatus.NEW) {
            throw new IllegalStateException("Only a NEW order can be cancelled.");
        }

        status = OrderStatus.CANCELLED;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
