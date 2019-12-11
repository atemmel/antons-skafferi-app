package se.grupp1.antonsskafferi.popups;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;
import se.grupp1.antonsskafferi.lib.StringFormatter;


public class ChangeScheduledTimePopup extends DialogFragment {

    private String user = "";

    public ChangeScheduledTimePopup(String user) {
        setUser(user);
    }

    public void setUser(String user){
        this.user = user;
    }

    /*
    public ChangeScheduledTimePopup(){}

    public static ChangeScheduledTimePopup newInstance()
    {
        ChangeScheduledTimePopup fragment = new ChangeScheduledTimePopup();

        return fragment;
    }*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.popup_change_sheduled_time, container, false);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_bg);

        root.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        final TextView chosenDate = root.findViewById(R.id.chosenDateView);

        root.findViewById(R.id.datePicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //TODO: Make sure that the string returned has constant length
                DatePickerDialog datepickerdialogview = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        chosenDate.setText(StringFormatter.formatDate(year + "-" + (month + 1) + "-" + day));
                    }
                }, year, month, day);
                datepickerdialogview.show();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        loadTimesToDropdown();

    }

    private void loadTimesToDropdown()
    {
        final Spinner dropdown = getView().findViewById(R.id.timeSpinner);

        HttpRequest httpRequest = new HttpRequest(new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status)
            {
                try
                {
                    JSONArray jsonArr = new JSONArray(output);

                    ArrayList<String> users = new ArrayList<String>();

                    for(int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject c = jsonArr.getJSONObject(i);

                        String start = c.getString("start");
                        String end = c.getString("end");

                        users.add(start+ "-" + end);
                    }

                    ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, users);
                    dropdown.setAdapter(adapter);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        httpRequest.setRequestMethod("GET");
        httpRequest.execute(DatabaseURL.getWorkingSchedule);
    }

}
