package se.grupp1.antonsskafferi.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import se.grupp1.antonsskafferi.components.ScheduledTimesComponent;
import se.grupp1.antonsskafferi.popups.ChangeScheduledTimePopupFragment;
import se.grupp1.antonsskafferi.R;


public class ScheduleFragment extends Fragment {

    DialogFragment popup;
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
        //get hold of textview
        final TextView dateView  = root.findViewById(R.id.todaysDate);
        //set it as current date.
        dateView.setText(date_n);


        Calendar cal = Calendar.getInstance();

        /*Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", "A Test Event from android app");
        startActivity(intent);*/


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String Date = dayOfMonth + "-" + (month + 1) + "-" + year;
                dateView.setText(Date);
            }
        });

        /*root.findViewById(R.id.changeTimeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.addToBackStack(null);

                popup = new ChangeScheduledTimePopupFragment();

                popup.show(getChildFragmentManager(), "popup");
            }
        });*/

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout workList = view.findViewById(R.id.workList);

        ScheduledTimesComponent worker1 = new ScheduledTimesComponent(getContext(), "14:00 - 20:00");
        worker1.showChangeButton(true);
        worker1.addItem("Tim");
        workList.addView(worker1);

        ScheduledTimesComponent worker2 = new ScheduledTimesComponent(getContext(), "21:00 - 00:00");
        worker2.showChangeButton(true);
        worker2.addItem("Ylva");
        workList.addView(worker2);

    }

}
