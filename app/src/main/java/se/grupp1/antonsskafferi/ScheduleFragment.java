package se.grupp1.antonsskafferi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

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

        return root;

    }
}
