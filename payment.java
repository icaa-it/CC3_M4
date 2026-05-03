abstract class Payment {
protected double amount;

public Payment(double amount) {
this.amount = amount;
}

public abstract void validate() throws Exception;
public abstract double processPayment();

public void printReceipt(double finalAmount) {
System.out.println("Payment Successful!");
System.out.println("Final Amount: " + finalAmount);
}
} 

