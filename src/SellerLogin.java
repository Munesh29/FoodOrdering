import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class SellerLogin extends Login{
    Scanner scan=new Scanner(System.in);
    Storage storage=new Storage();
    int foodSellerLogin() {
        while(true) {
            System.out.println("Enter your User Name");
            String userName = scan.next();
            System.out.println("Enter your Password");
            String password = scan.next();
            FoodSeller seller;
            seller = storage.checkSellerLogin(userName,password);
            if(seller!=null){
                if(storage.checkSellerBlockList(seller.getHotel().getHotelId())){
                    System.out.println("You are blocked by the admin");
                    return 9;
                }
                if(sellerControl(seller)){
                    return 0;
                }else {
                    return 9;
                }
            } else if (storage.checkRemovedSeller(userName,password)) {
                System.out.println("You were removed/rejected by the admin");
                return 9;
            } else {
                checkEmailId:
                while (true) {
                    getLoginErrorOption();
                    String checkSellerOption = scan.next();
                    switch (checkSellerOption){
                        case "1":
                            break checkEmailId;
                        case "2":
                            while (true){
                                System.out.println("Enter your EmailId");
                                String emailId=scan.next();
                                seller=storage.checkSellerEmailId(emailId);
                                if(seller!=null){
                                    if (!(seller.getUserName().equals(userName))) {
                                        System.out.println("\nUserName : " + seller.getUserName());
                                    }
                                    if (!(seller.getPassword().equals(password))) {
                                        System.out.println("Password : " + seller.getPassword());
                                    }
                                    System.out.println();
                                    break checkEmailId;
                                }else {
                                    emailIdMismatch();
                                    String checkEmailOption ;
                                    checkEmail:
                                    while (true) {
                                        checkEmailOption = scan.next();
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
                    }
                }
            }
        }
    }
    void createFoodSellerAccount() {
        System.out.println("Enter your Name");
        String name;
        do {
            name = scan.nextLine();
        } while (name.length() == 0);
        String userName;
        while(true) {
            System.out.println("Enter your UserName");
            userName = scan.next();
            if(storage.checkSellerUserName(userName)){
                break;
            }
            System.out.println("Entered User Name is already exits");
            System.out.println();
        }
        System.out.println("Enter your Password");
        String password=scan.next();
        String emailId;
        while(true) {
            System.out.println("Enter your emailId");
            emailId = scan.next();
            if(checkEmailIdFormat(emailId)) {
                if(storage.checkNewSellerEmailId(emailId)){
                    System.out.println("Entered Email Id is already Exist");
                    System.out.println("Please enter another EmailId");
                    continue;
                }
                break;
            }
            System.out.println("Entered EmailId is Invalid");
        }
        scan.nextLine();
        System.out.println("Enter a Hotel Name");
        String hotelName = scan.nextLine();
        String hotelId=storage.getHotelId();
        System.out.println("Available Hotel types");
        Type hotelType=assignHotelType();
        System.out.println("\nAvailable Days");
        HashMap<Days, ArrayList<HotelTime>> availableDays=getHotelAvailableDays();
        System.out.println("Enter a Address");
        Address address;
        while(true) {
            address = setAddress();
            Hotel hotel = storage.checkSellerAddress(address);
            if (hotel != null) {
                System.out.println("Entered Address is already Exit for a " + hotel.getHotelName());
                continue;
            }
            break;
        }
        ArrayList<String> contactNumber=assignContactNumber();

        Hotel hotel=new Hotel(hotelName,hotelId,hotelType,availableDays,address,contactNumber,null,null,null);
        FoodSeller seller=new FoodSeller(name,userName,password,emailId,hotel);
        ArrayList<FoodSeller> newHotel=storage.getAdminStorage().getNewHotel();
        if(newHotel!=null){
            newHotel.add(seller);
            storage.getAdminStorage().setNewHotel(newHotel);
        }else {
            ArrayList<FoodSeller> addHotel=new ArrayList<>();
            addHotel.add(seller);
            storage.getAdminStorage().setNewHotel(addHotel);
        }
    }
    boolean sellerControl(FoodSeller seller) {
        while (true){
            showAvailableDays(seller.getHotel().getAvailableDays());
            showSellerOption();
            String checkOption= scan.next();
            switch (checkOption){
                case "1":
                    while (true) {
                        showSellerDetails(seller);
                        System.out.println("Enter \"1\" for Edit Profile");
                        System.out.println("Enter \"9\" for Go-Back");
                        String checkSellerOption= scan.next();
                        if(checkSellerOption.equals("1")){
                            editSellerDetails(seller);
                        } else if (checkSellerOption.equals("9")) {
                            break;
                        }else {
                            System.out.println("Enter a valid Number");
                        }
                    }
                    break;
                case "2":
                    if(seller.getHotel().getItems()==null){
                        System.out.println("There are no items Available in Hotel");
                    }else {
                        if(viewSellerItems(seller.getHotel()))
                        {
                            return true;
                        }else {
                            break;
                        }
                    }
                    break;
                case "3":
                    addItems(seller);
                    break;
                case "4":
                    if(seller.getHotel().getHideItems()==null){
                        System.out.println("Hide Items List is Empty ");
                    }else {
                        assignHideItems(seller);
                    }
                    break;
                case "5":
                    assignLeave(seller.getHotel());
                    break;
                case "9":
                    return false;
                case "0":
                    return true;
                default:
                    System.out.println("Enter a valid Number");
                    break;
            }
        }
    }
    void showSellerDetails(FoodSeller seller){
        System.out.println("Seller Name\t\t\t: " + seller.getName());
        System.out.println("Seller UserName\t\t: "+ seller.getUserName());
        System.out.println("Seller EmailId\t\t: " + seller.getEmailId());
        System.out.println("Hotel Name\t\t\t: " + seller.getHotel().getHotelName());
        System.out.println("Hotel Type\t\t\t: " + seller.getHotel().getHotelType());
        System.out.println();
        showAvailableDays(seller.getHotel().getAvailableDays());
        System.out.print("Hotel Address\t\t: ");
        System.out.println(seller.getHotel().getHotelAddress().getDNumber()+",");
        System.out.println("\t\t\t\t\t "+seller.getHotel().getHotelAddress().getStreet()+",");
        System.out.println("\t\t\t\t\t "+seller.getHotel().getHotelAddress().getCity()+"-"+
                seller.getHotel().getHotelAddress().getPinCode()+",");
        System.out.println("\t\t\t\t\t "+seller.getHotel().getHotelAddress().getState());
        System.out.println("Hotel Contact Number: " + seller.getHotel().getNumbers());
    }
    void showAvailableDays(HashMap<Days,ArrayList<HotelTime>> availableDays ) {
        System.out.print("Available Day");
        for(PartOfDay part: PartOfDay.values()){
            System.out.print("\t\t"+part+"\t\t");
        }
        System.out.println();
        for (Days day:Days.values()) {
            if(availableDays.containsKey(day)){
                System.out.print(day);
                ArrayList<HotelTime> hotelTimes=availableDays.get(day);
                for(PartOfDay part: PartOfDay.values()){
                    int flag=0;
                    boolean checkDay = (day.equals(Days.WEDNESDAY) || day.equals(Days.THURSDAY) || day.equals(Days.SATURDAY)) && part.equals(PartOfDay.MORNING);
                    boolean checkPartOfDay = part.equals(PartOfDay.MORNING) || part.equals((PartOfDay.EVENING));
                    for (HotelTime partOfTime:hotelTimes) {
                        if(partOfTime.getDay().equals(part)){
                            if(checkDay){
                                System.out.print("\t\t\t"+partOfTime.getFrom()+"-"+partOfTime.getTo());
                            }else if(checkPartOfDay){
                                System.out.print("\t\t\t\t"+partOfTime.getFrom()+"-"+partOfTime.getTo());
                            }else {
                                System.out.print("\t\t\t"+partOfTime.getFrom()+"-"+partOfTime.getTo());
                            }
                            flag=1;
                        }
                    }
                    if(flag==0){
                        if(checkDay){
                            System.out.print("\t\t\t-\t\t");
                        }else if(checkPartOfDay){
                            System.out.print("\t\t\t\t-\t\t");
                        }else {
                            System.out.print("\t\t\t-\t\t");
                        }

                    }
                }
                System.out.println("\n");
            }
        }
    }
    void editSellerDetails(FoodSeller seller) {
        System.out.println("Seller Name : "+seller.getName());
        showEditOption();
        editName:
        while(true){
            String checkOption= scan.next();
            switch (checkOption) {
                case "1":
                    System.out.println("Enter a Name");
                    scan.nextLine();
                    String name = scan.nextLine();
                    seller.setName(name);
                    break editName;
                case "2":
                    break editName;
                case "9":
                    return;
                default:
                    System.out.println("Enter a valid number");
                    break;
            }
        }
        System.out.println("Seller UserName : "+seller.getUserName());
        showEditOption();
        editUserName:
        while (true){
            String checkOption= scan.next();
            switch (checkOption) {
                case "1":
                    String userName;
                    while(true) {
                        System.out.println("Enter your UserName");
                        userName = scan.next();
                        if(storage.checkSellerUserName(userName) || seller.getUserName().equals(userName)){
                            break;
                        }
                        System.out.println("Entered User Name is already exits");
                        System.out.println();
                    }
                    seller.setUserName(userName);
                    break editUserName;
                case "2":
                    break editUserName;
                case "9":
                    return ;
                default:
                    System.out.println("Enter a valid number");
                    break;
            }
        }
        System.out.println("Password : "+seller.getPassword() );
        showEditOption();
        editPassword:
        while (true){
            String checkOption= scan.next();
            switch (checkOption) {
                case "1":
                    System.out.println("Enter a Password");
                    String password= scan.next();
                    seller.setPassword(password);
                    break editPassword;
                case "2":
                    break editPassword;
                case "9":
                    return ;
                default:
                    System.out.println("Enter a valid number");
                    break;
            }
        }
        System.out.println("Email Id : "+seller.getEmailId());
        showEditOption();
        editEmailId:
        while (true){
            String checkOption= scan.next();
            switch (checkOption) {
                case "1":
                    String emailId;
                    while(true) {
                        System.out.println("Enter your emailId");
                        emailId = scan.next();
                        if(checkEmailIdFormat(emailId)) {
                            if(storage.checkNewSellerEmailId(emailId) && !(seller.getEmailId().equals(emailId))){
                                System.out.println("Entered Email Id is already Exist");
                                System.out.println("Please enter another EmailId");
                                continue;
                            }
                            break;
                        }
                        System.out.println("Entered EmailId is Invalid");
                    }
                    seller.setEmailId(emailId);
                    break editEmailId;
                case "2":
                    break editEmailId;
                case "9":
                    return ;
                default:
                    System.out.println("Enter a valid number");
                    break;
            }
        }
        System.out.println("Hotel Name : "+seller.getHotel().getHotelName());
        showEditOption();
        editHotelName:
        while (true){
            String checkOption= scan.next();
            switch (checkOption) {
                case "1":
                    System.out.println("Enter a Hotel Name");
                    scan.nextLine();
                    String hotelName= scan.nextLine();
                    seller.getHotel().setHotelName(hotelName);
                    break editHotelName;
                case "2":
                    break editHotelName;
                case "9":
                    return ;
                default:
                    System.out.println("Enter a valid number");
                    break;
            }
        }
        System.out.println("Hotel Type : "+seller.getHotel().getHotelType());
        showEditOption();
        editHotelType:
        while(true){
            String checkOption= scan.next();
            switch (checkOption) {
                case "1":
                    Type hotelType= assignHotelType();
                    seller.getHotel().setHotelType(hotelType);
                    break editHotelType;
                case "2":
                    break editHotelType;
                case "9":
                    return ;
                default:
                    System.out.println("Enter a valid number");
                    break;
            }
        }
        System.out.print("Hotel Address : ");
        System.out.println(seller.getHotel().getHotelAddress().getDNumber()+",");
        System.out.println("\t\t\t "+seller.getHotel().getHotelAddress().getStreet()+",");
        System.out.println("\t\t\t "+seller.getHotel().getHotelAddress().getCity()+"-"+
                seller.getHotel().getHotelAddress().getPinCode()+",");
        System.out.println("\t\t\t "+seller.getHotel().getHotelAddress().getState());
        showEditOption();
        editAddress:
        while (true){
            String checkOption= scan.next();
            switch (checkOption) {
                case "1":
                    seller.getHotel().setHotelAddress(setAddress());
                    break editAddress;
                case "2":
                    break editAddress;
                case "9":
                    return ;
                default:
                    System.out.println("Enter a valid number");
                    break;
            }
        }
        System.out.println("Contact Number : "+seller.getHotel().getNumbers());
        System.out.println("Enter \"1\" for Add Contact Number");
        System.out.println("Enter \"2\" for Edit Contact");
        System.out.println("Enter \"3\" for Next");
        System.out.println("Enter \"9\" for Go-Back");
        editContactNumber:
        while (true){
            ArrayList<String> contactNumbers=seller.getHotel().getNumbers();
            String checkOption= scan.next();
            switch (checkOption) {
                case "1":
                    ArrayList<String> contactNumber=assignContactNumber();
                    contactNumbers.addAll(contactNumber);
                    seller.getHotel().setContactNumbers(contactNumbers);
                    break editContactNumber;
                case "2":
                    for (int i=0;i<contactNumbers.size();i++){
                        System.out.println("Contact Number : "+contactNumbers.get(i));
                        showEditOption();
                        number:
                        while(true){
                            String checkNumberOption= scan.next();
                            switch (checkNumberOption) {
                                case "1":
                                    while (true) {
                                        System.out.println("Enter a Contact Number");
                                        String number = scan.next();
                                        if (checkContactNumberFormat(number)) {
                                            contactNumbers.set(i, number);
                                            break;
                                        } else {
                                            System.out.println("Entered Contact number is Invalid");
                                        }
                                    }
                                    break number;
                                case "2":
                                    break number;
                                case "9":
                                    break editContactNumber;
                                default:
                                    System.out.println("Enter a valid number");
                                    break;
                            }
                        }
                    }
                    seller.getHotel().setContactNumbers(contactNumbers);
                    break editContactNumber;
                case "3":
                    break editContactNumber;
                case "9":
                    return ;
                default:
                    System.out.println("Enter a valid number");
                    break;
            }
        }
        showAvailableDays(seller.getHotel().getAvailableDays());
        showEditOption();
        editAvailableDays:
        while (true){
            String checkOption= scan.next();
            switch (checkOption) {
                case "1":
                    seller.getHotel().setAvailableDays(editAvailableDays(seller.getHotel().getAvailableDays()));
                    break editAvailableDays;
                case "2":
                    break editAvailableDays;
                case "9":
                    return ;
                default:
                    System.out.println("Enter a valid number");
                    break;
            }
        }
    }
    HashMap<Days, ArrayList<HotelTime>> editAvailableDays(HashMap<Days, ArrayList<HotelTime>> availableDays) {
        for(Days day:Days.values()){
            if(availableDays.containsKey(day)){
                System.out.println("Enter \"1\" for Edit "+day);
                System.out.println("Enter \"2\" for Next");
                System.out.println("Enter \"9\" for Go-Back");
                editDay:
                while(true) {
                    String checkDayOption = scan.next();
                    switch (checkDayOption) {
                        case "1":
                            ArrayList<HotelTime> availableTime = availableDays.get(day);
                            for (PartOfDay part : PartOfDay.values()) {
                                int checkTiming=-1;
                                for (int i=0;i<availableTime.size();i++) {
                                    if(availableTime.get(i).getDay().equals(part)) {
                                        checkTiming = i;
                                    }
                                }
                                System.out.println("Enter \"1\" for " + (checkTiming>=0 ? "edit " : "add ") + day + " " + part);
                                System.out.println("Enter \"2\" for next");
                                System.out.println("Enter \"9\" for Go-Back");
                                editTiming:
                                while (true) {
                                    String checkTimeOption = scan.next();
                                    switch (checkTimeOption) {
                                        case "1":
                                            HotelTime hotelTime = setTiming(part);
                                            if (checkTiming>=0) {
                                                availableTime.set(checkTiming, hotelTime);
                                            } else {

                                                availableTime.add(0, hotelTime);
                                            }
                                            break editTiming;
                                        case "2":
                                            break editTiming;
                                        case "9":
                                            break editDay;
                                        default:
                                            System.out.println("Enter a valid number");
                                            break;
                                    }
                                }
                            }
                            //availableDays.put(day,availableTime);
                            break editDay;
                        case "2":
                            break editDay;
                        case "9":
                            return availableDays;
                        default:
                            System.out.println("Enter a valid number");
                            break;
                    }
                }
            }
            else{
                System.out.println("Enter \"1\" for add "+day );
                System.out.println("Enter \"2\" for next");
                System.out.println("Enter \"9\" for Go-Back");
                addDay:
                while (true){
                    String checkAddDay=scan.next();
                    switch (checkAddDay){
                        case "1":
                            ArrayList<HotelTime> availableTime=getHotelAvailableTiming();
                            availableDays.put(day,availableTime);
                            break addDay;
                        case "2":
                            break addDay;
                        case "3":
                            return availableDays;
                        default:
                            System.out.println("Enter a valid number");
                    }
                }
            }
        }
        return availableDays;
    }
    HotelTime setTiming(PartOfDay part){
        String time;
        LocalTime setToTime ;
        LocalTime setFromTime;
        String from=part.getFrom();
        String to=part.getTo();
        LocalTime fromTime=LocalTime.parse(from);
        LocalTime toTime=LocalTime.parse(to);
        setTime:
        while (true) {
            while (true) {
                System.out.println("Enter a timing between " + fromTime + " to " + toTime);
                time = scan.next();
                try {
                    setFromTime = LocalTime.parse(time);
                    break;
                } catch (Exception e) {
                    System.out.println("Enter a valid Format HH:mm:ss or HH:mm");
                }
            }
            if ((setFromTime.isAfter(fromTime) || setFromTime.equals(fromTime)) && (setFromTime.isBefore(toTime) || setFromTime.equals(toTime))) {
                while (true) {
                    while (true) {
                        System.out.println("Enter a timing between " + setFromTime + " to " + toTime);
                        time = scan.next();
                        try {
                            setToTime = LocalTime.parse(time);
                            break;
                        } catch (Exception e) {
                            System.out.println("Enter a valid Format HH:mm:ss or HH:mm");
                        }
                    }
                    if (setToTime.isAfter(setFromTime) && (setToTime.isBefore(toTime) || setToTime.equals(toTime))) {
                        break setTime;
                    } else {
                        System.out.println("Enter a valid Time");
                    }
                }
            } else {
                System.out.println("Enter a valid Time");
            }
        }
        HotelTime hotelTime = new HotelTime(part, setFromTime, setToTime);
        return hotelTime;
    }
    ArrayList<HotelTime> getHotelAvailableTiming() {
        ArrayList<HotelTime> availableTime=new ArrayList<>();
        System.out.println("Enter a Time of day");
        time:
        while(true) {
            int count = 1;
            for (PartOfDay time : PartOfDay.values()) {
                if(checkContainTime(time,availableTime)) {
                    System.out.println("Enter \"" + count + "\" for " + time);
                }
                count++;
            }
            while(true) {
                String partOfTime = scan.next();
                if (partOfTime.equals("1") && checkContainTime(PartOfDay.MORNING, availableTime)) {
                    availableTime.add(setTiming(PartOfDay.MORNING));
                    break;
                } else if (partOfTime.equals("2") && checkContainTime(PartOfDay.AFTERNOON, availableTime)) {
                    availableTime.add(setTiming(PartOfDay.AFTERNOON));
                    break;
                } else if (partOfTime.equals("3") && checkContainTime(PartOfDay.EVENING, availableTime)) {
                    availableTime.add(setTiming(PartOfDay.EVENING));
                    break;
                } else if (partOfTime.equals("4") && checkContainTime(PartOfDay.NIGHT, availableTime)) {
                    availableTime.add(setTiming(PartOfDay.NIGHT));
                    break;
                } else {
                    System.out.println("Enter a valid Number");
                }
            }
            if(availableTime.size()==4){
                break;
            }
            while (true) {
                System.out.println("Enter \"1\" for Skip");
                System.out.println("Enter \"2\" for Another Timing");
                String checkOption = scan.next();
                if (checkOption.equals("1")) {
                    break time;
                } else if (checkOption.equals("2")) {
                    continue time;
                } else {
                    System.out.println("Enter a valid Number");
                }
            }
        }
        return availableTime;
    }
    boolean checkContainTime(PartOfDay time, ArrayList<HotelTime> availableTime) {
        for (HotelTime partOfTime:availableTime) {
            if(partOfTime.getDay().equals(time)){
                return false;
            }
        }
        return true;
    }
    boolean viewSellerItems(Hotel hotel) {
        HashMap<String, ArrayList<Item>> items = hotel.getItems();
        String itemType;
        while(true) {
            int itemCount = 1;
            ArrayList<String> itemTypes=new ArrayList<>();
            for (Map.Entry<String, ArrayList<Item>> item : items.entrySet()) {
                System.out.println("Enter \"" + itemCount++ + "\" for " + item.getKey());
                itemTypes.add(item.getKey());
            }
            System.out.println("Enter \"" + itemCount + "\" for Go-Back");

            viewType:
            while (true) {
                int checkItemOption;
                while (true) {
                    try {
                        scan.nextLine();
                        checkItemOption = scan.nextInt();
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a valid Number");
                    }
                }
                if (checkItemOption > 0 && checkItemOption <= itemTypes.size()) {
                    ArrayList<String> hideItems = hotel.getHideItems();
                    itemType = itemTypes.get(checkItemOption - 1);
                    ArrayList<Item> availableItem = items.get(itemType);
                    boolean checkView=true;
                    items:
                    for (int i = 0; i < availableItem.size(); ) {
                        if (hideItems != null) {
                            for (String hideItem : hideItems) {
                                if (availableItem.get(i).getItemId().equals(hideItem)) {
                                    i++;
                                    continue items;
                                }
                            }
                        }
                        checkView=false;
                        showItem(availableItem.get(i));
                        showAvailableDays(availableItem.get(i).getItemAvailableDays());
                        while (true) {
                            showSellerItemOption(i, availableItem.size());
                            String checkSellerOption = scan.next();

                            if(checkSellerOption.equals("1") && i!=0) {
                                i--;
                                break ;
                            } else if (checkSellerOption.equals("2") && i!=(availableItem.size()-1)) {
                                i++;
                                break ;
                            } else if (checkSellerOption.equals("3")) {
                                editItem(availableItem.get(i),hotel.getAvailableDays());
                                break ;
                            } else if (checkSellerOption.equals("4")) {
                                if (hotel.getHideItems() == null) {
                                    ArrayList<String> hideItem = new ArrayList<>();
                                    hideItem.add(availableItem.get(i).getItemId());
                                    hideItems = hideItem;
                                } else {
                                    hideItems.add(availableItem.get(i).getItemId());
                                }
                                if(i!=0){
                                    i--;
                                } else if (i!=(availableItem.size()-1)) {
                                    i++;
                                }
                                hotel.setHideItems(hideItems);
                                break ;
                            } else if (checkSellerOption.equals("5")) {
                                if(i!=0){
                                    i--;
                                } else if (i!=(availableItem.size()-1)) {
                                    i++;
                                }
                                availableItem.remove(availableItem.get(i));
                            } else if (checkSellerOption.equals("9")) {
                                break viewType;
                            } else if (checkSellerOption.equals("0")) {
                                return false;
                            }else {
                                System.out.println("Enter a valid Number");
                            }

                        }
                    }
                    if(checkView){
                        System.out.println(itemType + " list is empty");
                    }
                    break;
                } else if (checkItemOption == itemCount) {
                    System.out.println("return");
                    return false;
                } else {
                    System.out.println("Enter a valid Number");
                }
            }

        }
    }
    void showItem(Item item) {
        System.out.println("------------------------------------------------");
        System.out.println(item.getItemName()+"\t*"+item.getItemType());
        System.out.println("\tPrice : "+item.getPrice()+"\t * "+item.getItemKind());
        System.out.println("Ingredients : "+item.getIngredients());
        System.out.println("Description : "+item.getDescription());
        System.out.println("------------------------------------------------");
    }
    void showSellerItemOption(int i, int size) {
        if(i!=0){
            System.out.println("Enter \"1\" for Previous");
        }
        if(i!=(size-1)){
            System.out.println("Enter \"2\" for Next");
        }
        System.out.println("Enter \"3\" for Edit");
        System.out.println("Enter \"4\" for Add Hide Items");
        System.out.println("Enter \"5\" for Remove Item");
        System.out.println("Enter \"9\" for Go-Back");
        System.out.println("Enter \"0\" for Exit");
    }
    void editItem(Item item,HashMap<Days,ArrayList<HotelTime>> hotelAvailableDays) {
        System.out.println("Item Name : "+item.getItemName());
        showEditOption();
        editItemName:
        while (true){
            String checkEditOption=scan.next();
            switch (checkEditOption){
                case "1":
                    System.out.println("Enter a Item Name");
                    String itemName= scan.nextLine();
                    item.setItemName(itemName);
                    break editItemName;
                case "2":
                    break editItemName;
                case "3":
                    return;
                default:
                    System.out.println("Enter a valid number");
            }
        }
        System.out.println("Price : "+item.getPrice());
        showEditOption();
        editPrice:
        while (true){
            String checkEditOption=scan.next();
            switch (checkEditOption){
                case "1":
                    double price;
                    while (true) {
                        try {
                            System.out.println("Enter a Item price");
                            price = scan.nextDouble();
                            break;
                        } catch (Exception e) {
                            System.out.println("Enter a valid Number");
                            scan.nextLine();
                        }
                    }
                    item.setPrice(price);
                    break editPrice;
                case "2":
                    break editPrice;
                case "3":
                    return;
                default:
                    System.out.println("Enter a valid number");
            }
        }
        System.out.println("Ingredients : "+item.getIngredients());
        System.out.println("Enter \"1\" for Add Ingredients");
        System.out.println("Enter \"2\" for Edit");
        System.out.println("Enter \"3\" for Next");
        System.out.println("Enter \"4\" for Go-Back");
        ArrayList<String > ingredients=item.getIngredients();
        editIngredients:
        while(true){
            String checkEditOption=scan.next();
            switch (checkEditOption){
                case "1":
                    addIngredient:
                    while(true) {
                        String ingredient = scan.nextLine();
                        ingredients.add(ingredient);
                        while (true) {
                            System.out.println("Enter \"1\" for Add More Ingredients");
                            System.out.println("Enter \"2\" for Stop Adding");
                            String checkOption = scan.next();
                            if (checkOption.equals("1")) {
                                break;
                            } else if (checkOption.equals("2")) {
                                break addIngredient;
                            } else {
                                System.out.println("Enter a valid number");
                            }
                        }
                    }
                    item.setIngredients(ingredients);
                    break editIngredients;
                case "2":
                    for(int i=0;i<ingredients.size();i++){
                        System.out.println("Ingredient : "+ingredients.get(i));
                        showEditOption();
                        editIngredient:
                        while (true){
                            String checkIngredientEditOption=scan.next();
                            switch (checkIngredientEditOption){
                                case "1":
                                    System.out.println("Enter a Item Name");
                                    String ingredient= scan.nextLine();
                                    ingredients.set(i,ingredient);
                                    break editIngredient;
                                case "2":
                                    break editIngredient;
                                case "3":
                                    break editIngredients;
                                default:
                                    System.out.println("Enter a valid number");
                            }
                        }

                    }
                    item.setIngredients(ingredients);
                    break editIngredients;
                case "3":
                    break editIngredients;
                case "4":
                    return;
                default:
                    System.out.println("Enter a valid number");
            }
        }
        System.out.println("Description : "+item.getDescription());
        showEditOption();
        editDescription:
        while (true){
            String checkEditOption=scan.next();
            switch (checkEditOption){
                case "1":
                    System.out.println("Enter a Description");
                    String description= scan.nextLine();
                    item.setDescription(description);
                    break editDescription;
                case "2":
                    break editDescription;
                case "3":
                    return;
                default:
                    System.out.println("Enter a valid number");
            }
        }
        showAvailableDays(item.getItemAvailableDays());
        showEditOption();
        editAvailableDays:
        while (true){
            String checkEditOption=scan.next();
            switch (checkEditOption){
                case "1":
                    item.setItemAvailableDays(assignItemAvailableDays(hotelAvailableDays));
                    break editAvailableDays;
                case "2":
                    break editAvailableDays;
                case "3":
                    return;
                default:
                    System.out.println("Enter a valid number");
            }
        }
    }
    void addItems(FoodSeller seller) {
        HashMap<String, ArrayList<Item>> items = seller.getHotel().getItems();
        AddItemType:
        while(true) {

            HashMap<Days, ArrayList<HotelTime>> availableDays = seller.getHotel().getAvailableDays();
            HashMap<Days, ArrayList<HotelTime>> itemAvailableDays ;
            ArrayList<String> itemTypes = new ArrayList<>();
            ArrayList<Item> itemList;
            int itemCount = 1;
            String itemType;
            String itemName;
            while (true) {
                if (items != null) {
                    for (Map.Entry<String, ArrayList<Item>> item : items.entrySet()) {
                        System.out.println("Enter \"" + itemCount++ + "\" for " + item.getKey());
                        itemTypes.add(item.getKey());
                    }
                }
                System.out.println("Enter \"" + itemCount + "\" for New Items");
                int checkItemOption;
                while (true) {
                    try {
                        scan.nextLine();
                        checkItemOption = scan.nextInt();
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a valid Number");
                    }
                }
                if (checkItemOption == itemCount) {
                    System.out.println("Enter your Item type");
                    scan.nextLine();
                    itemType = scan.nextLine();
                    break;
                } else if (checkItemOption <= itemTypes.size() && checkItemOption > 0) {
                    itemType = itemTypes.get(checkItemOption - 1);
                    scan.nextLine();
                    break;
                } else {
                    System.out.println("Enter a valid Number");
                    itemCount=1;
                }
            }
            addItem:
            while (true) {
                System.out.println("Enter a Item Name");
                itemName = scan.nextLine();
                double price;
                while (true) {
                    try {
                        System.out.println("Enter a Item price");
                        price = scan.nextDouble();
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a valid Number");
                        scan.nextLine();
                    }
                }
                Type itemKind;
                if(seller.getHotel().getHotelType().equals(Type.VEG)){
                    itemKind=Type.VEG;
                }else {
                    while(true) {
                        System.out.println("Enter \"1\" for " + Type.VEG);
                        System.out.println("Enter \"2\" for " + Type.NON_VEG);
                        String checkOption= scan.next();
                        if(checkOption.equals("1")){
                            itemKind=Type.VEG;
                            break ;
                        } else if (checkOption.equals("2")) {
                            itemKind=Type.NON_VEG;
                            break ;
                        }else {
                            System.out.println("Enter a valid Number");
                        }
                    }
                }
                String itemId=assignItemId(itemType,items);
                ArrayList<String> ingredients = new ArrayList<>();
                addIngredients:
                while (true) {
                    System.out.println("Enter a Ingredients Name");
                    scan.nextLine();
                    String ingredient = scan.nextLine();
                    ingredients.add(ingredient);
                    while (true) {
                        System.out.println("Enter \"1\" for Add More Ingredients");
                        System.out.println("Enter \"2\" for Stop Adding");
                        String checkOption = scan.next();
                        if (checkOption.equals("1")) {
                            break;
                        } else if (checkOption.equals("2")) {
                            break addIngredients;
                        } else {
                            System.out.println("Enter a valid number");
                        }
                    }
                }
                System.out.println("Enter a Item Description");
                scan.nextLine();
                String description = scan.nextLine();
                System.out.println("Enter a Item Available Days");
                itemAvailableDays=assignItemAvailableDays(availableDays);

                Item item = new Item(itemName, itemType, itemKind,itemId, price, ingredients, description, itemAvailableDays);
                if(items==null || !(items.containsKey(itemType)) || items.get(itemType)==null || items.get(itemType).isEmpty()){
                    itemList=new ArrayList<>();
                }else {
                    itemList=items.get(itemType);
                }
                itemList.add(item);
                if(items==null){
                    HashMap<String,ArrayList<Item>> newItem=new HashMap<>();
                    newItem.put(itemType,itemList);
                    items=newItem;
                }else {
                    items.put(itemType,itemList);
                }
                while (true) {
                    System.out.println("Enter \"1\" for Add another Item in " + itemType);
                    System.out.println("Enter \"2\" for Another Type");
                    System.out.println("Enter \"9\" for Go-Back");
                    String checkOption = scan.next();
                    switch (checkOption) {
                        case "1":
                            scan.nextLine();
                            continue addItem;
                        case "2":
                            break addItem;
                        case "9":
                            seller.getHotel().setItems(items);
                            break AddItemType;
                        default:
                            System.out.println("Enter a valid Number");
                            break;
                    }
                }
            }
        }
        seller.getHotel().setItems(items);
    }
    String assignItemId(String itemType, HashMap<String, ArrayList<Item>> items) {
        if(items!=null) {
            boolean checkType = false;
            for (Map.Entry<String, ArrayList<Item>> item : items.entrySet()) {
                if (item.getKey().equals(itemType)) {
                    checkType = true;
                    break;
                }
            }
            if(checkType){
                if(items.get(itemType)!=null && !(items.get(itemType).isEmpty())){
                    ArrayList<Item> item=items.get(itemType);
                    String itemId=item.get(item.size()-1).getItemId();
                    String[] str=itemId.split((itemType+"ID"));
                    System.out.println(str[0]+" ");
                    str[1]=String.valueOf(Integer.parseInt(str[1])+1);
                    return itemType+"ID"+str[1];
                }
                else{
                    return itemType+"ID101";
                }
            }else {
                return itemType+"ID101";
            }
        }else{
            return itemType+"ID101";
        }
    }
    HashMap<Days, ArrayList<HotelTime>> assignItemAvailableDays(HashMap<Days, ArrayList<HotelTime>> availableDays) {
        HashMap<Days, ArrayList<HotelTime>> itemAvailableDays = new HashMap<>();
        while(true) {
            checkDay:
            for (Days day : Days.values()) {
                if (availableDays.containsKey(day)) {
                    while (true) {
                        System.out.println("Enter \"1\" for Add " + day);
                        System.out.println("Enter \"2\" for Next Day");
                        System.out.println("Enter \"3\" for Exit");
                        String checkDays = scan.next();
                        if (checkDays.equals("1")) {
                            ArrayList<HotelTime> availableTiming = availableDays.get(day);
                            ArrayList<HotelTime> itemsAvailableTiming = new ArrayList<>();
                            checkPartOfDay:
                            for (PartOfDay part : PartOfDay.values()) {
                                for (HotelTime availableTime : availableTiming) {
                                    if (availableTime.getDay().equals(part)) {
                                        checkTimeOption:
                                        while (true) {
                                            System.out.println("Enter \"1\" for Add Item for " + day + " " + part);
                                            System.out.println("Enter \"2\" for Next");
                                            System.out.println("Enter \"9\" for Back");
                                            System.out.println("Enter \"0\" for Exit Adding Days");
                                            String checkTime = scan.next();
                                            switch (checkTime) {
                                                case "1":
                                                    HotelTime hotelTime;
                                                    hotelTime=new HotelTime(availableTime.getDay(),availableTime.getFrom(),availableTime.getTo());
                                                    itemsAvailableTiming.add(hotelTime);
                                                    break checkTimeOption;
                                                case "2":
                                                    break checkTimeOption;
                                                case "9":
                                                    itemAvailableDays.put(day, itemsAvailableTiming);
                                                    break checkPartOfDay;
                                                case "0":
                                                    itemAvailableDays.put(day, itemsAvailableTiming);
                                                    break checkDay;
                                                default:
                                                    System.out.println("Enter a valid Number");
                                            }
                                        }
                                    }
                                }
                            }

                            itemAvailableDays.put(day, itemsAvailableTiming);
                            break;



                        } else if (checkDays.equals("2")) {
                            break;
                        } else if (checkDays.equals("3")) {
                            if (itemAvailableDays.isEmpty()) {
                                System.out.println("Select at least one available day for an Item");
                                continue checkDay;
                            } else {
                                break checkDay;
                            }
                        } else {
                            System.out.println("Enter a valid number");
                        }

                    }
                }
            }
            if (itemAvailableDays.isEmpty()) {
                System.out.println("Select at least one available day for an Item");
            } else {
                break;
            }

        }
        return itemAvailableDays;
    }
    void assignHideItems(FoodSeller seller) {
        HashMap<String,ArrayList<Item>> items=seller.getHotel().getItems();
        ArrayList<String > hideItems=seller.getHotel().getHideItems();
        String itemType;
        while(true) {
            ArrayList<String> itemTypes=new ArrayList<>();
            int itemCount = 1;
            for (Map.Entry<String, ArrayList<Item>> item : items.entrySet()) {
                System.out.println("Enter \"" + itemCount++ + "\" for " + item.getKey());
                itemTypes.add(item.getKey());
            }
            System.out.println("Enter \""+ itemCount +"\" for Go-Back");
            int checkItemOption;
            while (true) {
                try {
                    scan.nextLine();
                    checkItemOption = scan.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Enter a valid Number");
                }
            }
            if(checkItemOption>0 && checkItemOption<=itemTypes.size()){
                itemType=itemTypes.get(checkItemOption-1);
                ArrayList<Item> availableItem=items.get(itemType);
                boolean isHideItem=true;
                checkItem:
                for (int i=0;i<availableItem.size();) {
                    String  hasHideItem=null;
                    for (String hideItem : hideItems) {
                        if (availableItem.get(i).getItemId().equals(hideItem)) {
                            hasHideItem = hideItem;
                        }
                    }
                    if(hasHideItem!=null) {
                        showItem(availableItem.get(i));
                        showHideItemOption(i,availableItem.size());
                        isHideItem = false;
                        while (true) {
                            String checkOption = scan.next();
                            if(checkOption.equals("1") && i!=0){
                                i--;
                                break;
                            } else if (checkOption.equals("2") && i!=(availableItem.size()-1)) {
                                i++;
                                break;
                            } else if (checkOption.equals("3") ) {
                                hideItems.remove(hasHideItem);
                                break ;
                            } else if (checkOption.equals("4")) {
                                hideItems.remove(hasHideItem);
                                availableItem.remove(availableItem.get(i));
                                break ;
                            } else if (checkOption.equals("9")) {
                                break checkItem;
                            }  else {
                                System.out.println("Enter a valid number");
                            }
                        }
                    }
                    else {
                        i++;
                    }
                }
                if(isHideItem){
                    System.out.println("No Hide Items in "+itemType);
                }
            } else if (checkItemOption==(itemTypes.size()+1)) {
                break;
            } else {
                System.out.println("Enter a Valid Number");
            }
        }

    }
    void assignLeave(Hotel hotel) {
        ArrayList<LocalDate> leave=hotel.getLeave();
        checkLeave:
        while(true){
            System.out.println("Enter \"1\" for View Leave");
            System.out.println("Enter \"2\" for Apply Leave");
            System.out.println("Enter \"9\" for Go Back");
            String checkOption= scan.next();
            switch (checkOption) {
                case "1":
                    if (leave == null) {
                        System.out.println("Leave List is Empty");
                    } else {
                        System.out.println(leave);
                    }
                    break;
                case "2":
                    String leaveDate;
                    LocalDate date;
                    addLeave:
                    while (true) {
                        while (true) {
                            System.out.println("Enter a Leave Date");
                            try {
                                leaveDate = scan.next();
                                date = LocalDate.parse(leaveDate);
                                break;
                            } catch (Exception e) {
                                System.out.println("Enter a Date Format in yyyy-MM-dd ");
                            }
                        }
                        if (leave == null) {
                            ArrayList<LocalDate> newLeave = new ArrayList<>();
                            newLeave.add(date);
                            leave = newLeave;
                        } else {
                            leave.add(date);
                        }
                        hotel.setLeave(leave);
                        System.out.println("Enter \"1\" for Add Another Date");
                        System.out.println("Enter \"9\" for Go-Back");
                        String checkLeaveOption = scan.next();
                        while (true) {
                            if (checkLeaveOption.equals("1")) {
                                continue addLeave;
                            } else if (checkLeaveOption.equals("9")) {
                                break addLeave;
                            } else {
                                System.out.println("Enter a valid Number");
                            }
                        }
                    }
                    break;
                case "9":
                    break checkLeave;
                default:
                    System.out.println("Enter a valid number");
                    break;
            }
        }
    }
    Type assignHotelType(){
        Type hotelType;
        int i=1;
        for(Type type:Type.values()){
            System.out.println("Enter "+ i++ + " for "+ type);
        }
        type:
        while(true){
            System.out.println("Enter your Hotel Type");
            String str=scan.next();
            switch(str){
                case "1":
                    hotelType=Type.VEG;
                    break type;
                case "2":
                    hotelType=Type.NON_VEG;
                    break type;
                case "3":
                    hotelType=Type.VEG_and_NON_VEG;
                    break type;
                default:
                    System.out.println("Enter a valid Input");
                    break;
            }
        }
        return hotelType;
    }
    HashMap<Days,ArrayList<HotelTime>> getHotelAvailableDays() {
        Days from = null;
        Days to = null;
        HashMap<Days,ArrayList<HotelTime>> availableDays=new HashMap<>();
        int input=2;
        while(input!=0) {
            System.out.println();
            int dayCount = 1;
            for (Days day : Days.values()) {
                System.out.println("Enter " + dayCount + " for " + day);
                dayCount++;
            }
            System.out.println("Enter a Hotel Day Available "+(input==2?"From":"To"));
            String availableDay=scan.next();
            switch (availableDay) {
                case "1" -> {
                    if(input==2){
                        from=Days.SUNDAY;
                    }else {
                        to=Days.SUNDAY;
                    }
                    input--;
                }
                case "2" -> {
                    if(input==2){
                        from=Days.MONDAY;
                    }else {
                        to=Days.MONDAY;
                    }
                    input--;
                }
                case "3" -> {
                    if(input==2){
                        from=Days.TUESDAY;
                    }else {
                        to=Days.TUESDAY;
                    }
                    input--;
                }
                case "4" -> {
                    if(input==2){
                        from=Days.WEDNESDAY;
                    }else {
                        to=Days.WEDNESDAY;
                    }
                    input--;
                }
                case "5" -> {
                    if(input==2){
                        from=Days.THURSDAY;
                    }else {
                        to=Days.THURSDAY;
                    }
                    input--;
                }
                case "6" -> {
                    if(input==2){
                        from=Days.FRIDAY;
                    }else {
                        to=Days.FRIDAY;
                    }
                    input--;
                }
                case "7" -> {
                    if(input==2){
                        from=Days.SATURDAY;
                    }else {
                        to=Days.SATURDAY;
                    }
                    input--;
                }
                default -> System.out.println("Enter a valid input\n");
            }
        }
        ArrayList<HotelTime> availableTime;
        while (true) {
            System.out.println("Enter \"1\" for Set Timing for Each Day");
            System.out.println("Enter \"2\" for Set Timing for All Mentioned Days at once");
            String checkOption = scan.next();
            if (checkOption.equals("1")) {
                setDays:
                while(true) {
                    for (Days day : Days.values()) {
                        if (from != null && from.equals(day)) {
                            from = null;
                        }
                        if(from==null){
                            System.out.println("Enter a Timing for "+day);
                            availableDays.put(day, getHotelAvailableTiming());
                        }
                        if (from==null && to.equals(day)) {
                            to = null;
                        }
                        if(from==null && to==null){
                            break setDays;
                        }
                    }
                }
                break;
            } else if (checkOption.equals("2")) {
                System.out.println("Enter a Timing for All Days");
                availableTime = getHotelAvailableTiming();
                setDays:
                while(true) {
                    for (Days day : Days.values()) {
                        if (from != null && from.equals(day)) {
                            from = null;
                        }
                        if(from==null){
                            HotelTime hotelTime;
                            ArrayList<HotelTime> availableTimes = new ArrayList<>();
                            for(PartOfDay part:PartOfDay.values()){
                                for(HotelTime hotelTiming:availableTime){
                                    if(hotelTiming.getDay().equals(part)){
                                        hotelTime=new HotelTime(hotelTiming.getDay(),hotelTiming.getFrom(),hotelTiming.getTo());
                                        availableTimes.add(hotelTime);
                                    }
                                }
                            }
                            availableDays.put(day,availableTimes);
                        }
                        if (from==null && to.equals(day)) {
                            to = null;
                        }
                        if(from==null && to==null){
                            break setDays;
                        }
                    }
                }
                break;
            } else {
                System.out.println("Enter a valid number");
            }
        }
        return availableDays;
    }
    ArrayList<String> assignContactNumber() {
        ArrayList<String> numbers=new ArrayList<>();
        contact:
        while(true) {
            System.out.println("Enter your Contact Number");
            String number= scan.next();
            if(checkContactNumberFormat(number)){
                numbers.add(number);
                while(true) {
                    System.out.println("Enter \"1\" for enter another Contact Number");
                    System.out.println("Enter \"0\" for Exit Contact");
                    String check = scan.next();
                    if (check.equals("1")) {
                        continue contact;
                    } else if (check.equals("0")) {
                        break contact;
                    }else {
                        System.out.println("Enter a Valid Number");
                    }
                }
            }
            System.out.println("Entered Contact Number is invalid");
            if(numbers.size()>=1){
                while(true) {
                    System.out.println("Enter \"1\" for Re-enter Contact Number");
                    System.out.println("Enter \"0\" for Skip");
                    String check = scan.next();
                    if (check.equals("1")) {
                        break;
                    } else if (check.equals("0")) {
                        break contact;
                    }else {
                        System.out.println("Enter a valid number");
                    }
                }
            }
        }
        return numbers;
    }
    void showHideItemOption(int index,int size) {
        if(index!=0) {
            System.out.println("Enter \"1\" for Previous");
        }
        if(index!=(size-1)) {
            System.out.println("Enter \"2\" for Next");
        }
        System.out.println("Enter \"3\" for Restore");
        System.out.println("Enter \"4\" for Remove Item");
        System.out.println("Enter \"9\" for Go-Back");
        System.out.println("Enter \"0\" for Exit");
    }
    void showSellerOption(){
        System.out.println("Enter \"1\" for Show Profile");
        System.out.println("Enter \"2\" for View Items");
        System.out.println("Enter \"3\" for Add Items");
        System.out.println("Enter \"4\" for Hide Items");
        System.out.println("Enter \"5\" for Apply Leave");
        System.out.println("Enter \"9\" for Logout");
        System.out.println("Enter \"0\" for Exit");
    }
}
