package se.grupp1.antonsskafferi.classes;

import org.json.JSONException;
import org.json.JSONObject;

public class BookingData {
    public String firstName;
    public String lastName;
    public String peopleAmount;
    public String phoneNr;
    public String time;
    public String date;
    public String email;
    public int dinnerTableId;

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