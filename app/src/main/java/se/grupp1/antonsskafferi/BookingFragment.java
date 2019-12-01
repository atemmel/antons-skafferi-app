package se.grupp1.antonsskafferi;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class BookingFragment extends Fragment {

    TextView mTv;
    Button mBtn;
    DatePickerDialog dpd;
    Calendar c;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_booking, container, false);


        mTv = root.findViewById(R.id.BookingDate);
        mBtn = root.findViewById(R.id.Datepicker);

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
                        mTv.setText(mYear + "-" + (mMonth+1) + "-" + mDay);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        Button SendBtn =  root.findViewById(R.id.SendBtn);
        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText BookingFirstName = v.findViewById(R.id.BookingFirstName);
                EditText BookingLastName = v.findViewById(R.id.BookingLastName);
                EditText BookingPeopleAmount =  v.findViewById(R.id.BookingPeopleAmount);
                EditText BookingPhoneNr =  v.findViewById(R.id.BookingPhoneNr);
                EditText BookingTime =  v.findViewById(R.id.BookingTime);
                EditText BookingDate =  v.findViewById(R.id.BookingDate);

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
                if(date.isEmpty())
                {
                    emptyFields = true;
                    BookingDate.setError("Skriv in datum");
                }

                //if(!emptyFields) sendToDatabase();

            }
        });

        return root;
    }
}
