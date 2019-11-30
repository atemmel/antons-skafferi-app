package se.grupp1.antonsskafferi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Locale;

public class BookingFragment extends Fragment {
    class BookingData {
         String firstName;
         String lastName;
         String peopleAmount;
         String phoneNr;
         String time;
         String date;
         String email;
         int dinnerTableId;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_booking, container, false);



        Button SendBtn =  root.findViewById(R.id.SendBtn);
        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText BookingFirstName = getView().findViewById(R.id.BookingFirstName);
                EditText BookingLastName = getView().findViewById(R.id.BookingLastName);
                EditText BookingPeopleAmount =  getView().findViewById(R.id.BookingPeopleAmount);
                EditText BookingPhoneNr =  getView().findViewById(R.id.BookingPhoneNr);
                EditText BookingTime =  getView().findViewById(R.id.BookingTime);
                EditText BookingDate =  getView().findViewById(R.id.BookingDate);

                String firstName = BookingFirstName.getText().toString();
                String lastName = BookingLastName.getText().toString();
                String peopleAmount = BookingPeopleAmount.getText().toString();
                String phoneNr = BookingPhoneNr.getText().toString();
                String time = BookingTime.getText().toString();
                String date = BookingDate.getText().toString();

                boolean emptyFields = false;

                if(firstName.isEmpty())
                {

                    emptyFields = true;
                    BookingFirstName.setError("Skriv in förnamn");
                }
                if(lastName.isEmpty())
                {

                    emptyFields = true;
                    BookingLastName.setError("Skriv in efternamn");
                }
                if(peopleAmount.isEmpty())
                {

                    emptyFields = true;
                    BookingPeopleAmount.setError("Skriv in antal bokade platser");
                }
                if(phoneNr.isEmpty())
                {

                    emptyFields = true;
                    BookingPhoneNr.setError("Skriv in ett teleNr");
                }
                if(time.isEmpty())
                {

                    emptyFields = true;
                    BookingTime.setError("Skriv in bokad tid");
                }

                if(!dateTimeIsValid(time, "HH:mm")){

                    emptyFields = true;
                    BookingTime.setError("Ogiltig tid");
                }
                if(date.isEmpty())
                {

                    emptyFields = true;
                    BookingDate.setError("Skriv in datum");
                }
                if(!dateTimeIsValid(date, "yyyyMMdd")) {   //TODO: This check is not working as intended

                    emptyFields = true;
                    BookingDate.setError("Ogiltigt datum");
                }


                BookingData data = new BookingData();
                data.date = date;
                data.firstName = firstName;
                data.lastName = lastName;
                data.peopleAmount = peopleAmount;
                data.phoneNr = phoneNr;
                data.time = time;
                data.email = "a@b.c";   //TODO: Add this field to form
                data.dinnerTableId = 1; //TODO: Add this field to form


                if(!emptyFields) {
                    System.out.println("Sent to backend");
                    sendToDatabase(data);
                }
            }

            });

        return root;
    }

    private boolean dateTimeIsValid(String str, String pattern) {   //TODO: Remove this and replace it with actual date/time pickers
        DateFormat timeFormat = new SimpleDateFormat(pattern, Locale.GERMAN);
        try {
            Date dummyDate = timeFormat.parse(str);
        }
        catch (ParseException e) {
            return false;
        }
        return true;
    }

    private void sendToDatabase(BookingData data) {
        final String urlString = "http://10.0.2.2:8080/post/customers?customer=";  //TODO: Move to a global constant of some sorts

        JSONObject object = new JSONObject();

        try {
            object.put("firstname", data.firstName);
            object.put("lastname", data.lastName);
            object.put("sizeofcompany", data.peopleAmount);
            object.put("phone", data.phoneNr);
            object.put("bookingdate", data.date);
            object.put("bookingtime", data.time);
            object.put("email", data.email);
            object.put("dinnertableid", data.dinnerTableId);

            HttpRequest.Response response = new HttpRequest.Response() {
                @Override
                public void processFinish(String output) {
                    System.out.println(output);
                }
            };
            HttpRequest httpRequest = new HttpRequest(response);
            httpRequest.setRequestMethod("POST");
            System.out.println(object.toString());
            httpRequest.setPayload(object.toString());
            httpRequest.execute(urlString);
        } catch(JSONException e) {
            e.printStackTrace();
        }


    }


}
