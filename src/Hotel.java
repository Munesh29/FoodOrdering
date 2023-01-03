import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Hotel implements Serializable {
    private String hotelName;
    private final String hotelId;
    private Type hotelType;
    private HashMap<Days,ArrayList<HotelTime>> availableDays;
    private Address address;
    private ArrayList<String> numbers;
    private HashMap<String,ArrayList<Item>> items;
    private ArrayList<String> hideItems;
    private ArrayList<LocalDate> leave;
    Hotel(String hotelName,String hotelId,Type hotelType, HashMap<Days,ArrayList<HotelTime>> availableDays,
          Address address,ArrayList<String> numbers, HashMap<String,ArrayList<Item>> items,ArrayList<String> hideItems,
          ArrayList<LocalDate> leave){
        this.hotelName=hotelName;
        this.hotelId=hotelId;
        this.hotelType=hotelType;
        this.availableDays=availableDays;
        this.address=address;
        this.numbers=numbers;
        this.items=items;
        this.hideItems=hideItems;
        this.leave=leave;
    }
    void setHotelName(String hotelName){
        this.hotelName=hotelName;
    }
    void setHotelType(Type hotelType){
        this.hotelType=hotelType;
    }
    void setAvailableDays(HashMap<Days,ArrayList<HotelTime>> availableDays){
        this.availableDays=availableDays;
    }
    void setHotelAddress(Address address){
        this.address=address;
    }
    void setContactNumbers(ArrayList<String> numbers){
        this.numbers=numbers;
    }
    void setItems(HashMap<String,ArrayList<Item>> items){
        this.items=items;
    }
    void setHideItems(ArrayList<String> hideItems){
        this.hideItems=hideItems;
    }
    void setLeave(ArrayList<LocalDate> leave) {
        this.leave=leave;
    }
    String getHotelName(){
        return hotelName;
    }
    String getHotelId(){
        return hotelId;
    }
    Type getHotelType(){
        return hotelType;
    }
    HashMap<Days,ArrayList<HotelTime>> getAvailableDays(){
        return availableDays;
    }
    Address getHotelAddress(){
        return address;
    }
    ArrayList<String> getNumbers(){
        return numbers;
    }
    HashMap<String,ArrayList<Item>> getItems(){
        return items;
    }
    ArrayList<String> getHideItems() {
        return hideItems;
    }
    ArrayList<LocalDate> getLeave() {
        return leave;
    }
}
