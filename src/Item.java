import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Item implements Serializable {
    private String itemName;
    private final String itemType;
    private  Type itemKind;
    private final String itemId;
    private double price;
    private ArrayList<String> ingredients;
    private String description;
    private HashMap<Days,ArrayList<HotelTime>> itemAvailableDays;
    Item(String itemName,String itemType,Type itemKind, String itemId,double price,ArrayList<String> ingredients,String description,HashMap<Days,ArrayList<HotelTime>> itemAvailableDays){
        this.itemName=itemName;
        this.itemType=itemType;
        this.itemKind=itemKind;
        this.itemId=itemId;
        this.price=price;
        this.ingredients=ingredients;
        this.description=description;
        this.itemAvailableDays=itemAvailableDays;
    }
    void setItemName(String itemName){
        this.itemName=itemName;
    }
    void setPrice(double price){
        this.price=price;
    }
    void setIngredients(ArrayList<String> ingredients){
        this.ingredients=ingredients;
    }
    void setDescription(String description){
        this.description=description;
    }
    void setItemAvailableDays(HashMap<Days,ArrayList<HotelTime>> itemAvailableDays){
        this.itemAvailableDays=itemAvailableDays;
    }
    String getItemName(){
        return itemName;
    }
    String getItemType(){
        return itemType;
    }
    Type getItemKind(){
        return itemKind;
    }
    String getItemId(){
        return itemId;
    }
    double getPrice(){
        return price;
    }
    ArrayList<String> getIngredients(){
        return ingredients;
    }
    String getDescription(){
        return description;
    }
    HashMap<Days,ArrayList<HotelTime>> getItemAvailableDays(){
        return itemAvailableDays;
    }
}
