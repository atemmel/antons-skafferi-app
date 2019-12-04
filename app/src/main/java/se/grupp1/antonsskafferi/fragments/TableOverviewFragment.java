package se.grupp1.antonsskafferi.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import se.grupp1.antonsskafferi.components.TableCardComponent;
import se.grupp1.antonsskafferi.popups.BookedTablePopupFragment;
import se.grupp1.antonsskafferi.popups.OccupiedTablePopupFragment;
import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.popups.UnbookedTablePopupFragment;


public class TableOverviewFragment extends Fragment {

    DialogFragment popup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_table_overview, container, false);

        root.findViewById(R.id.addBookingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.navigation_booking);
            }
        });

        root.findViewById(R.id.viewBookingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.navigation_view_bookings);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        loadTables();

    }

    private void loadTables()
    {
        GridLayout tableGrid = (GridLayout) getView().findViewById(R.id.tableGrid);

        tableGrid.addView(new TableCardComponent(getContext(), "7"));
        tableGrid.addView(new TableCardComponent(getContext(), "7"));
        tableGrid.addView(new TableCardComponent(getContext(), "7"));
        tableGrid.addView(new TableCardComponent(getContext(), "7"));
    }

    public void newOrder()
    {
        popup.dismiss();

        NavController navController = Navigation.findNavController(getView());
        navController.navigate(R.id.navigation_new_order);
    }
}
