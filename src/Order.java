import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Order implements Serializable {
    private final String orderId;
    private final String customerName;
    private final String hotelName;
    private final ArrayList<Cart> orderItem;
    private final double totalPrice;
    private final int totalItem;
    private final LocalDate orderedDate;
    private final LocalTime orderedTime;
    private final Address address;
    private final ArrayList<String> contactNumber;
    Order(String orderId, String customerName,String hotelName, ArrayList<Cart> orderItem, double totalPrice,
          int totalItem, LocalDate orderedDate, LocalTime orderedTime, Address address, ArrayList<String>
                  contactNumber){
        this.orderId=orderId;
        this.customerName=customerName;
        this.hotelName=hotelName;
        this.orderItem=orderItem;
        this.totalPrice=totalPrice;
        this.totalItem=totalItem;
        this.orderedDate=orderedDate;
        this.orderedTime=orderedTime;
        this.address=address;
        this.contactNumber=contactNumber;
    }
    String getOrderId(){
        return orderId;
    }
    String getCustomerName(){
        return customerName;
    }
    String getHotelName(){
        return hotelName;
    }
    ArrayList<Cart> getOrders(){
        return orderItem;
    }
    double getTotalPrice(){
        return totalPrice;
    }
    int getTotalItem(){
        return totalItem;
    }
    LocalDate getOrderedDate(){
        return orderedDate;
    }
    LocalTime getOrderedTime(){
        return orderedTime;
    }
    Address getAddress(){
        return address;
    }
    ArrayList<String> getContactNumber(){
        return contactNumber;
    }
}
