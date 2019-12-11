package se.grupp1.antonsskafferi.lib;

public class DatabaseURL {
    private static final String local = "http://10.0.2.2:8080/";
    private static final String remote = "http://82.196.113.65:8080/";

    //Om appen skall kopplas till en lokal databas
    private static final boolean usingLocal = true;

    private static final String base = usingLocal ? local : remote;

    // --- Bokningar

    //Hämta

    //Alla
    public static final String getCustomers = base + "customers";

    //För ett id
    public static final String getCustomerById = base + "customers/customer?id=";

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

    //Hämta alla
    public static final String getOrders = base + "orders";

    //Hämta klara
    public static final String getReadyOrders = getOrders + "/ready";

    //Hämta ej klara
    public static final String getUnreadyOrders = getOrders + "/unready";

    //Sätt levererad
    public static final String setDelivered = base + "post/delivered?dinnertable=";



    //Ta bort för ett bord
    public static final String deleteOrders = base + "orders/delete?dinnertable=";


    // --- Bord

    //Hämta
    public static final String getTables = base + "dinnertables";

    //Hämta om ett bord används
    public static final String getIfTableInUse = base + "/dinnertables/active?dinnertable=";

    //Hämta på datum
    public static final String getTableAvailableForDate = base + "dinnertables/booking?date=";

    //Sätt att det ej används
    public static final String setTableInUse = base + "/post/active/true?dinnertable=";

    //Sätt att det används
    public static final String setTableNotInUse = base + "/post/active/false?dinnertable=";


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

    // --- User Edit
    public static final String addUserFirstName = base + "post/newuser?firstname=";
    public static final String addUserLastName = "&lastname=";
    public static final String addUserPassword = "&password=";
    public static final String addUserAdmin = "&admin=";


    // --- Testing of posting image
    public static final String testingImage = base + "upload";

    // --- Employees
    public static final String getEmployees = base + "employees";


    // --- Workingschedule
    public static final String getWorkingSchedule = base + "schedules";

    // --- Hämta tid för jobbpass med givet namn och datum
    public static final String getWorkScheduleByNameAndDate = base + "schedules/schedule?name=";
    public static final String getGetWorkingScheduleByDate = "&date=";

    // --- Hämta alla händelser i schemat på ett givet datum
    public static final String getScheduleByDate = base + "schedules/schedule/date?date=";


    private DatabaseURL() {
    }
}
