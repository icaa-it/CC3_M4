class CashPayment extends Payment {

   public CashPayment(double amount) {
       super(amount);
   }

   public void validate() {
       System.out.println("Cash accepted.");
   }

   public double processPayment() {
       return amount;
   }
}
