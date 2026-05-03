class Transaction {
    int transactionID;
    double amount;

    public Transaction(int transactionID, double amount) {
        this.transactionID = transactionID;
        this.amount = amount;
    }

    public void display() {
        System.out.println("Transaction ID: " + transactionID);
        System.out.println("Amount: " + amount);
    }
}
