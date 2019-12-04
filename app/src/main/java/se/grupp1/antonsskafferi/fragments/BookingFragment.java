package se.grupp1.antonsskafferi.fragments;

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

import java.util.Calendar;

import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;
import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.classes.BookingData;

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
                EditText BookingEmail =  root.findViewById(R.id.BookingEmail);
                TextView BookingTime =  root.findViewById(R.id.BookingTime);
                TextView BookingDate =  root.findViewById(R.id.BookingDate);

                String firstName = BookingFirstName.getText().toString();
                String lastName = BookingLastName.getText().toString();
                String peopleAmount = BookingPeopleAmount.getText().toString();
                String phoneNr = BookingPhoneNr.getText().toString();
                String email = BookingEmail.getText().toString();
                String time = BookingTime.getText().toString();
                String date = BookingDate.getText().toString();

                boolean emptyFields = false;

                if(firstName.isEmpty())
                {
                    emptyFields = true;
                    BookingFirstName.setError("Skriv in förnamn");
                } else BookingFirstName.setError(null);

                if(lastName.isEmpty())
                {
                    emptyFields = true;
                    BookingLastName.setError("Skriv in efternamn");
                } else BookingLastName.setError(null);

                if(peopleAmount.isEmpty())
                {
                    emptyFields = true;
                    BookingPeopleAmount.setError("Skriv in antal bokade platser");
                } else BookingPeopleAmount.setError(null);

                if(phoneNr.isEmpty())
                {
                    emptyFields = true;
                    BookingPhoneNr.setError("Skriv in ett teleNr");
                } else BookingPhoneNr.setError(null);

                if(time.isEmpty() || time.equals(getString(R.string.tid)))
                {
                    emptyFields = true;
                    BookingTime.setError("Skriv in bokad tid");
                } else BookingTime.setError(null);

                if(date.isEmpty() || date.equals(getString(R.string.datum)))
                {
                    emptyFields = true;
                    BookingDate.setError("Skriv in datum");
                } else BookingDate.setError(null);

                if(email.isEmpty())
                {
                    emptyFields = true;
                    BookingEmail.setError("Skriv in Email");
                } else BookingEmail.setError(null);


                BookingData data = new BookingData(
                        firstName,
                        lastName,
                        peopleAmount,
                        phoneNr,
                        time,
                        date,
                        email,
                        //TODO: Add this field to form
                        1);

                if(!emptyFields) {
                    System.out.println("Sent to backend");
                    sendToDatabase(data);
                }
            }

            });

        return root;
    }

    private void sendToDatabase(BookingData data) {
        HttpRequest.Response response = new HttpRequest.Response() {
            @Override
            public void processFinish(String output) {
                System.out.println(output);
            }
        };
        HttpRequest httpRequest = new HttpRequest(response);
        httpRequest.setRequestMethod("POST");
        String payload = data.toJSONString();
        System.out.println(payload);
        httpRequest.setPayload(payload);
        httpRequest.execute(DatabaseURL.insertCustomer);
    }


}
