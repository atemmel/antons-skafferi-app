package se.grupp1.antonsskafferi.data;

import org.json.JSONException;
import org.json.JSONObject;

public class BookingData {
    private int tableId;
    private String bookingAmount;
    private int customerId = -1;
    private String time;
    private String date;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNr;

    public BookingData(String firstName, String lastName, String bookingAmount, String phoneNr, String time, String date, String email, int tableId,  int customerId) {
        this.bookingAmount = bookingAmount;
        this.tableId = tableId;
        this.customerId = customerId;
        this.time = time;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNr = phoneNr;
    }

    public BookingData(String firstName, String lastName, String bookingAmount, String phoneNr, String time, String date, String email, int tableId) {
        this.bookingAmount = bookingAmount;
        this.tableId = tableId;
        this.time = time;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNr = phoneNr;
    }

    public void setTableId(int dinnerTableId) {
        this.tableId = dinnerTableId;
    }
    public int getTableId() {
        return tableId;
    }
    public void setBookingAmount(String bookingAmount) {this.bookingAmount = bookingAmount;}
    public String getBookingAmount() {
        return bookingAmount;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return time;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }
    public String getPhoneNr() {
        return phoneNr;
    }


    public String toJSONString() {
        JSONObject object = new JSONObject();

        try {
            object.put("firstname", firstName);
            object.put("lastname", lastName);
            object.put("sizeofcompany", bookingAmount);
            object.put("phone", phoneNr);
            object.put("bookingdate", date);
            object.put("bookingtime", time);
            object.put("email", email);
            object.put("dinnertable", tableId);
            if(customerId != -1){object.put("customerid", customerId);}
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }
}