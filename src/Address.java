import java.io.Serializable;

public class Address implements Serializable {
    private String number;
    private String street;
    private String city;
    private String state;
    private int pinCode;
    Address(String number,String street,String city,String state,int pinCode){
        this.number=number;
        this.street=street;
        this.city=city;
        this.state=state;
        this.pinCode = pinCode;
    }
    void setDNumber(String number){
        this.number=number;
    }
    void setStreet(String street){
        this.street=street;
    }
    void setCity(String city){
        this.city=city;
    }
    void setState(String state){
        this.state=state;
    }
    void setPinCode(int pinCode){
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
