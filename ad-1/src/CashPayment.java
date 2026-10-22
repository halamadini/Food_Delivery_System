public class CashPayment implements PaymentMethod {
    @Override
    public double pay(double amount) {
        return amount;
    }
}
