class Booking {
int bookingID;
String email;
String itemType;
String itemName;
String date;
String time;
String status;
double price;

public Booking(int bookingID, String email, String itemType,
String itemName, String date, String time,
String status, double price) {

this.bookingID = bookingID;
this.email = email;
this.itemType = itemType;
this.itemName = itemName;
this.date = date;
this.time = time;
this.status = status;
this.price = price;
}

public double getPrice() {
return price;
}
} 
