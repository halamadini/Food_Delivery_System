public class CardPayment implements PaymentMethod {
    private static final double FEE = 0.02;

    @Override
    public double pay(double amount) {
        return amount * (1 + FEE);
    }
}
