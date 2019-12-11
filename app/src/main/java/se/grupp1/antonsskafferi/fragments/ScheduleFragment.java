package se.grupp1.antonsskafferi.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

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


public class ScheduleFragment extends Fragment {

    DialogFragment popup;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);

        CalendarView calendar = (CalendarView) root.findViewById(R.id.calendar); // get the reference of CalendarView
        calendar.setDate(System.currentTimeMillis()); // set selected date 22 May 2016 in milliseconds
        calendar.setShowWeekNumber(true);

        //create a date string.
        String date_n = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new Date());
        //get hold of textview
        final TextView dateView = root.findViewById(R.id.todaysDate);
        //set it as current date.
        dateView.setText(date_n);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                SimpleDateFormat monthParse = new SimpleDateFormat("MM");
                SimpleDateFormat monthDisplay = new SimpleDateFormat("MMMM");

                String Date = null;
                try {
                    Date = monthDisplay.format(monthParse.parse(Integer.toString(month))) + " " + dayOfMonth + ", " + year;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                dateView.setText(Date);
            }
        });

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadScheduledEvents();

        LinearLayout workList = view.findViewById(R.id.workList);
        //ArrayList<String> startingTimes = new ArrayList();

        /*ScheduledTimesComponent worker1 = new ScheduledTimesComponent(getContext(), "14:00", "20:00");
        worker1.showChangeButton(true);
        worker1.setName("Tim");
        workList.addView(worker1);
        //startingTimes.add(worker1.getStartTime());

        ScheduledTimesComponent worker2 = new ScheduledTimesComponent(getContext(), "20:00", "00:00");
        worker2.showChangeButton(true);
        worker2.setName("Ylva");
        workList.addView(worker2);

        ScheduledTimesComponent worker3 = new ScheduledTimesComponent(getContext(), "20:00",  "00:00");
        worker3.showChangeButton(false);
        worker3.setName("Anton");
        workList.addView(worker3);*/

    }

    public void loadScheduledEvents() {
        final LinearLayout workList = getView().findViewById(R.id.workList);

        HttpRequest httpRequest = new HttpRequest(new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status) {
                try {
                    JSONArray jsonArr = new JSONArray(output);

                    ArrayList<String> users = new ArrayList<String>();

                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject c = jsonArr.getJSONObject(i);

                        Integer workId = c.getInt("workingscheduleid");
                        String name = c.getString("date");
                        String startTime = c.getString("start");
                        String endTime = c.getString("end");

                        ScheduledTimesComponent scheduledEvent = new ScheduledTimesComponent(getContext(), startTime, endTime);
                        //TODO:If current user is the same person as the person of the scheduled event, don't show change button
                        scheduledEvent.showChangeButton(true);
                        //TODO:Get name instead of id and show at event.
                        scheduledEvent.setName(workId.toString());
                        workList.addView(scheduledEvent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        httpRequest.setRequestMethod("GET");
        httpRequest.execute(DatabaseURL.getWorkingSchedule);
    }
}