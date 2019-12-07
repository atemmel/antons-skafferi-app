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

import java.text.ParseException;
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

        CalendarView calendar = (CalendarView) root.findViewById(R.id.calendar); // get the reference of CalendarView
        calendar.setDate(System.currentTimeMillis()); // set selected date 22 May 2016 in milliseconds
        calendar.setShowWeekNumber(true);

        //create a date string.
        String date_n = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new Date());
        //get hold of textview
        final TextView dateView  = root.findViewById(R.id.todaysDate);
        //set it as current date.
        dateView.setText(date_n);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month = month +1;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout workList = view.findViewById(R.id.workList);

        ScheduledTimesComponent worker1 = new ScheduledTimesComponent(getContext(), "14:00 - 20:00");
        worker1.showChangeButton(true);
        worker1.addItem("Tim");
        workList.addView(worker1);

        ScheduledTimesComponent worker2 = new ScheduledTimesComponent(getContext(), "20:00 - 00:00");
        worker2.showChangeButton(true);
        worker2.addItem("Ylva");
        workList.addView(worker2);

        ScheduledTimesComponent worker3 = new ScheduledTimesComponent(getContext(), "20:00 - 00:00");
        worker3.showChangeButton(false);
        worker3.addItem("Anton");
        workList.addView(worker3);

    }

}
