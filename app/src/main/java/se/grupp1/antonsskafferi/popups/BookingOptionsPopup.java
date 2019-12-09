package se.grupp1.antonsskafferi.popups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import se.grupp1.antonsskafferi.R;

public class BookingOptionsPopup extends DialogFragment {
    private static final BookingOptionsPopup ourInstance = new BookingOptionsPopup();

    public static BookingOptionsPopup getInstance() {
        return ourInstance;
    }

    public BookingOptionsPopup() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.popup_bookings_options, container, false);

        v.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }
}



