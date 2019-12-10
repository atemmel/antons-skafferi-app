package se.grupp1.antonsskafferi.lib;

public class DatabaseURL {
    private static final String local = "http://10.0.2.2:8080/";
    private static final String remote = "http://82.196.113.65:8080/";

    //Om appen skall kopplas till en lokal databas
    private static final boolean usingLocal = false;

    private static final String base = usingLocal ? local : remote;

    // --- Bokningar

    //Hämta

    //Alla
    public static final String getCustomers = base + "customers";

    //För ett bord
    public static final String getBookingsForTable = getCustomers + "/dinnertable?dinnerTable=";

    //Lägg till
    public static final String insertCustomer = base + "post/customers?customer=";

    //Ta bort
    public static final String deleteCustomer = base + "customers/delete/customer?id=";


    // --- Items

    //Hämta
    public static final String getItems = base + "items";

    //Lägg till/Redigera
    public static final String insertItem = base + "post/items?item=";

    //Ta bort
    public static final String deleteItem = base + "items/delete/item?id=";


    // --- Beställningar

    //Lägg till
    public static final String insertOrder = base + "post/orders?order=";

    //Hämta
    public static final String getOrders = base + "orders";

    //Sätt levererad
    public static final String setDelivered = base + "post/delivered?dinnertable=";

    // --- Bord

    //Hämta
    public static final String getTables = base + "dinnertables";


    // --- Kategorier

    //Hämta
    public static final String getCategories = base + "itemcategorys";


    // --- Inloggning (användare)

    //Testa:
    public static final String validateLogin = base + "users/login?username=";
    public static final String validateLoginPassword = "&password=";

    //Hämta
    public static final String getUsers = base + "users";


    // --- Summering
    public static final String summaryByTable = base + "orders/table?dinnertable=";


    // --- Employees
    public static final String getEmployees = base + "employees";


    // --- Workingschedule
    public static final String getWorkingSchedule = base + "schedules";

    private DatabaseURL() {
    }
}
