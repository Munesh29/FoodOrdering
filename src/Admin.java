import java.io.Serializable;
public class Admin implements Serializable {
    private String name;
    private String password;
    private String emailId;

    Admin(String name,String password,String emailId){
        this.name=name;
        this.password=password;
        this.emailId=emailId;
    }
    void setName(String name){
        this.name=name;
    }
    void setPassword(String password){
        this.password=password;
    }
    void setEmailId(String emailID){
        this.emailId=emailID;
    }
    String getName(){
        return name;
    }
    String getPassword(){
        return password;
    }
    String getEmailId(){
        return emailId;
    }
}
