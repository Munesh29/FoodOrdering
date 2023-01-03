import java.io.Serializable;

public class Address implements Serializable {
    private final String number;
    private final String street;
    private final String city;
    private final String state;
    private final int pinCode;
    Address(String number,String street,String city,String state,int pinCode){
        this.number=number;
        this.street=street;
        this.city=city;
        this.state=state;
        this.pinCode = pinCode;
    }
    String getDNumber(){
        return number;
    }
    String getStreet(){
        return street;
    }
    String getCity(){
        return city;
    }
    String getState(){
        return state;
    }
    int getPinCode(){
        return pinCode;
    }

}
