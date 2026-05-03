class GCashPayment extends Payment {
double balance;

public GCashPayment(double amount, double balance) {
super(amount);
this.balance = balance;
}

public void validate() throws Exception {
if (balance < amount) {
throw new Exception("Insufficient balance.");
}
}

public double processPayment() {
return amount * 0.95;
}
} 