package se.grupp1.antonsskafferi.lib;

public class DatabaseURL {
    private static final String local = "http://10.0.2.2:8080/";
    private static final String remote = "http://82.196.113.65:8080/";

    //Om appen skall kopplas till en lokal databas
    private static final boolean usingLocal = true;

    private static final String base = usingLocal ? local : remote;

    // --- Bokningar

    //Hämta
    public static final String getCustomers = base + "customers";

    //Lägg till
    public static final String insertCustomer = base + "post/customers?customer=";


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

    // --- Bord

    //Hämta
    public static final String getTables = base + "dinnertables";


    // --- Kategorier

    //Hämta
    public static final String getCategories = base + "itemcategorys";


    // --- Inloggning

    //Testa:
    public static final String validateLogin = base + "users/login?username=";
    public static final String validateLoginPassword = "&password=";

    private DatabaseURL() {
    }
}
