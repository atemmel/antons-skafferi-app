package se.grupp1.antonsskafferi.components;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.popups.ChangeScheduledTimePopupFragment;

public class ScheduledTimesComponent extends CardView
{
    private boolean showChangeTimeButton = false;

    public ScheduledTimesComponent(Context context, String workingTime)
    {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.component_schedule_event, this, true);

        ((TextView) findViewById(R.id.scheduleTime)).setText(workingTime);

        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(0, 16, 0, 0);
        setLayoutParams(params);
        requestLayout();

        showChangeButton(showChangeTimeButton);


        //TODO: open popup dialogfragment window in cardview when pressing change button
        findViewById(R.id.changeTimeButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ChangeScheduledTimePopupFragment popup = new ChangeScheduledTimePopupFragment();
                System.out.println("popup");
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
            changeButton.setVisibility(View.GONE);
        }
    }

    public void addItem(String name)
    {
        LinearLayout itemsList = (LinearLayout) findViewById(R.id.scheduleName);

        TextView textView = new TextView(getContext());
        textView.setText(name);
        textView.setTextAppearance(getContext(), android.R.style.TextAppearance_Material_Body1);
        textView.setPadding(8, 0,0,0);

        itemsList.addView(textView);
    }
}