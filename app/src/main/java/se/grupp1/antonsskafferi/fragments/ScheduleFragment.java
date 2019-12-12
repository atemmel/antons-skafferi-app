package se.grupp1.antonsskafferi.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import se.grupp1.antonsskafferi.components.ScheduledTimesComponent;
import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;
import se.grupp1.antonsskafferi.popups.BookingOptionsPopup;


public class ScheduleFragment extends Fragment {

    interface Callback {
        void getUserName(String username);
    }

    interface LoadingCallback
    {
        void finishedLoading();
    }

    DialogFragment popup;
    private String date = "";
    private String workScheduleId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);

        CalendarView calendar = (CalendarView) root.findViewById(R.id.calendar); // get the reference of CalendarView
        calendar.setDate(System.currentTimeMillis()); // set selected date 22 May 2016 in milliseconds
        calendar.setShowWeekNumber(true);

        //create a date string.
        String date_n = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new Date());
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        //get hold of textview
        final TextView dateView = root.findViewById(R.id.todaysDate);
        //set it as current date.
        dateView.setText(date_n);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                month = month + 1;
                String old_date = year + "-" + month + "-" + day;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");

                try {
                    Date d = sdf.parse(old_date);
                    sdf.applyPattern("yyyy-MM-dd");
                    date = sdf.format(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat monthParse = new SimpleDateFormat("MM");
                SimpleDateFormat monthDisplay = new SimpleDateFormat("MMMM");

                String Date = null;
                try {
                    Date = monthDisplay.format(monthParse.parse(Integer.toString(month))) + " " + day + ", " + year;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                loadScheduledEvents(new LoadingCallback() {
                    @Override
                    public void finishedLoading() {

                    }
                });

                dateView.setText(Date);
            }
        });


        final SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                loadScheduledEvents(new LoadingCallback() {
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadScheduledEvents(new LoadingCallback() {
            @Override
            public void finishedLoading() {
            }
        });
    }

    public void loadScheduledEvents(final LoadingCallback callback) {
        final LinearLayout workList = getView().findViewById(R.id.workList);

        workList.removeAllViews();

        HttpRequest httpRequest = new HttpRequest(new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status) {
                try {
                    JSONArray jsonArr = new JSONArray(output);

                    ArrayList<String> users = new ArrayList<String>();

                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject c = jsonArr.getJSONObject(i);

                        Integer workId = c.getInt("workingscheduleid");
                        String dateFromDB = c.getString("date");
                        String startTime = c.getString("start");
                        String endTime = c.getString("end");

                        final ScheduledTimesComponent scheduledEvent = new ScheduledTimesComponent(getContext(), startTime, endTime, workId.toString());

                        //TODO:If current user is the same person as the person of the scheduled event, don't show change button
                        scheduledEvent.showChangeButton(true);

                        //TODO:Get name instead of id and show at event.
                        //scheduledEvent.setName(workId.toString());
                        workScheduleId = workId.toString();

                        getNameOfWorker(new Callback() {
                            @Override
                            public void getUserName(String username) {
                                scheduledEvent.setName(username);
                            }
                        });

                        workList.addView(scheduledEvent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally
                {
                    callback.finishedLoading();
                }
            }
        });

        httpRequest.setRequestMethod("GET");
        httpRequest.execute(DatabaseURL.getScheduleByDate + date);
    }

    private void getNameOfWorker(final Callback callback)
    {
        HttpRequest httpRequest = new HttpRequest(new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status) {
                try {
                    output = output.trim();
                    callback.getUserName(output);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        httpRequest.setRequestMethod("GET");
        httpRequest.execute(DatabaseURL.getNameByWorkscheduleId + workScheduleId);
    }

}