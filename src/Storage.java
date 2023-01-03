import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Storage {
    private static  ArrayList<Customer> customers;
    private static ArrayList<Admin> admins;
    private static ArrayList<FoodSeller> sellers;
    private static AdminStorage adminStorage;
    void getFiles() throws IOException, ClassNotFoundException {
        File file=new File("Customer.txt");
        File file1=new File("Admin.txt");
        File file2=new File("Seller.txt");
        File file3=new File("AdminStorage.txt");

        if(file.length()!=0){
            FileInputStream fisCustomer=new FileInputStream("Customer.txt");
            ObjectInputStream oisCustomer=new ObjectInputStream(fisCustomer);
            customers=(ArrayList<Customer>) oisCustomer.readObject();
            oisCustomer.close();
        }
        if(file1.length()==0){
            Admin admin=new Admin("admin","123","admin123@gmail.com");
            ArrayList<Admin> newAdmin=new ArrayList<>();
            newAdmin.add(admin);
            admins=newAdmin;
        }else{
            FileInputStream fisAdmin=new FileInputStream("Admin.txt");
            ObjectInputStream oisAdmin=new ObjectInputStream(fisAdmin);
            admins=(ArrayList<Admin>) oisAdmin.readObject();
            oisAdmin.close();
        }
        if(file2.length()!=0){
            FileInputStream fisSeller=new FileInputStream("Seller.txt");
            ObjectInputStream oisSeller=new ObjectInputStream(fisSeller);
            sellers=(ArrayList<FoodSeller>) oisSeller.readObject();
            oisSeller.close();
        }
        if(file3.length()!=0){
            FileInputStream fisControl=new FileInputStream("AdminStorage.txt");
            ObjectInputStream oisControl=new ObjectInputStream(fisControl);
            adminStorage=(AdminStorage) oisControl.readObject();
            oisControl.close();
        }else {
            adminStorage=new AdminStorage(null,null,null,null);
        }

    }

    AdminStorage getAdminStorage(){
        return adminStorage;
    }
    ArrayList<FoodSeller> getSellers(){
        return sellers;
    }
    boolean checkAdminName(String name){
        for(Admin admin:admins){
            if(admin.getName().equals(name)){
                return false;
            }
        }
        return true;
    }
    boolean checkNewAdminEmailId(String emailId){
        for(Admin admin:admins){
            if(admin.getEmailId().equals(emailId)){
                return false;
            }
        }
        return true;
    }
    boolean checkSellerUserName(String userName){
        if(sellers==null){
            return true;
        }
        for(FoodSeller seller:sellers){
            if(seller.getUserName().equals(userName)){
                return false;
            }
        }
        return true;
    }
    String getHotelId(){
        if(sellers==null){
            return "HT1001";
        }
        String hotelId=sellers.get(sellers.size()-1).getHotel().getHotelId();
        String[] str=hotelId.split("HT");
        str[1]=String.valueOf(Integer.parseInt(str[1])+1);
        return "HT"+str[1];
    }
    FoodSeller checkSellerLogin(String userName,String password){
        if(sellers==null){
            return null;
        }
        for(FoodSeller seller:sellers){
            if(seller.getUserName().equals(userName) && seller.getPassword().equals(password)){
                return seller;
            }
        }
        return null;
    }
    boolean checkSellerBlockList(String hotelId){
        ArrayList<String> blockHotel=adminStorage.getBlockHotel();
        if(blockHotel==null){
            return false;
        }
        for(String id:blockHotel){
            if(id.equals(hotelId)){
                return true;
            }
        }
        return false;
    }
    boolean checkRemovedSeller(String username,String password){
        ArrayList<FoodSeller> removedSeller=adminStorage.getRemovedHotel();
        if(removedSeller==null){
            return false;
        }
        for(FoodSeller seller:removedSeller){
            if(seller.getUserName().equals(username) && seller.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    FoodSeller checkSellerEmailId(String emailId){
        ArrayList<FoodSeller> newHotel=adminStorage.getNewHotel();
        if(sellers==null){
            return null;
        }else {
            for (FoodSeller seller : sellers) {
                if (seller.getEmailId().equals(emailId)) {
                    return seller;
                }
            }
        }
        if(newHotel==null){
            return null;
        }else {
            for (FoodSeller newSeller : newHotel) {
                if (newSeller.getEmailId().equals(emailId)) {
                    return newSeller;
                }
            }
        }
        return null;
    }
    boolean checkNewSellerEmailId(String emailId){
        ArrayList<FoodSeller> removedSeller=adminStorage.getRemovedHotel();
        ArrayList<FoodSeller> newHotel=adminStorage.getNewHotel();
        if(sellers==null) {
            return false;
        }else {
            for (FoodSeller seller : sellers) {
                if (seller.getEmailId().equals(emailId)) {
                    return true;
                }
            }
        }
        if (newHotel==null) {
            return false;
        }else {
            for (FoodSeller newSeller : newHotel) {
                if (newSeller.getEmailId().equals(emailId)) {
                    return true;
                }
            }
        }
        if(removedSeller==null) {
            return false;
        }else {
            for (FoodSeller seller : removedSeller) {
                if (seller.getEmailId().equals(emailId)) {
                    return true;
                }
            }
        }
        return false;
    }
    Admin checkAdminLogin(String name, String password){
        for (Admin admin:admins) {
            if(admin.getName().equals(name) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null;
    }
    Admin checkAdminEmailId(String emailId){
        for (Admin admin:admins) {
            if(admin.getEmailId().equals(emailId)) {
                return admin;
            }
        }
        return null;
    }
    Hotel checkSellerAddress(Address address){
        if(sellers!=null) {
            for (FoodSeller seller : sellers) {
                Address sellersAddress = seller.getHotel().getHotelAddress();
                if (sellersAddress.getDNumber().equalsIgnoreCase(address.getDNumber()) &&
                        sellersAddress.getStreet().equalsIgnoreCase(address.getStreet()) &&
                        sellersAddress.getCity().equalsIgnoreCase(address.getCity()) &&
                        sellersAddress.getState().equalsIgnoreCase(address.getState())) {
                    return seller.getHotel();
                }
            }
        }
        return null;
    }
    void setAdmin(Admin admin){
        admins.add(admin);
    }
    void setSeller(FoodSeller seller){
        if(sellers==null){
            ArrayList<FoodSeller> addSeller=new ArrayList<>();
            addSeller.add(seller);
            sellers=addSeller;
        }else {
            sellers.add(seller);
        }
    }
    boolean checkCustomerUserName(String userName) {
        if(customers==null){
            return true;
        }else {
            for(Customer customer:customers){
                if(customer.getUserName().equals(userName)){
                    return false;
                }
            }
        }
        return true;
    }
    boolean checkNewCustomerEmailId(String emailId){
        if(customers==null){
            return true;
        }else {
            for(Customer customer:customers){
                if(customer.getEmailId().equals(emailId)){
                    return false;
                }
            }
        }
        return true;
    }
    void setCustomers(Customer customer){
        if(customers==null){
            ArrayList<Customer> newCustomer=new ArrayList<>();
            newCustomer.add(customer);
            customers=newCustomer;
        }else {
            customers.add(customer);
        }
    }
    Customer checkCustomerLogin(String userName,String password){
        if(customers!=null){
            for(Customer customer:customers){
                if(customer.getUserName().equals(userName) && customer.getPassword().equals(password)){
                    return customer;
                }
            }
        }
        return null;
    }
    Customer checkCustomerEmailId(String emailId){
        if(customers!=null){
            for(Customer customer:customers){
                if(customer.getEmailId().equals(emailId)){
                    return customer;
                }
            }
        }
        return null;
    }
    ArrayList<Hotel> getHotels(){
        LocalTime presentTime=LocalTime.now();
        LocalDate presentDate=LocalDate.now();
        DayOfWeek day=DayOfWeek.from(presentDate);
        String presentDay=String.valueOf(day);
        ArrayList<Hotel> hotels=new ArrayList<>();
        if(sellers!=null) {
            for (FoodSeller seller : sellers) {
                boolean isBlock = true;
                if(adminStorage.getBlockHotel()!=null) {
                    for (String hotelId : adminStorage.getBlockHotel()) {
                        if (seller.getHotel().getHotelId().equals(hotelId)) {
                            isBlock = false;
                            break;
                        }
                    }
                }
                if (isBlock) {
                    HashMap<Days, ArrayList<HotelTime>> availableDay = seller.getHotel().getAvailableDays();
                    for (Map.Entry<Days, ArrayList<HotelTime>> days : availableDay.entrySet()) {
                        boolean checkLeave = true;
                        if (seller.getHotel().getLeave() != null) {
                            for (LocalDate date : seller.getHotel().getLeave()) {
                                if (presentDate.isEqual(date)) {
                                    checkLeave = false;
                                }
                            }
                        }
                        if (days.getKey().toString().equals(presentDay) && checkLeave) {
                            ArrayList<HotelTime> hotelTimes = seller.getHotel().getAvailableDays().get(days.getKey());
                            for (HotelTime hotelTime : hotelTimes) {
                                if (hotelTime.getFrom().isBefore(presentTime) && hotelTime.getTo().isAfter(presentTime)) {
                                    hotels.add(seller.getHotel());
                                }
                            }
                        }
                    }
                }
            }
        }
        return hotels;
    }
    Hotel getHotel(String hotelId){
        for (FoodSeller seller:sellers) {
            boolean isBlock = true;
            if(adminStorage.getBlockHotel()!=null) {
                for (String blockId : adminStorage.getBlockHotel()) {
                    if (seller.getHotel().getHotelId().equals(blockId)) {
                        isBlock = false;
                        break;
                    }
                }
            }

            if(isBlock && seller.getHotel().getHotelId().equals(hotelId)){
                boolean isLeave=true;
                if(seller.getHotel().getLeave()!=null) {
                    for (LocalDate date : seller.getHotel().getLeave()) {
                        if (date.isEqual(LocalDate.now())) {
                            isLeave = false;
                            break;
                        }
                    }
                }
                if(isLeave) {
                    return seller.getHotel();
                } else {
                    return null;
                }
            }
        }
        return null;
    }
    String getOrderId(){
        ArrayList<Order> order=adminStorage.getOrder();
        if(order!=null){
            String id=order.get(order.size()-1).getOrderId();
            String[] str=id.split("ORDERID");
            str[1]=String.valueOf(Integer.parseInt(str[1])+1);
            return "ORDERID"+str[1];
        }else{
            return "ORDERID1001";
        }
    }
    void setFiles() throws IOException{

        FileOutputStream fosAdmin=new FileOutputStream("Admin.txt");
        ObjectOutputStream oosAdmin=new ObjectOutputStream(fosAdmin);
        oosAdmin.writeObject(admins);
        oosAdmin.flush();
        oosAdmin.close();

        FileOutputStream fosSeller=new FileOutputStream("Seller.txt");
        ObjectOutputStream oosSeller=new ObjectOutputStream(fosSeller);
        oosSeller.writeObject(sellers);
        oosSeller.flush();
        oosSeller.close();

        FileOutputStream fosCustomer=new FileOutputStream("Customer.txt");
        ObjectOutputStream oosCustomer=new ObjectOutputStream(fosCustomer);
        oosCustomer.writeObject(customers);
        oosCustomer.flush();
        oosCustomer.close();

        FileOutputStream fosControl=new FileOutputStream("AdminStorage.txt");
        ObjectOutputStream oosControl=new ObjectOutputStream(fosControl);
        oosControl.writeObject(adminStorage);
        oosControl.flush();
        oosControl.close();
    }


}
