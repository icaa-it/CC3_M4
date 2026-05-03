public class Admin extends User {

    public Admin(String email, String password) {
        super(email, password, "admin");
    }

    public void showAdminMenu() {
        System.out.println("\n===== ADMIN DASHBOARD =====");
        System.out.println("1. View All Bookings");
        System.out.println("2. Approve Booking");
        System.out.println("3. Reject Booking");
        System.out.println("4. Logout");
    }
}
