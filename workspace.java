class Workspace {
    String workspaceName;
    String status;
    double price;

    public Workspace(String workspaceName, String status, double price) {
        this.workspaceName = workspaceName;
        this.status = status;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
