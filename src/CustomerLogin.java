
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CustomerLogin extends Login{
    Scanner scan=new Scanner(System.in);
    Customer customer;
    Storage storage=new Storage();
    void addNewCustomer(){
        System.out.println("Enter a Name");
        String name;
        while (true) {
            name = scan.nextLine();
            if(name.length()!=0){
                break;
            }
        }
        String userName;
        while (true) {
            System.out.println("Enter a userName");
            userName = scan.next();
            if(storage.checkCustomerUserName(userName)){
                break;
            }else {
                System.out.println("Entered User Name is already Exist");
            }
        }
        System.out.println("Enter a Password");
        String password= scan.next();
        String emailId;
        while(true){
            System.out.println("Enter your emailId");
            emailId= scan.next();
            if(checkEmailIdFormat(emailId)){
                if(storage.checkNewCustomerEmailId(emailId)){
                    break;
                }else {
                    System.out.println("Entered EmailId is already Exist");
                }
            }else {
                System.out.println("Enter a valid EmailId");
            }
        }
        String contactNumber;
        while(true){
            System.out.println("Enter a Contact Number");
            contactNumber= scan.next();
            if(checkContactNumberFormat(contactNumber)){
                break;
            }else {
                System.out.println("Enter a valid Contact Number");
            }
        }
        customer=new Customer(name,userName,password,emailId,contactNumber,null,null,null);
        storage.setCustomers(customer);
        System.out.println("Customer Account is successfully Added");
    }
    boolean customerLogin() {
        while (true) {
            System.out.println("Enter your UserName");
            String userName = scan.next();
            System.out.println("Enter your PassWord");
            String password = scan.next();
            customer=storage.checkCustomerLogin(userName, password);
            if (customer!=null) {
                while(true) {
                    showCustomerOption();
                    String checkSellerOption = scan.next();
                    switch (checkSellerOption){
                        case "1":
                            if(viewHotels(customer)){
                                return true;
                            }
                            break ;
                        case "2":
                            if(customer.getCart()==null){
                                System.out.println("Cart is Empty");
                            }else {
                                if (viewCart(customer)) {
                                    return true;
                                }
                            }
                            break ;
                        case "3":
                            if(customer.getOrder()==null){
                                System.out.println("Your order List is Empty" );
                            }else{
                                for(Order order:storage.getAdminStorage().getOrder()){
                                    for(String orderId:customer.getOrder()){
                                        if(order.getOrderId().equals(orderId)){
                                            showOrder(order);
                                        }
                                    }
                                }
                            }
                            break;
                        case "4":
                            while (true) {
                                showCustomerProfile(customer);
                                System.out.println("Enter \"1\" for Edit Profile");
                                System.out.println("Enter \"9\" for Go-Back");
                                String checkOption= scan.next();
                                if(checkOption.equals("1")){
                                    editCustomerProfile(customer);
                                } else if (checkOption.equals("9")) {
                                    break;
                                }else {
                                    System.out.println("Enter a valid Number");
                                }
                            }
                            break;
                        case "9":
                            return false;
                        case "0":
                            return true;
                        default:
                            System.out.println("Enter a valid Number");
                    }
                }
            } else {
                checkEmailId:
                while (true) {
                    getLoginErrorOption();
                    String checkOption = scan.next();
                    switch (checkOption) {
                        case "1":
                            break checkEmailId;
                        case "2":
                            while (true) {
                                System.out.println("Enter your EmailId");
                                String emailId = scan.next();
                                customer = storage.checkCustomerEmailId(emailId);
                                if (customer != null) {
                                    if (!(customer.getUserName().equals(userName))) {
                                        System.out.println("\nUserName : " + customer.getUserName());
                                    }
                                    if (!(customer.getPassword().equals(password))) {
                                        System.out.println("Password : " + customer.getPassword());
                                    }
                                    System.out.println();
                                    break checkEmailId;
                                } else {
                                    emailIdMismatch();
                                    String checkEmailOption = scan.next();
                                    checkEmail:
                                    while (true) {
                                        switch (checkEmailOption) {
                                            case "1":
                                                break checkEmail;
                                            case "9":
                                                return false;
                                            case "0":
                                                return true;
                                            default:
                                                System.out.println("Enter a valid number\n");
                                                break;
                                        }
                                    }
                                }
                            }
                        case "9":
                            return false;
                        case "0":
                            return true;
                    }
                }
            }
        }
    }
    void showOrder(Order order){
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        System.out.println("Order Id : "+order.getOrderId());
        System.out.println("Date and Time : "+order.getOrderedDate()+" / "+order.getOrderedTime());
        System.out.println("Hotel Name : "+order.getHotelName());
        System.out.println("Customer Name : "+order.getCustomerName());
        System.out.println("----------------------------------------------------");
        for(Cart cart:order.getOrders()){
            System.out.print(cart.getItem().getItemName()+"("+cart.getItem().getItemType()+")");
            System.out.print("\t\t"+cart.getPrice()+"\t\t"+cart.getQuantity());
            System.out.println("\t\t"+cart.getQuantity()*cart.getPrice());
        }
        System.out.println("----------------------------------------------------");
        System.out.println("Total Item : "+order.getTotalItem()+" \t Total Price : "+order.getTotalPrice());
        System.out.println("Address : "+order.getAddress().getDNumber()+","+order.getAddress().getStreet());
        System.out.println("\t\t "+order.getAddress().getCity()+" - "+order.getAddress().getPinCode());
        System.out.println("\t\t "+order.getAddress().getState());
        System.out.println("Contact Number : "+order.getContactNumber());
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        System.out.println();
    }
    boolean viewHotels(Customer customer){
        ArrayList<Hotel> hotels=storage.getHotels();
        if(!(hotels.isEmpty())){
            System.out.println("Check"+hotels);
            for(int i=0;i<hotels.size();){
                showHotelOption(i,hotels.size(),hotels.get(i));
                while(true) {
                    String checkOption = scan.next();
                    if (checkOption.equals("1") && i!=0) {
                        i--;
                        break ;
                    } else if (checkOption.equals("2") && i!=(hotels.size()-1)) {
                        i++;
                        break ;
                    } else if (checkOption.equals("3")) {
                        if(hotels.get(i)!=null){
                            if(viewItems(hotels.get(i),customer)){
                                return false;
                            }
                        }else {
                            System.out.println("Currently No Items Available on this Hotel");
                        }
                        break ;
                    } else if (checkOption.equals("9")) {
                        return false;
                    } else if (checkOption.equals("0")) {
                        return true;
                    } else {
                        System.out.println("Enter a valid Number");
                    }
                }
            }
        }else {
            System.out.println("There is no available Hotel at current time");
        }
        return false;
    }

    private void showHotelOption(int index,int size,Hotel hotel) {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Hotel name : "+hotel.getHotelName());
        System.out.println("\t\t\t Type : "+hotel.getHotelType());
        System.out.println("Address : "+hotel.getHotelAddress().getDNumber()+","+hotel.getHotelAddress().getStreet());
        System.out.println(hotel.getHotelAddress().getCity()+" - "+hotel.getHotelAddress().getPinCode());
        System.out.println(hotel.getHotelAddress().getState());
        System.out.println("-----------------------------------------------------------------");
        if(index!=0) {
            System.out.println("Enter \"1\" for Previous");
        }
        if(index!=(size-1)) {
            System.out.println("Enter \"2\" for Next");
        }
        System.out.println("Enter \"3\" for view Hotel");
        System.out.println("Enter \"9\" for Go-Back");
        System.out.println("Enter \"0\" for Exit");
    }
    boolean viewItems(Hotel hotel,Customer customer){
        HashMap<String,ArrayList<Item>> items=hotel.getItems();
        if(items!=null) {
            String itemType;
            showItem:
            while (true) {
                int itemCount = 1;
                ArrayList<String> itemTypes = new ArrayList<>();
                for (Map.Entry<String, ArrayList<Item>> item : items.entrySet()) {
                    System.out.println("Enter \"" + itemCount++ + "\" for " + item.getKey());
                    itemTypes.add(item.getKey());
                }
                System.out.println("Enter \"" + itemCount + "\" for Go-Back");
                System.out.println("Enter \"0\" for Exit View Hotel");
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
                        itemType = itemTypes.get(checkItemOption - 1);
                        ArrayList<Item> availableItem = getItems(items.get(itemType),hotel.getHideItems());
                        if (!(availableItem.isEmpty())) {
                            for (int i = 0; i < availableItem.size(); ) {
                                showItemOption(i, availableItem.size(),availableItem.get(i));
                                while (true) {
                                    String checkOption = scan.next();
                                    if (checkOption.equals("1") && i != 0) {
                                        i--;
                                        break;
                                    } else if (checkOption.equals("2") && i != (availableItem.size() - 1)) {
                                        i++;
                                        break;
                                    } else if (checkOption.equals("3")) {
                                        ArrayList<Cart> cartList = customer.getCart();
                                        int quantity;
                                        while (true) {
                                            try {
                                                System.out.println("Enter a Quantity of Item");
                                                quantity = scan.nextInt();
                                                if (quantity > 0 && quantity < 50) {
                                                    break;
                                                } else if (quantity == 0) {
                                                    System.out.println("Select At-least one Item");
                                                } else {
                                                    System.out.println("Enter a valid number");
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Enter a valid Number");
                                            }
                                        }
                                        Cart cart = new Cart(hotel.getHotelId(), availableItem.get(i), quantity, availableItem.get(i).getPrice());
                                        boolean isSameHotel = true;
                                        if (cartList!=null) {
                                            isSameHotel = hotel.getHotelId().equals(cartList.get(0).getHotelId());
                                        }
                                        if (cartList==null || !(isSameHotel)) {
                                            ArrayList<Cart> newCart = new ArrayList<>();
                                            newCart.add(cart);
                                            cartList = newCart;
                                        } else {
                                            cartList.add(cart);
                                        }
                                        customer.setCart(cartList);
                                        break;
                                    } else if (checkOption.equals("9")) {
                                        break viewType;
                                    } else if (checkOption.equals("0")) {
                                        return true;
                                    }else {
                                        System.out.println("Enter a valid number");
                                    }
                                }
                            }
                        } else {
                            System.out.println("Currently no Items Available on " + itemType);
                            break;
                        }
                    } else if (checkItemOption == itemCount) {

                        break showItem;
                    } else if (checkItemOption==0) {
                        return true;
                    } else {
                        System.out.println("Enter a Valid Number");
                    }
                }
            }
        }else {
            System.out.println("Currently No Items Available on this Hotel");
        }
        return false;
    }

    private void showItemOption(int index, int size,Item item) {
        System.out.println("------------------------------------------------");
        System.out.println(item.getItemName()+"\t\t*"+item.getItemKind());
        System.out.println("\tPrice : "+item.getPrice());
        System.out.println("Ingredients : "+item.getIngredients());
        System.out.println("Description : "+item.getDescription());
        System.out.println("------------------------------------------------");
        if(index!=0){
            System.out.println("Enter \"1\" for Previous");
        }
        if(index!=(size-1)){
            System.out.println("Enter \"2\" for Next");
        }
        System.out.println("Enter \"3\" for Add to Cart");
        System.out.println("Enter \"9\" for Go-Back");
        System.out.println("Enter \"0\" for Exit View hotel");
    }
    private ArrayList<Item> getItems(ArrayList<Item> items,ArrayList<String> hideItems) {
        ArrayList<Item> currentItem=new ArrayList<>();
        for(Item item:items){
            boolean isHide=true;
            if(hideItems!=null){
                for(String itemId:hideItems){
                    if(item.getItemId().equals(itemId)){
                        isHide=false;
                        break;
                    }
                }
            }
            if(isHide) {
                HashMap<Days, ArrayList<HotelTime>> itemAvailableDays = item.getItemAvailableDays();
                LocalTime presentTime = LocalTime.now();
                DayOfWeek day = DayOfWeek.from(LocalDate.now());
                String presentDay = String.valueOf(day);
                for (Map.Entry<Days, ArrayList<HotelTime>> days : itemAvailableDays.entrySet()) {
                    if (days.getKey().toString().equals(presentDay)) {
                        ArrayList<HotelTime> hotelTimes = itemAvailableDays.get(days.getKey());
                        for (HotelTime hotelTime : hotelTimes) {
                            if (hotelTime.getFrom().isBefore(presentTime) && hotelTime.getTo().isAfter(presentTime)) {
                                currentItem.add(item);
                            }
                        }
                    }
                }
            }
        }
        return currentItem;
    }
    boolean viewCart(Customer customer){
        ArrayList<Cart> cartList=customer.getCart();
        double totalPrice;
        int totalItem;
        if(customer.getCart()!=null ) {
            Hotel hotel = storage.getHotel(customer.getCart().get(0).getHotelId());
            if(hotel!=null) {
                totalPrice=0D;
                totalItem=0;
                System.out.println("Hotel Name : "+hotel.getHotelName()+"\t*"+hotel.getHotelType());
                System.out.println("----------------------------------------------------------");
                for(Cart cart:cartList) {
                    System.out.print(cart.getItem().getItemName()+"("+cart.getItem().getItemType()+")");
                    System.out.print("\t\t"+cart.getPrice()+"\t\t"+cart.getQuantity());
                    System.out.println("\t\t"+cart.getQuantity()*cart.getPrice());
                    totalItem+= cart.getQuantity();
                    totalPrice+= cart.getPrice()*cart.getQuantity();
                }
                System.out.println("----------------------------------------------------------");
                System.out.println("Total Item : "+totalItem+" \t Total Price : "+totalPrice);
                cart:
                while(true) {
                    showCartOption();
                    String checkCartOption= scan.next();
                    switch (checkCartOption){
                        case "1":
                            LocalDate presentDate=LocalDate.now();
                            LocalTime presentTime=LocalTime.now();
                            ArrayList<Cart> unavailableItem=new ArrayList<>();
                            for(Cart cart:cartList) {
                                if(checkItemsAvailable(cart.getItem(),presentDate,presentTime,hotel.getHideItems())) {
                                    unavailableItem.add(cart);
                                }
                            }
                            if(!(unavailableItem.isEmpty())){
                                System.out.println("----------------------------------------------------------");
                                for(Cart cart:unavailableItem) {
                                    System.out.print(cart.getItem().getItemName()+"("+cart.getItem().getItemType()+")");
                                    System.out.print("\t\t"+cart.getPrice()+"\t\t"+cart.getQuantity());
                                    System.out.println("\t\t"+cart.getQuantity()*cart.getPrice());
                                }
                                System.out.println("----------------------------------------------------------");
                                System.out.println("Above mentioned Item is Currently Unavailable");
                                while(true) {
                                    if (cartList.size() != unavailableItem.size()) {
                                        System.out.println("Enter \"1\" Remove a unavailable Item and buy Remaining");
                                    }
                                    System.out.println("Enter \"9\" for Go-Back");
                                    System.out.println("Enter \"0\" for Exit Cart");
                                    String checkOption = scan.next();
                                    if (checkOption.equals("1") && cartList.size()!=unavailableItem.size()){
                                        cartList.removeAll(unavailableItem);
                                        customer.setCart(cartList);
                                        break;
                                    }else if(checkOption.equals("9")){
                                        continue cart;
                                    } else if (checkOption.equals("0")) {
                                        return false;
                                    }else {
                                        System.out.println("Enter a valid number");
                                    }
                                }
                            }
                            String orderId = storage.getOrderId();
                            ArrayList<Cart> orderItem = new ArrayList<>();
                            totalPrice=0D;
                            totalItem=0;
                            for (Cart cart : cartList) {
                                Item item = new Item(cart.getItem().getItemName(), cart.getItem().getItemType(),
                                        cart.getItem().getItemKind(), cart.getItem().getItemId(), cart.getItem()
                                        .getPrice(), cart.getItem().getIngredients(), cart.getItem().getDescription(),
                                        cart.getItem().getItemAvailableDays());
                                totalItem+= cart.getQuantity();
                                totalPrice+= cart.getPrice()*cart.getQuantity();
                                cart.setItem(item);
                                orderItem.add(cart);
                            }
                            String hotelName = hotel.getHotelName();
                            Address address;
                            if (customer.getAddress() != null) {
                                System.out.print("Address : " + customer.getAddress().getDNumber() + ", ");
                                System.out.println(customer.getAddress().getStreet());
                                System.out.println("\t\t" + customer.getAddress().getCity() + " - " + customer.getAddress().getPinCode());
                                System.out.println("\t\t" + customer.getAddress().getState());
                                System.out.println("Enter \"1\" for Proceed with Previous Address");
                                System.out.println("Enter \"2\" for New Address");
                                while (true) {
                                    String checkAddressOption = scan.next();
                                    if (checkAddressOption.equals("1")) {
                                        address = customer.getAddress();
                                        break;
                                    } else if (checkAddressOption.equals("2")) {
                                        address = setAddress();
                                        customer.setAddress(address);
                                        break;
                                    } else {
                                        System.out.println("Enter a valid Number");
                                    }
                                }
                            } else {
                                address = setAddress();
                                customer.setAddress(address);
                            }
                            ArrayList<String> contactNumber = assignContactNumber();
                            Order order = new Order(orderId, customer.getName(), hotelName, orderItem, totalPrice, totalItem,
                                    LocalDate.now(), LocalTime.now(), address, contactNumber);
                            ArrayList<Order> orderList = storage.getAdminStorage().getOrder();
                            if (orderList == null) {
                                ArrayList<Order> newOrder = new ArrayList<>();
                                newOrder.add(order);
                                orderList = newOrder;
                            } else {
                                orderList.add(order);
                            }
                            storage.getAdminStorage().setOrder(orderList);
                            ArrayList<String> customerOrder=customer.getOrder();
                            if(customerOrder==null){
                                ArrayList<String> newOrderId=new ArrayList<>();
                                newOrderId.add(orderId);
                                customerOrder=newOrderId;
                            }else {
                                customerOrder.add(orderId);
                            }
                            customer.setOrder(customerOrder);
                            customer.setCart(null);
                            break cart;
                        case "2":
                            editCartItems(customer);
                            break cart;
                        case "9":
                            return false;
                        case "0":
                            return true;
                        default:
                            System.out.println("Enter a valid Number");
                    }
                }

            }else {
                customer.setCart(null);
                System.out.println("Cart is Empty");
            }
        }else {
            System.out.println("Cart is Empty");
        }
        return false;
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
    boolean checkItemsAvailable(Item item,LocalDate date,LocalTime time,ArrayList<String> hideItems){
        DayOfWeek day=DayOfWeek.from(date);
        String presentDay=String.valueOf(day);
        if(hideItems!=null){
            for(String itemId:hideItems){
                if(item.getItemId().equals(itemId)){
                    return true;
                }
            }
        }
        for (Map.Entry<Days, ArrayList<HotelTime>> days : item.getItemAvailableDays().entrySet()){
            if(days.getKey().toString().equals(presentDay)){
                ArrayList<HotelTime> hotelTimes=item.getItemAvailableDays().get(days.getKey());
                for(HotelTime hotelTime:hotelTimes){
                    if(hotelTime.getFrom().isBefore(time) && hotelTime.getTo().isAfter(time)){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    void editCartItems(Customer customer){
        for(int i=0;i<customer.getCart().size();) {
            while (true) {
                showCartItemEditOption(i, customer.getCart().size(),customer.getCart().get(i));
                String checkEditOption = scan.next();
                if (checkEditOption.equals("1") && i != 0) {
                    i--;
                    break;
                } else if (checkEditOption.equals("2") && i != (customer.getCart().size() - 1)) {
                    i++;
                    break;
                } else if (checkEditOption.equals("3")) {
                    int quantity;
                    while (true) {
                        try {
                            System.out.println("Enter a Quantity of Item");
                            quantity = scan.nextInt();
                            if (quantity > 0 && quantity < 50) {
                                break;
                            } else if (quantity == 0) {
                                System.out.println("Select At-least one Item");
                            } else {
                                System.out.println("Enter a valid number");
                            }
                        } catch (Exception e) {
                            System.out.println("Enter a valid Number");
                        }
                    }
                    customer.getCart().get(i).setQuantity(quantity);
                    break;
                } else if (checkEditOption.equals("4")) {
                    customer.getCart().remove(i);
                    break;
                } else if (checkEditOption.equals("9")) {
                    return ;
                } else {
                    System.out.println("Enter a valid number");
                }
            }
        }
    }
    private void showCartItemEditOption(int index,int size,Cart cart){
        System.out.println("---------------------------------------------------");
        System.out.print("Item Name : "+cart.getItem().getItemName()+"("+cart.getItem().getItemType()+")");
        System.out.print("Price : "+cart.getPrice()+"\t\tQuantity : "+cart.getQuantity());
        System.out.println("\t\tTotal Price : "+cart.getQuantity()*cart.getPrice());
        System.out.println("---------------------------------------------------");
        if(index!=0){
            System.out.println("Enter \"1\" for Previous");
        }
        if(index!=(size-1)){
            System.out.println("Enter \"2\" for Next");
        }
        System.out.println("Enter \"3\" for Change Quantity");
        System.out.println("Enter \"4\" for Remove Item");
        System.out.println("Enter \"9\" Go-Back");
    }
    private void editCustomerProfile(Customer customer){
        System.out.println("Customer Name : "+customer.getName());
        showEditOption();
        editName:
        while(true){
            String checkEditOption= scan.next();
            switch (checkEditOption){
                case "1":
                    System.out.println("Enter a Name");
                    scan.nextLine();
                    String name= scan.nextLine();
                    customer.setName(name);
                    break editName;
                case "2":
                    break editName;
                case "9":
                    return;
                default:
                    System.out.println("Enter a valid Number");
            }
        }
        System.out.println("Customer User Name : "+customer.getUserName());
        showEditOption();
        editUserName:
        while (true){
            String checkEditOption= scan.next();
            switch (checkEditOption){
                case "1":
                    while (true) {
                        System.out.println("Enter a User Name");
                        String userName= scan.next();
                        if (storage.checkCustomerUserName(userName) || userName.equals(customer.getUserName())) {
                            customer.setUserName(userName);
                            break ;
                        } else {
                            System.out.println("Entered User Name is already Exist");
                        }
                    }
                    break editUserName;
                case "2":
                    break editUserName;
                case "9":
                    return;
                default:
                    System.out.println("Enter a valid Number");
            }
        }
        System.out.println("Password : "+customer.getPassword());
        showEditOption();
        editPassword:
        while (true){
            String checkEditOption= scan.next();
            switch (checkEditOption){
                case "1":
                    System.out.println("Enter a Password");
                    String password= scan.next();
                        customer.setPassword(password);
                    break editPassword;
                case "2":
                    break editPassword;
                case "9":
                    return;
                default:
                    System.out.println("Enter a valid Number");
            }
        }
        System.out.println("Email Id : "+customer.getEmailId());
        showEditOption();
        editEmailId:
        while (true){
            String checkEditOption= scan.next();
            switch (checkEditOption){
                case "1":
                    String emailId;
                    while(true){
                        System.out.println("Enter your emailId");
                        emailId= scan.next();
                        if(checkEmailIdFormat(emailId)){
                            if(storage.checkNewCustomerEmailId(emailId) || customer.getEmailId().equals(emailId)){
                                break;
                            }else {
                                System.out.println("Entered EmailId is already Exist");
                            }
                        }else {
                            System.out.println("Enter a valid EmailId");
                        }
                    }
                    customer.setEmailId(emailId);
                    break editEmailId;
                case "2":
                    break editEmailId;
                case "9":
                    return;
                default:
                    System.out.println("Enter a valid Number");
            }
        }
        System.out.println("Contact Number : "+customer.getContactNumber());
        showEditOption();
        editContact:
        while (true){
            String checkEditOption= scan.next();
            switch (checkEditOption){
                case "1":
                    String contactNumber;
                    while(true){
                        System.out.println("Enter a Contact Number");
                        contactNumber= scan.next();
                        if(checkContactNumberFormat(contactNumber)){
                            break;
                        }else {
                            System.out.println("Enter a valid Contact Number");
                        }
                    }
                    customer.setContactNumber(contactNumber);
                    break editContact;
                case "2":
                    break editContact;
                case "9":
                    return;
                default:
                    System.out.println("Enter a valid Number");
            }
        }
    }
    void showCustomerProfile(Customer customer){
        System.out.println("Name \t\t\t: "+customer.getName());
        System.out.println("User Name \t\t: "+customer.getUserName());
        System.out.println("Password \t\t: "+customer.getPassword());
        System.out.println("EmailId \t\t: "+customer.getEmailId());
        System.out.println("Contact Number \t: "+customer.getContactNumber());
    }
    private void showCartOption() {
        System.out.println("Enter \"1\" for Buy Item");
        System.out.println("Enter \"2\" for Edit Items in Cart");
        System.out.println("Enter \"9\" for Go-Back");
        System.out.println("Enter \"0\" for Exit");
    }
    private void showCustomerOption() {
        System.out.println("Enter \"1\" for View Hotels");
        System.out.println("Enter \"2\" for View Cart");
        System.out.println("Enter \"3\" for View Orders");
        System.out.println("Enter \"4\" for Show Profile");
        System.out.println("Enter \"9\" for Logout");
        System.out.println("Enter \"0\" for Exit");
    }
}
