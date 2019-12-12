package se.grupp1.antonsskafferi.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.popups.ChangeScheduledTimePopup;

public class ScheduledTimesComponent extends CardView
{
    private boolean showChangeTimeButton = false;
    private String username = "";
    private String startTime = "";
    private String endTime = "";
    private String date = "";
    private String workId = "";

    public ScheduledTimesComponent(Context context)
    {
        super(context);
    }

    public ScheduledTimesComponent(Context context, String startTime, String endTime, String workId)
    {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.component_schedule_event, this, true);

        setStartTime(startTime);
        setEndTime(endTime);
        setWorkId(workId);
        setDate(date);

        ((TextView) findViewById(R.id.scheduleTime)).setText(startTime + "-" + endTime);

        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(0, 16, 0, 0);
        setLayoutParams(params);
        requestLayout();



        showChangeButton(showChangeTimeButton);


        findViewById(R.id.changeTimeButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentActivity parent = (FragmentActivity)getContext();
                FragmentTransaction ft = parent.getSupportFragmentManager().beginTransaction();

                String tag = "dialog";

                ChangeScheduledTimePopup popup = new ChangeScheduledTimePopup(getName(), getStartTime(), getEndTime(), getWorkId());

                popup.show(ft, tag);
            }
        });
    }

    public void showChangeButton(boolean readyStatus)
    {
        this.showChangeTimeButton = readyStatus;

        Button changeButton = findViewById(R.id.changeTimeButton);

        changeButton.setVisibility(View.VISIBLE);

        if(readyStatus)
        {
            changeButton.setVisibility(View.VISIBLE);
        }
        else
        {
            changeButton.setVisibility(View.INVISIBLE);
        }
    }

    public void setName(String username)
    {
        this.username = username;

        SharedPreferences prefs = getContext().getSharedPreferences("loginProfile", Context.MODE_PRIVATE);
        String current_user = prefs.getString("username", "");

        if(current_user == username)
        {
            showChangeButton(false);

            TextView timeText = findViewById(R.id.scheduleTime);
            timeText.setBackgroundColor(getResources().getColor(R.color.white));
        }

        LinearLayout itemsList = findViewById(R.id.scheduleName);

        TextView textView = new TextView(getContext());
        textView.setText(username);
        textView.setTextAppearance(getContext(), android.R.style.TextAppearance_Medium);
        textView.setPadding(8, 0,0,0);

        itemsList.addView(textView);
    }

    public String getName()
    {
        return username;
    }

    public void setWorkId(String workId){
        this.workId = workId;
    }

    public String getWorkId(){
        return workId;
    }

    public void setStartTime(String startTime){

        this.startTime = startTime;
    }

    public String getStartTime(){
        return startTime;
    }

    public void setEndTime(String endTime){

        this.endTime = endTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }
}