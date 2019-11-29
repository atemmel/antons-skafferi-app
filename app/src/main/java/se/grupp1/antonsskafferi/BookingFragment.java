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

                EditText BookingName = v.findViewById(R.id.BookingName);
                EditText BookingPeopleAmount =  v.findViewById(R.id.BookingPeopleAmount);
                EditText BookingPhoneNr =  v.findViewById(R.id.BookingPhoneNr);
                EditText BookingDate =  v.findViewById(R.id.BookingDate);

            }
        });

        return root;
    }
}
