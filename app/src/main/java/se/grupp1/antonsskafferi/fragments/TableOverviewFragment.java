package se.grupp1.antonsskafferi.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import se.grupp1.antonsskafferi.components.TableCardComponent;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;
import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.popups.EditDinnerMenuPopup;


public class TableOverviewFragment extends Fragment
{
    public interface LoadingCallback
    {
        void finishedLoading();
    }

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

        final SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                loadTables(new LoadingCallback() {
                    @Override
                    public void finishedLoading() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        loadTables(new LoadingCallback() {
            @Override
            public void finishedLoading() {

            }
        });
    }

    private void loadTables(final LoadingCallback callback)
    {
        final GridLayout tableGrid = getView().findViewById(R.id.tableGrid);

        tableGrid.removeAllViews();

        HttpRequest request = new HttpRequest(new HttpRequest.Response()
        {
            @Override
            public void processFinish(String output, int status) {
            try
            {
                JSONArray jsonArr = new JSONArray(output);

                for(int i = 0; i < jsonArr.length(); i++)
                {
                    JSONObject c = jsonArr.getJSONObject(i);

                    final int tableId = c.getInt("dinnertableid");

                    tableGrid.addView(new TableCardComponent(getContext(), tableId, -1, TableCardComponent.Status.FREE, Navigation.findNavController(getView())));
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                checkForBookedTables();
                checkForInUseTables();

                callback.finishedLoading();
            }
            }
        });

        request.setRequestMethod("GET");
        request.execute(DatabaseURL.getTables);
    }

    private void checkForInUseTables()
    {
        final GridLayout tableGrid = getView().findViewById(R.id.tableGrid);

        for(int i = 0; i < tableGrid.getChildCount(); i++)
        {
            final WeakReference<TableCardComponent> tableCardRef = new WeakReference<>((TableCardComponent)tableGrid.getChildAt(i));

            HttpRequest.Response response = new HttpRequest.Response() {
                @Override
                public void processFinish(String output, int status)
                {
                    output = output.trim();

                    if(output.toLowerCase().equals("true"))
                    {
                        System.out.println("lägger till");
                        tableCardRef.get().setStatus(TableCardComponent.Status.OCCUPIED);
                    }
                }
            };


            HttpRequest request = new HttpRequest(response);
            request.setRequestMethod("GET");

            request.execute(DatabaseURL.getIfTableInUse + tableCardRef.get().getTableId());
        }
    }

    private void checkForBookedTables()
    {
        final GridLayout tableGrid = getView().findViewById(R.id.tableGrid);

        for(int i = 0; i < tableGrid.getChildCount(); i++)
        {
            final WeakReference<TableCardComponent> tableCardRef = new WeakReference<>((TableCardComponent)tableGrid.getChildAt(i));

            HttpRequest.Response response = new HttpRequest.Response() {
                @Override
                public void processFinish(String output, int status) {

                    try {
                        JSONArray jsonArr = new JSONArray(output);

                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject c = jsonArr.getJSONObject(i);

                            int customerId = c.getInt("customerid");

                            String string_date = c.getString("bookingdate");
                            String string_time = c.getString("bookingtime");

                            if (!isBookedNow(string_date, string_time)) return;

                            tableCardRef.get().setStatus(TableCardComponent.Status.BOOKED);
                            tableCardRef.get().setCustomerId(customerId);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        // callback.finishedLoading();
                    }
                }
            };


            HttpRequest request = new HttpRequest(response);
            request.setRequestMethod("GET");

            String requestURL = DatabaseURL.getBookingsForTable + tableCardRef.get().getTableId();

            request.execute(requestURL);
        }
    }

    private boolean isBookedNow(String date_string, String time_string)
    {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String combined_strings = date_string + " " + time_string;

        long milliseconds_date = 0;

        try {
            Date d = f.parse(combined_strings);
            milliseconds_date = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(!DateUtils.isToday(milliseconds_date)) return false;

        if(System.currentTimeMillis() >= milliseconds_date) return true;
        else                                                return false;
    }


}
