package se.grupp1.antonsskafferi.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.components.BookingCardComponent;
import se.grupp1.antonsskafferi.data.BookingData;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;
import se.grupp1.antonsskafferi.lib.StringFormatter;


public class ViewBookingsFragment extends Fragment {

    public interface LoadingCallback
    {
        void finishedLoading();
    }

    //DatePicker
    private TextView mTv;
    private Button mBtn;
    private DatePickerDialog dpd;
    private Calendar c;
    //-------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_view_bookings, container, false);

        final SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);

        //DatePicker
        mTv = root.findViewById(R.id.CustomerDate);
        mBtn = root.findViewById(R.id.DatepickerBtn);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year =c.get(Calendar.YEAR);

                //TODO: Make sure that the string returned has constant length
                dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        mTv.setText(StringFormatter.formatDate(mYear + "-" + (mMonth + 1) + "-" + mDay));
                        loadBookings(new LoadingCallback() {
                            @Override
                            public void finishedLoading() {

                            }
                        });
                    }
                }, year, month, day);
                dpd.show();
            }
        });
        // End of DatePicker

        //Picking today's date
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String datum = simpleDateFormat.format(new Date());

        mTv.setText(datum);
        //

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                loadBookings(new LoadingCallback() {
                    @Override
                    public void finishedLoading()
                    {
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

        loadBookings(new LoadingCallback() {
            @Override
            public void finishedLoading() {

            }
        });
    }

    private void loadBookings(final LoadingCallback callback)
    {
        ((LinearLayout)getView().findViewById(R.id.bookingList)).removeAllViews();

        HttpRequest.Response response = new HttpRequest.Response()
        {
            @Override
            public void processFinish(String output, int status)
            {
                System.out.println(status);

                if(status != 200)
                {
                    Toast.makeText(getActivity(), "Kunde inte hämta bokningar. Felkod: " + status,
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

                try {
                JSONArray jsonArr = new JSONArray(output);

                //TODO ändra i till i = 0 när allan är borttagen från customers
                for (int i = 1; i < jsonArr.length(); i++) {
                    JSONObject obj = jsonArr.getJSONObject(i);


                    int tableId = obj.getJSONObject("dinnertable").getInt("dinnertableid");
                    String bookingAmount = obj.getString("sizeofcompany");
                    int customerId = obj.getInt("customerid");
                    String time = obj.getString("bookingtime");
                    String date = obj.getString("bookingdate");
                    String firstname = obj.getString("firstname");
                    String lastname = obj.getString("lastname");
                    String email = obj.getString("email");
                    String phoneNr = obj.getString("phone");

                    BookingData bookingData = new BookingData(firstname, lastname, bookingAmount, phoneNr, time, date, email, tableId, customerId);
                    BookingCardComponent booking = new BookingCardComponent(getContext(), bookingData);

                    if(date.equals(mTv.getText().toString())){

                        ((LinearLayout)getView().findViewById(R.id.bookingList)).addView(booking);

                    }
                }
            }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    callback.finishedLoading();
                }
            }
        };

        HttpRequest httpRequest = new HttpRequest(response);
        httpRequest.setRequestMethod("GET");

        httpRequest.execute(DatabaseURL.getCustomers);
    }
}
