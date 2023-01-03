import java.io.Serializable;

public class FoodSeller implements Serializable {
    private String name;
    private String userName;
    private String password;
    private String emailId;
    private Hotel hotel;
    FoodSeller(String name, String userName, String password,String emailId, Hotel hotel){
        this.name=name;
        this.userName=userName;
        this.password=password;
        this.emailId=emailId;
        this.hotel=hotel;
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
    void setHotel(Hotel hotel){
        this.hotel=hotel;
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
    Hotel getHotel(){
        return hotel;
    }
}
