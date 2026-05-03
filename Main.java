import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static UserDAO userDAO = new UserDAO();
    static BookingDAO bookingDAO = new BookingDAO();

    // simple sample data based on your wireframe
    static ArrayList<Tool> tools = new ArrayList<>();
    static ArrayList<Workspace> workspaces = new ArrayList<>();

    public static void main(String[] args) {
        loadSampleData();

        while (true) {
            System.out.println("\n===== ARTISAN MAKER SPACE =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Goodbye.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void loadSampleData() {
        tools.add(new Tool("3D Printer", "Available"));
        tools.add(new Tool("Laser Cutter", "In Use"));
        tools.add(new Tool("CNC Machine", "Available"));

        workspaces.add(new Workspace("Room A", "Available"));
        workspaces.add(new Workspace("Room B", "Occupied"));
        workspaces.add(new Workspace("Workshop Area", "Available"));
    }

    static void register() {
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        User user = new User(email, password, "user");

        if (userDAO.register(user)) {
            System.out.println("Registered successfully.");
        } else {
            System.out.println("Registration failed.");
        }
    }

    static void login() {
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        User user = userDAO.login(email, password);

        if (user != null) {
            if (user.getRole().equalsIgnoreCase("admin")) {
                adminDashboard(user);
            } else {
                userDashboard(user);
            }
        } else {
            System.out.println("Invalid login.");
        }
    }

    static void userDashboard(User user) {
        while (true) {
            System.out.println("\n===== USER DASHBOARD =====");
            System.out.println("1. Tools");
            System.out.println("2. Workspaces");
            System.out.println("3. My Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> showTools(user);
                case 2 -> showWorkspaces(user);
                case 3 -> bookingDAO.viewUserBookings(user.getEmail());
                case 4 -> cancelBooking();
                case 5 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void showTools(User user) {
        System.out.println("\n===== TOOLS =====");
        for (int i = 0; i < tools.size(); i++) {
            Tool tool = tools.get(i);
            System.out.println((i + 1) + ". " + tool.getName() + " - " + tool.getStatus());
        }
        System.out.println((tools.size() + 1) + ". Back");
        System.out.print("Choose tool: ");

        int choice = Integer.parseInt(sc.nextLine());

        if (choice == tools.size() + 1) {
            return;
        }

        if (choice < 1 || choice > tools.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Tool selectedTool = tools.get(choice - 1);

        if (!selectedTool.isAvailable()) {
            System.out.println("Tool is not available.");
            return;
        }

        System.out.print("Enter Date: ");
        String date = sc.nextLine();

        System.out.print("Enter Time: ");
        String time = sc.nextLine();

        boolean success = bookingDAO.addBooking(user.getEmail(), "Tool", selectedTool.getName(), date, time);

        if (success) {
            System.out.println("Tool booked successfully.");
        } else {
            System.out.println("Booking failed. Schedule conflict found.");
        }
    }

    static void showWorkspaces(User user) {
        System.out.println("\n===== WORKSPACES =====");
        for (int i = 0; i < workspaces.size(); i++) {
            Workspace workspace = workspaces.get(i);
            System.out.println((i + 1) + ". " + workspace.getName() + " - " + workspace.getStatus());
        }
        System.out.println((workspaces.size() + 1) + ". Back");
        System.out.print("Choose workspace: ");

        int choice = Integer.parseInt(sc.nextLine());

        if (choice == workspaces.size() + 1) {
            return;
        }

        if (choice < 1 || choice > workspaces.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Workspace selectedWorkspace = workspaces.get(choice - 1);

        if (!selectedWorkspace.isAvailable()) {
            System.out.println("Workspace is not available.");
            return;
        }

        System.out.print("Enter Date: ");
        String date = sc.nextLine();

        System.out.print("Enter Time: ");
        String time = sc.nextLine();

        boolean success = bookingDAO.addBooking(user.getEmail(), "Workspace", selectedWorkspace.getName(), date, time);

        if (success) {
            System.out.println("Workspace booked successfully.");
        } else {
            System.out.println("Booking failed. Schedule conflict found.");
        }
    }

    static void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        int bookingID = Integer.parseInt(sc.nextLine());
        bookingDAO.cancelBooking(bookingID);
    }

    static void adminDashboard(User user) {
        Admin admin = new Admin(user.getEmail(), user.getPassword());

        while (true) {
            admin.showAdminMenu();
            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> bookingDAO.viewAllBookings();
                case 2 -> {
                    System.out.print("Enter Booking ID to approve: ");
                    int bookingID = Integer.parseInt(sc.nextLine());
                    bookingDAO.approveBooking(bookingID);
                }
                case 3 -> {
                    System.out.print("Enter Booking ID to reject: ");
                    int bookingID = Integer.parseInt(sc.nextLine());
                    bookingDAO.rejectBooking(bookingID);
                }
                case 4 -> {
                    System.out.println("Admin logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}