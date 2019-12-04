package se.grupp1.antonsskafferi.classes;

import org.json.JSONException;
import org.json.JSONObject;

public class BookingData {
    private String firstName;
    private String lastName;
    private String peopleAmount;
    private String phoneNr;
    private String time;
    private String date;
    private String email;
    private int dinnerTableId;

    public BookingData(String firstName, String lastName, String peopleAmount, String phoneNr, String time, String date, String email, int dinnerTableId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.peopleAmount = peopleAmount;
        this.phoneNr = phoneNr;
        this.time = time;
        this.date = date;
        this.email = email;
        this.dinnerTableId = dinnerTableId;
    }

    public String toJSONString() {
        JSONObject object = new JSONObject();

        try {
            object.put("firstname", firstName);
            object.put("lastname", lastName);
            object.put("sizeofcompany", peopleAmount);
            object.put("phone", phoneNr);
            object.put("bookingdate", date);
            object.put("bookingtime", time);
            object.put("email", email);
            object.put("dinnertable", dinnerTableId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }
}