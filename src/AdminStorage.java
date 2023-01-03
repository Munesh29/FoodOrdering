import java.io.Serializable;
import java.util.ArrayList;

public class AdminStorage implements Serializable {
    private ArrayList<FoodSeller> newHotel;
    private ArrayList<String> blockHotel;
    private ArrayList<FoodSeller> removedHotel;
    private ArrayList<Order> order;
    AdminStorage(ArrayList<FoodSeller> newHotel, ArrayList<String> blockHotel,ArrayList<FoodSeller> removedHotel,ArrayList<Order> order){
        this.newHotel=newHotel;
        this.blockHotel=blockHotel;
        this.removedHotel=removedHotel;
        this.order=order;
    }
    void setNewHotel(ArrayList<FoodSeller> newHotel){
        this.newHotel=newHotel;
    }
    void  setBlockHotel(ArrayList<String> blockHotel){
        this.blockHotel=blockHotel;
    }
    void  setRemovedHotel(ArrayList<FoodSeller> removedHotel){
        this.removedHotel=removedHotel;
    }
    void setOrder(ArrayList<Order> order){
        this.order=order;
    }
    ArrayList<FoodSeller> getNewHotel(){
        return newHotel;
    }
    ArrayList<String> getBlockHotel(){
        return blockHotel;
    }
    ArrayList<FoodSeller> getRemovedHotel(){
        return removedHotel;
    }
    ArrayList<Order> getOrder(){
        return order;
    }


}
