package se.grupp1.antonsskafferi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class BookingFragment extends Fragment {
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
