class CreditCardPayment extends Payment {

public CreditCardPayment(double amount) {
super(amount);
}

public void validate() {
System.out.println("Credit Card validated.");
}

public double processPayment() {
return amount * 0.90;
}
} 
