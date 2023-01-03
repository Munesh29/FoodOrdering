import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    private String name;
    private String userName;
    private String password;
    private String emailId;
    private String contactNumber;
    private Address address;
    private ArrayList<Cart> cart;
    private ArrayList<String> order;
    Customer(String name,String userName,String password,String emailId,String contactNumber,Address address,ArrayList<Cart> cart,ArrayList<String> order){
        this.name=name;
        this.userName=userName;
        this.password=password;
        this.emailId=emailId;
        this.contactNumber=contactNumber;
        this.address=address;
        this.cart=cart;
        this.order=order;
    }
    void setName(String name){
        this.name=name;
    }
    void setUserName(String userName){
        this.userName=userName;
    }
    void setPassword(String password){
        this.password=password;
    }
    void setEmailId(String emailId){
        this.emailId=emailId;
    }
    void setContactNumber(String contactNumber){
        this.contactNumber=contactNumber;
    }
    void setAddress(Address address){
        this.address=address;
    }
    void setCart(ArrayList<Cart> cart){
        this.cart=cart;
    }
    void setOrder(ArrayList<String> order){
        this.order=order;
    }
    String getName(){
        return name;
    }
    String getUserName(){
        return userName;
    }
    String getPassword(){
        return password;
    }
    String getEmailId(){
        return emailId;
    }
    String getContactNumber(){
        return contactNumber;
    }
    Address getAddress(){
        return address;
    }
    ArrayList<Cart> getCart(){
        return cart;
    }
    ArrayList<String> getOrder(){
        return order;
    }
}
