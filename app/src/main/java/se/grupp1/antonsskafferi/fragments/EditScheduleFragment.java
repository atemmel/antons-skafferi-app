package se.grupp1.antonsskafferi.fragments;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;
import se.grupp1.antonsskafferi.lib.StringFormatter;

public class EditScheduleFragment extends Fragment
{
    private Map<String, Integer> employee_map = new HashMap<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_edit_schedule, container, false);


        SimpleDateFormat sdf = new SimpleDateFormat(StringFormatter.date_format);
        String today = sdf.format(new Date());

        ((TextView)root.findViewById(R.id.dateLabel)).setText(today);

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        String current_time = new SimpleDateFormat(StringFormatter.time_format).format(now);

        ((TextView)root.findViewById(R.id.startTimeLabel)).setText(current_time);

        calendar.add(Calendar.HOUR_OF_DAY, +1);

        String one_hour_forward = new SimpleDateFormat(StringFormatter.time_format).format(calendar.getTime());

        ((TextView)root.findViewById(R.id.endTimeLabel)).setText(one_hour_forward);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        loadUsersToDropdown();
        addListenerOnSpinnerEmployeeSelection();

        view.findViewById(R.id.datePickerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                TextView dateLabel = getView().findViewById(R.id.dateLabel);

                String date = dateLabel.getText().toString();

                String[] splitDate = date.split("-");

                int year = Integer.parseInt(splitDate[0]);
                int month = Integer.parseInt(splitDate[1]) - 1;
                int day = Integer.parseInt(splitDate[2]);

                DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day)
                    {
                        String dateString = StringFormatter.formatDate(year + "-" + (month + 1) + "-" + day);

                        SimpleDateFormat sdf = new SimpleDateFormat(StringFormatter.date_format);

                        try
                        {
                            Date date = sdf.parse(dateString);

                            if(date.before(new Date()))
                            {
                                Toast.makeText(getActivity(), "Datumet har passerat, välj ett datum i framtiden.",
                                        Toast.LENGTH_LONG
                                ).show();

                                return;
                            }
                        }
                        catch (ParseException e)
                        {
                            e.printStackTrace();
                        }

                        TextView dateText = getView().findViewById(R.id.dateLabel);

                        dateText.setText(dateString);
                    }
                }, year, month, day);

                datepickerdialog.show();
            }
        });

        view.findViewById(R.id.startTimePickerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView startTimeLabel = getView().findViewById(R.id.startTimeLabel);

                String startTime = startTimeLabel.getText().toString();

                String[] splitStartTime = startTime.split(":");

                int hour = Integer.parseInt(splitStartTime[0]);
                int minute = Integer.parseInt(splitStartTime[1]);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {

                        String time = StringFormatter.formatTime(hour + ":" + minute);

                        TextView endTime = getView().findViewById(R.id.endTimeLabel);

                        if(timeIsBefore(endTime.getText().toString(), time))
                        {
                            Calendar calendar = Calendar.getInstance();

                            calendar.set(Calendar.HOUR_OF_DAY, hour);
                            calendar.set(Calendar.MINUTE, minute);

                            calendar.add(Calendar.HOUR_OF_DAY, + 1);

                            String one_hour_forward = new SimpleDateFormat(StringFormatter.time_format).format(calendar.getTime());

                            endTime.setText(one_hour_forward);
                        }

                        TextView timeText = getView().findViewById(R.id.startTimeLabel);

                        timeText.setText(time);
                    }
                }, hour, minute, true);

                timePickerDialog.show();
            }
        });

        view.findViewById(R.id.endTimePickerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView endTimeLabel = getView().findViewById(R.id.endTimeLabel);

                String endTime = endTimeLabel.getText().toString();

                String[] splitEndTime = endTime.split(":");

                int hour = Integer.parseInt(splitEndTime[0]);
                int minute = Integer.parseInt(splitEndTime[1]);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {

                        String time = StringFormatter.formatTime(hour + ":" + minute);

                        TextView startTime = getView().findViewById(R.id.startTimeLabel);

                        if(timeIsBefore(time, startTime.getText().toString()))
                        {
                            Toast.makeText(getActivity(), "Du kan inte välja ett slutdatum tidigare än startdatumet.",
                                    Toast.LENGTH_LONG
                            ).show();

                            return;
                        }

                        TextView timeText = getView().findViewById(R.id.endTimeLabel);

                        timeText.setText(time);

                    }
                }, hour, minute, true);

                timePickerDialog.show();
            }
        });

        view.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                postEmployeeSchedule();
            }
        });
    }

    private boolean timeIsBefore(String time_before, String time_after)
    {
        SimpleDateFormat parser = new SimpleDateFormat(StringFormatter.time_format);

        try {
            Date date_before = parser.parse(time_before);
            Date date_after = parser.parse(time_after);

            return date_after.after(date_before);

        } catch (ParseException e) {
            e.printStackTrace();

            return false;
        }
    }

    private void loadUsersToDropdown()
    {
        final Spinner dropdown = getView().findViewById(R.id.employeeDropdown);

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

                        String name = c.getJSONObject("user").getString("username");
                        int id = c.getInt("employeeid");

                        employee_map.put(name, id);

                        users.add(name);
                    }

                    ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, users);
                    dropdown.setAdapter(adapter);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        httpRequest.setRequestMethod("GET");
        httpRequest.execute(DatabaseURL.getEmployees);
    }

    public void addListenerOnSpinnerEmployeeSelection() {
        Spinner dropdown = getView().findViewById(R.id.employeeDropdown);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object user = parent.getItemAtPosition(pos);

                System.out.println(user.toString());
                //loadEmployees(user.toString());

            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void postEmployeeSchedule()
    {
        HttpRequest.Response response = new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status) {
                if(status != 200)
                {
                    Toast.makeText(getActivity(), "Kunde inte skicka till databsen, var vänlig försök igen. Felkod: " + status,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        };

        Spinner employeeSpinner = getView().findViewById(R.id.employeeDropdown);
        TextView dateText = getView().findViewById(R.id.dateLabel);
        TextView startTimeText = getView().findViewById(R.id.startTimeLabel);
        TextView endTimeText = getView().findViewById(R.id.endTimeLabel);

        int employeeid = employee_map.get(employeeSpinner.getSelectedItem().toString());

        String date = dateText.getText().toString();
        String startTime = startTimeText.getText().toString();
        String endTime = endTimeText.getText().toString();


        HttpRequest request = new HttpRequest(response);
        request.setRequestMethod("POST");
        request.execute(DatabaseURL.postEmployeeScheduleBase + employeeid +
                        DatabaseURL.postEmplyeeScheduleDate + date +
                        DatabaseURL.postEmployeeScheduleStart + startTime +
                        DatabaseURL.postEmployeeScheduleEnd + endTime);
    }
}
