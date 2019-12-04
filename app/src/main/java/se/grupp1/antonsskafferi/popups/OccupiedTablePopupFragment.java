package se.grupp1.antonsskafferi.popups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.fragments.TableOverviewFragment;

public class OccupiedTablePopupFragment extends DialogFragment
{
    public interface Callback
    {
        enum OptionClicked
        {
            TAKE_ORDER,
            WIPE_TABLE,
            SHOW_BILL
        }

        void clicked(OptionClicked optionClicked);
    }

    private Callback callback;

    public OccupiedTablePopupFragment(Callback callback) {
        this.callback = callback;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_occupied_table_popup, container, false);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_bg);

        v.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        v.findViewById(R.id.takeOrderButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.clicked(Callback.OptionClicked.TAKE_ORDER);
                dismiss();
            }
        });

        v.findViewById(R.id.clearTableButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.clicked(Callback.OptionClicked.WIPE_TABLE);
                dismiss();
            }
        });

        return v;
    }

}
