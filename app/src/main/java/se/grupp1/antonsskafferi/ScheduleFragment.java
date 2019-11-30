package se.grupp1.antonsskafferi;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ScheduleFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_schedule);

        CalendarView calendar = (CalendarView) root.findViewById(R.id.calendar); // get the reference of CalendarView
        calendar.setDate(System.currentTimeMillis()); // set selected date 22 May 2016 in milliseconds
        calendar.setShowWeekNumber(true);

        //create a date string.
        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        //get hold of textview.
        TextView date  = (TextView) root.findViewById(R.id.todaysDate);
        //set it as current date.
        date.setText(date_n);


        Calendar cal = Calendar.getInstance();
        /*Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", "A Test Event from android app");
        startActivity(intent);*/

        return root;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }


}
