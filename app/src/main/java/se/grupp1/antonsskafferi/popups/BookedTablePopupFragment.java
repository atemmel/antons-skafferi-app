package se.grupp1.antonsskafferi.popups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import se.grupp1.antonsskafferi.R;

public class BookedTablePopupFragment extends DialogFragment
{
    public interface Callback
    {
        enum OptionClicked
        {
            PLACE_CUSTOMER,
            SHOW_BOOKING,
            REMOVE_BOOKING
        }

        void clicked(OptionClicked optionClicked);
    }

    Callback callback;

    public BookedTablePopupFragment(Callback callback)
    {
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.popup_booked_table, container, false);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_bg);

        v.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        v.findViewById(R.id.placeCustomerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.clicked(Callback.OptionClicked.PLACE_CUSTOMER);
                dismiss();
            }
        });

        v.findViewById(R.id.showBookingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.clicked(Callback.OptionClicked.SHOW_BOOKING);
                dismiss();
            }
        });

        v.findViewById(R.id.removeBookingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.clicked(Callback.OptionClicked.REMOVE_BOOKING);
                dismiss();
            }
        });

        return v;
    }

}