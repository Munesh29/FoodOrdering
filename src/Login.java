import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Login {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scan=new Scanner(System.in);
        Storage storage=new Storage();
        CustomerLogin customerLogin=new CustomerLogin();
        SellerLogin sellerLogin=new SellerLogin();
        storage.getFiles();
        login:
        while(true){
            showLoginOption();
            String checkLogin=scan.next();
            switch (checkLogin) {
                case "1":
                    if(customerLogin.customerLogin()){
                        break login;
                    }else {
                        break;
                    }
                case "2":
                    customerLogin.addNewCustomer();
                    break;
                case "3":
                    int checkSellerLogin=sellerLogin.foodSellerLogin();
                    if(checkSellerLogin==9){
                        break;
                    }else {
                        break login;
                    }
                case "4":
                    sellerLogin.createFoodSellerAccount();
                    System.out.println("Your Food Seller Account is successfully applied");
                    break;
                case "5":
                    AdminLogin adminLogin=new AdminLogin();
                    int checkAdminLogin=adminLogin.adminLogin();
                    if(checkAdminLogin==9){
                        break;
                    }else {
                        break login;
                    }
                case "0":
                    break login;
                default:
                    System.out.println("Enter a valid number\n");
                    break;
            }
        }
        storage.setFiles();
    }
    static void showLoginOption(){
        System.out.println("Enter \"1\" for Customer Login");
        System.out.println("Enter \"2\" for Create Customer Account");
        System.out.println("Enter \"3\" for FoodSeller Login");
        System.out.println("Enter \"4\" for Apply FoodSeller Account");
        System.out.println("Enter \"5\" for Admin Login");
        System.out.println("Enter \"0\" for Exit");
    }
    void getLoginErrorOption(){
        System.out.println("Entered UserName or Password is Invalid\n ");
        System.out.println("Enter \"1\" for Re-enter UserName and Password");
        System.out.println("Enter \"2\" for Forget your UserName or Password");
        System.out.println("Enter \"9\" for Back");
        System.out.println("Enter \"0\" for Exit");
    }
    void emailIdMismatch(){
        System.out.println("Entered EmailId is Incorrect\n");
        System.out.println("Enter \"1\" for Re-enter your EmailId");
        System.out.println("Enter \"9\" for Back");
        System.out.println("Enter \"0\" for Exit");
    }
    boolean checkEmailIdFormat(String emailId){
        if (Pattern.matches("[a-z]+[0-9]*@[a-z]+[.][a-z]+", emailId)) {
            return emailId.contains("gmail") && (emailId.contains("com") || emailId.contains("in"));
        }
        return false;
    }
    boolean checkContactNumberFormat(String contactNumber){
        return Pattern.matches("[6789][0-9]{9}", contactNumber);//|| Pattern.matches("[1-9][0-9]{7}", contactNumber);
    }
    Address setAddress() {
        Scanner scan=new Scanner(System.in);
        System.out.println("Enter your Number");
        String number=scan.nextLine();
        System.out.println("Enter your Street Name");
        String street=scan.nextLine();
        System.out.println("Enter your City");
        String city= scan.next();
        System.out.println("Enter your State");
        scan.nextLine();
        String state=scan.nextLine();
        int pinCode;
        while(true) {
            try {
                System.out.println("Enter your PinCode");
                pinCode = scan.nextInt();
                break;
            }catch (Exception e){
                System.out.println("Enter a valid Number");
                scan.nextLine();
            }
        }
        Address address= new Address(number, street, city, state, pinCode);
        return address;
    }
    void showEditOption() {
        System.out.println();
        System.out.println("Enter \"1\" for Edit");
        System.out.println("Enter \"2\" for Next");
        System.out.println("Enter \"9\" for Exit Edit");
    }
}
