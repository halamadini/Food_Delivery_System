import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("=== FOOD DELIVERY SYSTEM ===");

        // 1. Enter customer name
        System.out.print("Enter customer name: ");
        String customerName = input.nextLine().trim();

        while (customerName.isEmpty()) {
            System.out.print("Please enter your name: ");
            customerName = input.nextLine().trim();
        }

        // 2. Choose delivery option
        System.out.println("\nChoose delivery option:");
        System.out.println("1. Standard Delivery - $2.00");
        System.out.println("2. Express Delivery - $5.00");
        System.out.print("Your choice: ");

        int deliveryChoice = readChoice(input, 1, 2);

        DeliveryOption delivery;

        if (deliveryChoice == 1) {
            delivery = new StandardDelivery();
        } else {
            delivery = new ExpressDelivery();
        }

        // 3. Create order
        Order order = new Order(customerName, delivery);

        System.out.println("\nOrder number: " + order.getOrderNumber());
        System.out.println("Order created successfully!");

        // 4. Display fixed-price menu
        boolean addingItems = true;

        while (addingItems) {

            System.out.println("\n============== FOOD MENU ==============");
            System.out.println("1. Burger Meal  - $9.50");
            System.out.println("2. Pizza Meal   - $13.00");
            System.out.println("3. Small Juice  - $3.00");
            System.out.println("4. Large Juice  - $4.00");
            System.out.println("5. Finish adding items");
            System.out.println("6. Cancel order");
            System.out.println("=========================================");
            System.out.print("Choose an option: ");

            int choice = readChoice(input, 1, 6);

            if (choice == 5) {

                if (order.getSubtotal() == 0) {
                    System.out.println(
                            "Please add at least one item first."
                    );
                } else {
                    addingItems = false;
                }

            } else if (choice == 6) {

                order.cancel();

                System.out.println("\nOrder cancelled successfully.");
                System.out.println("Order status: " + order.getStatus());

                input.close();
                return;

            } else {

                System.out.print("Enter quantity: ");
                int quantity = readPositiveInt(input);

                switch (choice) {

                    case 1:
                        order.addItem(
                                new Meal("Burger Meal", 9.50, 0),
                                quantity
                        );
                        System.out.println("Burger Meal added successfully!");
                        break;

                    case 2:
                        order.addItem(
                                new Meal("Pizza Meal", 13.00, 0),
                                quantity
                        );
                        System.out.println("Pizza Meal added successfully!");
                        break;

                    case 3:
                        order.addItem(
                                new Drink("Small Juice", 3.00, false),
                                quantity
                        );
                        System.out.println("Small Juice added successfully!");
                        break;

                    case 4:
                        order.addItem(
                                new Drink("Large Juice", 3.00, true),
                                quantity
                        );
                        System.out.println("Large Juice added successfully!");
                        break;
                }
            }
        }

        // 5. Display bill
        System.out.println("\n========== ORDER SUMMARY ==========");
        System.out.println("Order number: " + order.getOrderNumber());
        System.out.println("Customer name: " + order.getCustomerName());

        System.out.printf(
                "Subtotal: $%.2f%n",
                order.getSubtotal()
        );

        double deliveryFee =
                order.getTotal() - order.getSubtotal();

        System.out.printf(
                "Delivery fee: $%.2f%n",
                deliveryFee
        );

        System.out.printf(
                "Total: $%.2f%n",
                order.getTotal()
        );

        // 6. Choose payment method
        System.out.println("\nChoose payment method:");
        System.out.println("1. Cash");
        System.out.println("2. Card (2% fee)");
        System.out.print("Your choice: ");

        int paymentChoice = readChoice(input, 1, 2);

        PaymentMethod paymentMethod;

        if (paymentChoice == 1) {
            paymentMethod = new CashPayment();
        } else {
            paymentMethod = new CardPayment();
        }

        // 7. Pay for order
        order.pay(paymentMethod);

        System.out.println("Order status: " + order.getStatus());

        // 8. Deliver order
        System.out.println("\n1. Deliver order");
        System.out.println("2. Finish without delivery");
        System.out.print("Your choice: ");

        int finalChoice = readChoice(input, 1, 2);

        if (finalChoice == 1) {
            order.deliver();
            System.out.println("Order delivered successfully!");
        } else {
            System.out.println("Order is paid but not delivered yet.");
        }

        System.out.println(
                "Final order status: " + order.getStatus()
        );

        System.out.println("\nThank you for using Food Delivery System!");

        input.close();
    }

    // Read a choice within a specified range
    private static int readChoice(
            Scanner input, int min, int max
    ) {
        while (true) {
            String value = input.nextLine().trim();

            try {
                int choice = Integer.parseInt(value);

                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException e) {
                // Invalid input
            }

            System.out.print(
                    "Please enter a number from "
                            + min + " to " + max + ": "
            );
        }
    }

    // Read a positive quantity
    private static int readPositiveInt(Scanner input) {
        while (true) {
            String value = input.nextLine().trim();

            try {
                int quantity = Integer.parseInt(value);

                if (quantity > 0) {
                    return quantity;
                }
            } catch (NumberFormatException e) {
                // Invalid input
            }

            System.out.print(
                    "Please enter a quantity greater than zero: "
            );
        }
    }
}
