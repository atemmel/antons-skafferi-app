package se.grupp1.antonsskafferi.components;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.fragments.TableOverviewFragment;
import se.grupp1.antonsskafferi.popups.BookedTablePopupFragment;
import se.grupp1.antonsskafferi.popups.FreeTablePopupFragment;
import se.grupp1.antonsskafferi.popups.MultilineTextPopup;
import se.grupp1.antonsskafferi.popups.OccupiedTablePopupFragment;

public class TableCardComponent extends CardView
{
    public enum Status
    {
        BOOKED,
        FREE,
        OCCUPIED
    }

    private Status status;

    final NavController navController;

    final int tableId;

    public TableCardComponent(Context context, int tableId, Status status, NavController navController)
    {
        super(context);

        this.navController = navController;
        this.tableId = tableId;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f));

        gridParams.setMargins(16, 0,16,32);

        setLayoutParams(gridParams);

        this.setRadius(32f);
        this.setCardElevation(16f);

        requestLayout();

        inflater.inflate(R.layout.component_table_card, this, true);

        TextView tableText = findViewById(R.id.tableId);

        tableText.setText(Integer.toString(tableId));

        tableText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });

        setStatus(status);
    }

    private void setStatus(Status status)
    {
        this.status = status;

        switch(status)
        {
            case FREE:
                setCardBackgroundColor(getResources().getColor(R.color.freeTableColor));
                break;
            case BOOKED:
                setCardBackgroundColor(getResources().getColor(R.color.bookedTableColor));
                break;
            case OCCUPIED:
                setCardBackgroundColor(getResources().getColor(R.color.occupiedTableColor));
                break;
        }
    }

    private void showPopup()
    {
        FragmentActivity parent = (FragmentActivity)getContext();
        FragmentTransaction ft = parent.getSupportFragmentManager().beginTransaction();

        String tag = "dialog";

        switch(status)
        {
            case FREE: {
                FreeTablePopupFragment popup = FreeTablePopupFragment.newInstance(new FreeTablePopupFragment.Callback() {
                    @Override
                    public void clicked(OptionClicked optionClicked) {
                        switch(optionClicked)
                        {
                            case PLACE_CUSTOMER:
                                setStatus(Status.OCCUPIED);
                                break;
                        }
                    }
                });
                popup.show(ft, tag);
                break;
            }
            case BOOKED: {
                BookedTablePopupFragment popup = BookedTablePopupFragment.newInstance();
                popup.show(ft, tag);
                break;
            }
            case OCCUPIED: {
                OccupiedTablePopupFragment popup = new OccupiedTablePopupFragment(new OccupiedTablePopupFragment.Callback() {
                    @Override
                    public void clicked(OptionClicked optionClicked) {
                        switch(optionClicked)
                        {
                            case TAKE_ORDER:
                                Bundle args = new Bundle();
                                args.putInt("tableId", tableId);

                                navController.navigate(R.id.navigation_new_order, args);
                                break;
                            case WIPE_TABLE:
                                setStatus(Status.FREE);
                                break;
                            case SHOW_BILL:
                                break;
                        }
                    }
                });
                popup.show(ft, tag);
                break;
            }
        }
    }

}
