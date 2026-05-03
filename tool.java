class Tool {
    String toolName;
    String status;
    double price;

    public Tool(String toolName, String status, double price) {
        this.toolName = toolName;
        this.status = status;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
