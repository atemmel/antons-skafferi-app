package se.grupp1.antonsskafferi.popups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.data.BookingData;


public final class BookingOptionsPopup extends DialogFragment {


    private BookingData itemData;

    Callback callback;

    public interface Callback
    {
        void onChanged(BookingData itemData);
    }

    public BookingOptionsPopup(Callback callback){
        this.callback = callback;
    }

    public BookingOptionsPopup(BookingData itemData, Callback callback) {

        this.itemData = itemData;
        this.callback = callback;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.popup_bookings_options, container, true);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        EditText bookingFirstName = view.findViewById(R.id.BookingFirstName);
        EditText bookingLastName = view.findViewById(R.id.BookingLastName);
        EditText bookingPeopleAmount =  view.findViewById(R.id.BookingPeopleAmount);
        EditText bookingPhoneNr =  view.findViewById(R.id.BookingPhoneNr);
        EditText bookingEmail =  view.findViewById(R.id.BookingEmail);
        TextView bookingTime =  view.findViewById(R.id.BookingTime);
        TextView bookingDate =  view.findViewById(R.id.BookingDate);
        TextView hiddenCustomerId = view.findViewById(R.id.hiddenCustomerId);
        TextView hiddenTableId = view.findViewById(R.id.hiddenTableId);

        bookingFirstName.setText(itemData.getFirstName());
        bookingLastName.setText(itemData.getLastName());
        bookingPeopleAmount.setText(itemData.getBookingAmount());
        bookingPhoneNr.setText(itemData.getPhoneNr());
        bookingEmail.setText(itemData.getEmail());
        bookingTime.setText(itemData.getTime());
        bookingDate.setText(itemData.getDate());
        hiddenCustomerId.setText(""+itemData.getCustomerId());
        hiddenTableId.setText(""+itemData.getTableId());



        //final FragmentManager fm = getFragmentManager();
       // final FragmentTransaction ft = fm.beginTransaction();

        view.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment f = getFragmentManager().findFragmentById(R.id.fragmentPlaceholder);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }



}




