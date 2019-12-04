package se.grupp1.antonsskafferi.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.Reference;

import se.grupp1.antonsskafferi.components.TableCardComponent;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;
import se.grupp1.antonsskafferi.R;


public class TableOverviewFragment extends Fragment
{
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
        final GridLayout tableGrid = (GridLayout) getView().findViewById(R.id.tableGrid);

        HttpRequest request = new HttpRequest(new HttpRequest.Response()
        {
            @Override
            public void processFinish(String output) {
                try
                {
                    JSONArray jsonArr = new JSONArray(output);

                    for(int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject c = jsonArr.getJSONObject(i);

                        int id = c.getInt("dinnertableid");

                        tableGrid.addView(new TableCardComponent(getContext(), id, TableCardComponent.Status.FREE, Navigation.findNavController(getView())));
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        request.setRequestMethod("GET");

        request.execute(DatabaseURL.getTables);
    }

    public void newOrder()
    {
        //NavController navController =
        //navController.navigate(R.id.navigation_new_order);
    }
}
