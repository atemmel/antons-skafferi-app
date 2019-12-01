package se.grupp1.antonsskafferi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

public class BookedTablePopupFragment extends DialogFragment
{
    public BookedTablePopupFragment() {}


    public static BookedTablePopupFragment newInstance()
    {
        BookedTablePopupFragment fragment = new BookedTablePopupFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_booked_table_popup, container, false);

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
                ((TableOverviewFragment) getParentFragment()).setTable4Booked();
                dismiss();
            }
        });

        v.findViewById(R.id.clearTableButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TableOverviewFragment) getParentFragment()).setTable4Unbooked();
                dismiss();
            }
        });

        return v;
    }

}