import java.util.ArrayList;
import java.util.Scanner;

public class AdminLogin extends Login{
    Admin admin;
    Scanner scan=new Scanner(System.in);
    Storage storage=new Storage();
    SellerLogin sellerLogin=new SellerLogin();
    int adminLogin() {
        while (true) {
            System.out.println("Enter your UserName ");
            String adminName = scan.next();
            System.out.println("Enter your Password ");
            String password = scan.next();
            admin=storage.checkAdminLogin(adminName, password);
            if (admin!=null) {
                while(true){
                    getAdminLoginOption();
                    String adminOption=scan.next();
                    switch (adminOption){
                        case "1":
                            sellerLogin.createFoodSellerAccount();
                            storage.setSeller(storage.getAdminStorage().getNewHotel().get(storage.getAdminStorage().getNewHotel().size()-1));
                            storage.getAdminStorage().getNewHotel().remove(storage.getAdminStorage().getNewHotel().size()-1);
                            System.out.println("Food Seller is Successfully Added");
                            break;
                        case "2":
                            int checkAdmin=acceptNewHotel();
                            if(checkAdmin==9){
                                break;
                            }else{
                                return 0;
                            }
                        case "3":
                            CustomerLogin customerLogin=new CustomerLogin();
                            customerLogin.addNewCustomer();
                            System.out.println("Customer is Successfully Added");
                            break;
                        case "4":
                            addAdmin();
                            break;
                        case "5":
                            editAdmin(admin);
                            break;
                        case "6":
                            viewSeller();
                            break;
                        case "9":
                            return 9;
                        case "0":
                            return 0;
                        default:
                            System.out.println("Enter a valid Input");
                    }
                }

            } else {
                checkEmailId:
                while (true) {
                    getLoginErrorOption();
                    String checkAdminOption = scan.next();
                    switch (checkAdminOption) {
                        case "1":
                            break checkEmailId;
                        case "2":
                            while (true) {
                                System.out.println("Enter your emailId");
                                String emailId = scan.next();
                                admin = storage.checkAdminEmailId(emailId);
                                if (admin != null) {
                                    if (!(admin.getName().equals(adminName))) {
                                        System.out.println("\nUserName : " + admin.getName());
                                    }
                                    if (!(admin.getPassword().equals(password))) {
                                        System.out.println("Password : " + admin.getPassword());
                                    }
                                    System.out.println();
                                    break checkEmailId;
                                } else {
                                    emailIdMismatch();
                                    String checkEmailOption = scan.next();
                                    checkEmail:
                                    while(true) {
                                        switch (checkEmailOption) {
                                            case "1":
                                                break checkEmail;
                                            case "9":
                                                return 9;
                                            case "0":
                                                return 0;
                                            default:
                                                System.out.println("Enter a valid number\n");
                                                break;
                                        }
                                    }
                                }
                            }
                        case "9":
                            return 9;
                        case "0":
                            return 0;
                        default:
                            System.out.println("Enter a valid number");
                            break;
                    }
                }
            }
        }
    }
    int acceptNewHotel() {
        ArrayList<FoodSeller> sellers=storage.getAdminStorage().getNewHotel();

        if(sellers!=null) {
            for (int i = 0;i<sellers.size(); ) {
                sellerLogin.showSellerDetails(sellers.get(i));
                showNewHotelOption(i, (sellers.size() - 1));
                while (true) {
                    String checkOption = scan.next();
                    if (checkOption.equals("1") && (i != (sellers.size() - 1))) {
                        i++;
                        break;
                    } else if (checkOption.equals("2") && (i != 0)) {
                        i--;
                        break;
                    } else if (checkOption.equals("3")) {
                        storage.setSeller(sellers.get(i));
                        ArrayList<FoodSeller> newHotel=storage.getAdminStorage().getNewHotel();
                        newHotel.remove(sellers.get(i));
                        storage.getAdminStorage().setNewHotel(newHotel);
                        break;
                    } else if (checkOption.equals("4")) {
                        ArrayList<FoodSeller> removedHotel=storage.getAdminStorage().getRemovedHotel();
                        if(removedHotel!=null){
                            removedHotel.add(sellers.get(i));
                            storage.getAdminStorage().setRemovedHotel(removedHotel);
                            ArrayList<FoodSeller> newHotel=storage.getAdminStorage().getNewHotel();
                            newHotel.remove(sellers.get(i));
                            storage.getAdminStorage().setNewHotel(newHotel);
                        }else {
                            ArrayList<FoodSeller> foodSeller=new ArrayList<>();
                            foodSeller.add(sellers.get(i));
                            storage.getAdminStorage().setRemovedHotel(foodSeller);
                            ArrayList<FoodSeller> newHotel=storage.getAdminStorage().getNewHotel();
                            newHotel.remove(sellers.get(i));
                            storage.getAdminStorage().setNewHotel(newHotel);
                        }
                        break;
                    } else if (checkOption.equals(("9"))) {
                        return 9;
                    } else if (checkOption.equals("0")) {
                        return 0;
                    } else {
                        System.out.println("Enter a valid number");
                    }
                }
            }
        }
        System.out.println("New Hotel list is Empty");
        return 9;
    }
    void addAdmin(){
        String name;
        while (true) {
            System.out.println("Enter a Admin Name");
            name = scan.next();
            if(storage.checkAdminName(name)){
                break;
            }else {
                System.out.println("Entered Name is already Exist");
            }
        }
        System.out.println("Enter a Password");
        String password=scan.next();
        String emailId;
        while (true) {
            System.out.println("Enter a Email Id");
            emailId = scan.next();
            if(storage.checkNewAdminEmailId(emailId)){
                break;
            }else {
                System.out.println("Entered Email Id is already Exist");
            }
        }
        Admin admin=new Admin(name,password,emailId);
        storage.setAdmin(admin);
    }
    void editAdmin(Admin admin){
        System.out.println("User Name : "+admin.getName());
        showEditOption();
        editName:
        while (true){
            String checkEditOption= scan.next();
            switch (checkEditOption){
                case "1":
                    String name;
                    while (true) {
                        System.out.println("Enter a Admin Name");
                        name = scan.next();
                        if(storage.checkAdminName(name)){
                            break;
                        }else {
                            System.out.println("Entered Name is already Exist");
                        }
                    }
                    admin.setName(name);
                    break editName;
                case "2":
                    break editName;
                case "9":
                    return;
                default:
                    System.out.println("Enter a valid number");
            }
        }
        System.out.println("Password : "+admin.getPassword());
        showEditOption();
        editPassword:
        while (true){
            String checkEditOption= scan.next();
            switch (checkEditOption){
                case "1":
                    System.out.println("Enter a Password");
                    String password=scan.next();
                    admin.setPassword(password);
                    break editPassword;
                case "2":
                    break editPassword;
                case "9":
                    return;
                default:
                    System.out.println("Enter a valid number");
            }
        }
        System.out.println("Email Id : "+admin.getEmailId());
        showEditOption();
        editEmailId:
        while (true){
            String checkEditOption= scan.next();
            switch (checkEditOption){
                case "1":
                    String emailId;
                    while (true) {
                        System.out.println("Enter a Email Id");
                        emailId = scan.next();
                        if(storage.checkNewAdminEmailId(emailId)){
                            break;
                        }else {
                            System.out.println("Entered Email Id is already Exist");
                        }
                    }
                    admin.setEmailId(emailId);
                    break editEmailId;
                case "2":
                    break editEmailId;
                case "9":
                    return;
                default:
                    System.out.println("Enter a valid number");
            }
        }
    }
    void viewSeller(){
        ArrayList<FoodSeller> sellers=storage.getSellers();
        if(sellers!=null){
            for (int i=0;i<sellers.size();){
                sellerLogin.showSellerDetails(sellers.get(i));
                boolean isBlock=false;
                if(storage.getAdminStorage().getBlockHotel()!=null) {
                    for (String blockId : storage.getAdminStorage().getBlockHotel()) {
                        if (sellers.get(i).getHotel().getHotelId().equals(blockId)) {
                            isBlock = true;
                            break;
                        }
                    }
                }
                while (true){
                    showSellerOption(i,sellers.size(),isBlock);
                    String checkOption= scan.next();
                    if(checkOption.equals("1") && i!=0){
                        i--;
                        break;
                    } else if (checkOption.equals("2") && i!=(sellers.size() - 1)) {
                        i++;
                        break;
                    } else if (checkOption.equals("3")) {
                        sellerLogin.sellerControl(sellers.get(i));
                        break;
                    } else if (checkOption.equals("4")) {
                        ArrayList<String> blockList=storage.getAdminStorage().getBlockHotel();
                        if(isBlock){
                            blockList.remove(sellers.get(i).getHotel().getHotelId());
                            System.out.println("Successfully Removed from Block LIst");
                        }else {
                            if(blockList==null ){
                                ArrayList<String> newBlockList=new ArrayList<>();
                                newBlockList.add(sellers.get(i).getHotel().getHotelId());
                                blockList=newBlockList;
                            }else {
                                blockList.add(sellers.get(i).getHotel().getHotelId());
                            }
                            System.out.println("Successfully Added to Block LIst");
                        }
                        storage.getAdminStorage().setBlockHotel(blockList);
                        break;
                    } else if (checkOption.equals("5")) {
                        sellers.remove(sellers.get(i));
                    } else if (checkOption.equals("9")) {
                        return;
                    }else {
                        System.out.println("Enter a valid number");
                    }
                }
            }
        }else {
            System.out.println("There is no FoodSeller Available");
        }
    }
    void showSellerOption(int index,int size,boolean isBlock){
        if(index!=0){
            System.out.println("Enter \"1\" for Previous");
        }
        if(index!=(size-1)){
            System.out.println("ENter \"2\" for Next");
        }
        System.out.println("Enter \"3\" for View Seller Hotel");
        System.out.println("Enter \"4\" for "+(isBlock?"UnBlock Seller":"Block Seller"));
        System.out.println("Enter \"5\" for Remove Seller");
        System.out.println("Enter \"9\" for Go-Back");
    }
    void showNewHotelOption(int hasPrevious,int hasNext) {
        if(hasPrevious!=hasNext) {
            System.out.println("Enter \"1\" for Next");
        }
        if(hasPrevious!=0){
            System.out.println("Enter \"2\" for Previous");
        }
        System.out.println("Enter \"3\" for Accept New Hotel");
        System.out.println("Enter \"4\" for Reject New Hotel");
        System.out.println("Enter \"9\" for Back");
        System.out.println("Enter \"0\" for Exit");
        System.out.println();
    }
    void getAdminLoginOption(){
        System.out.println("Enter \"1\" for Add New Hotel");
        System.out.println("Enter \"2\" for Accept New Hotel");
        System.out.println("Enter \"3\" for Add Customer");
        System.out.println("Enter \"4\" for Add Admin");
        System.out.println("Enter \"5\" for Edit Profile");
        System.out.println("Enter \"6\" for View Food Seller");
        System.out.println("Enter \"9\" for Logout");
        System.out.println("Enter \"0\" for Exit");
    }
}
