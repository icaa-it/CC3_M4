import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BookingDAO {

    public boolean addBooking(String email, String itemType, String itemName,
                              String bookingDate, String bookingTime) {

        if (hasConflict(itemName, bookingDate, bookingTime)) {
            return false;
        }

        String sql = "INSERT INTO bookings (email, item_type, item_name, booking_date, booking_time, status) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, itemType);
            ps.setString(3, itemName);
            ps.setString(4, bookingDate);
            ps.setString(5, bookingTime);
            ps.setString(6, "Pending");

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Booking error: " + e.getMessage());
            return false;
        }
    }

    public boolean hasConflict(String itemName, String bookingDate, String bookingTime) {
        String sql = "SELECT * FROM bookings WHERE item_name = ? AND booking_date = ? AND booking_time = ? AND status != 'Cancelled' AND status != 'Rejected'";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, itemName);
            ps.setString(2, bookingDate);
            ps.setString(3, bookingTime);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Conflict check error: " + e.getMessage());
            return false;
        }
    }

    public void viewAllBookings() {
        String sql = "SELECT * FROM bookings";

        try {
            Connection conn = DBConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n===== ALL BOOKINGS =====");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println(
                    rs.getInt("booking_id") + " | " +
                    rs.getString("email") + " | " +
                    rs.getString("item_type") + " | " +
                    rs.getString("item_name") + " | " +
                    rs.getString("booking_date") + " | " +
                    rs.getString("booking_time") + " | " +
                    rs.getString("status")
                );
            }

            if (!hasData) {
                System.out.println("No bookings found.");
            }

        } catch (Exception e) {
            System.out.println("View booking error: " + e.getMessage());
        }
    }

    public void viewUserBookings(String email) {
        String sql = "SELECT * FROM bookings WHERE email = ?";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n===== MY BOOKINGS =====");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println(
                    rs.getInt("booking_id") + " | " +
                    rs.getString("item_type") + " | " +
                    rs.getString("item_name") + " | " +
                    rs.getString("booking_date") + " | " +
                    rs.getString("booking_time") + " | " +
                    rs.getString("status")
                );
            }

            if (!hasData) {
                System.out.println("No bookings found.");
            }

        } catch (Exception e) {
            System.out.println("View user booking error: " + e.getMessage());
        }
    }

    public void cancelBooking(int bookingID) {
        String sql = "UPDATE bookings SET status = 'Cancelled' WHERE booking_id = ?";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bookingID);
            ps.executeUpdate();

            System.out.println("Booking cancelled successfully.");

        } catch (Exception e) {
            System.out.println("Cancel booking error: " + e.getMessage());
        }
    }

    public void approveBooking(int bookingID) {
        String sql = "UPDATE bookings SET status = 'Approved' WHERE booking_id = ?";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bookingID);
            ps.executeUpdate();

            System.out.println("Booking approved successfully.");

        } catch (Exception e) {
            System.out.println("Approve booking error: " + e.getMessage());
        }
    }

    public void rejectBooking(int bookingID) {
        String sql = "UPDATE bookings SET status = 'Rejected' WHERE booking_id = ?";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bookingID);
            ps.executeUpdate();

            System.out.println("Booking rejected successfully.");

        } catch (Exception e) {
            System.out.println("Reject booking error: " + e.getMessage());
        }
    }
}
