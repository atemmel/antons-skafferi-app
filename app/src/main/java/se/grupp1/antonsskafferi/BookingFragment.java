package se.grupp1.antonsskafferi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class BookingFragment extends Fragment {

    //DatePicker
    private TextView mTv;
    private Button mBtn;
    private DatePickerDialog dpd;
    private Calendar c;
    //-------

    //Timepicker
    private TextView tv;
    private Button Btn;
    private Calendar currentTime;
    private int hour, minute;
    private String format;
    //-------


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
        final View root = inflater.inflate(R.layout.fragment_booking, container, false);

        //TimePicker
        tv = root.findViewById(R.id.BookingTime);
        Btn = root.findViewById(R.id.BtnTimePicker);

        currentTime = Calendar.getInstance();

        hour = currentTime.get(Calendar.HOUR_OF_DAY);
        minute = currentTime.get(Calendar.MINUTE);

        //tv.setText(hour + ":" + minute);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        tv.setText(hour + ":" + minute);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });
        //End of TimePicker

        //DatePicker
        mTv = root.findViewById(R.id.BookingDate);
        mBtn = root.findViewById(R.id.BtnDatePicker);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year =c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        mTv.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
                    }
                }, year, month, day);
                dpd.show();
            }
        });
        // End of DatePicker

        Button SendBtn =  root.findViewById(R.id.SendBtn);
        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText BookingFirstName = root.findViewById(R.id.BookingFirstName);
                EditText BookingLastName = root.findViewById(R.id.BookingLastName);
                EditText BookingPeopleAmount =  root.findViewById(R.id.BookingPeopleAmount);
                EditText BookingPhoneNr =  root.findViewById(R.id.BookingPhoneNr);
                TextView BookingTime =  root.findViewById(R.id.BookingTime);
                TextView BookingDate =  root.findViewById(R.id.BookingDate);

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
                if(time.isEmpty() || time.equals(getString(R.string.tid)))
                {
                    emptyFields = true;
                    BookingTime.setError("Skriv in bokad tid");
                }
                if(date.isEmpty() || date.equals(getString(R.string.datum)))
                {

                    emptyFields = true;
                    BookingDate.setError("Skriv in datum");
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
            object.put("dinnertable", data.dinnerTableId);

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
