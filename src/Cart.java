import java.io.Serializable;

public class Cart implements Serializable {
    private String hotelId;
    private Item item;
    private int quantity;
    private double price;
    Cart(String hotelId,Item item,int quantity,double price){
        this.hotelId=hotelId;
        this.item=item;
        this.quantity=quantity;
        this.price=price;
    }
    void setItem(Item item){
        this.item=item;
    }
    void setQuantity(int quantity){
        this.quantity=quantity;
    }
    String getHotelId(){
        return hotelId;
    }
    Item getItem(){
        return item;
    }
    int getQuantity(){
        return quantity;
    }
    double getPrice(){
        return price;
    }
}
