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

import java.util.ArrayList;
import java.util.Calendar;
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
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        TextView dateText = getView().findViewById(R.id.dateLabel);

                        dateText.setText(StringFormatter.formatDate(year + "-" + (month + 1) + "-" + day));
                    }
                }, year, month, day);

                datepickerdialog.show();
            }
        });

        view.findViewById(R.id.startTimePickerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar currentTime = Calendar.getInstance();

                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        TextView timeText = getView().findViewById(R.id.startTimeLabel);

                        timeText.setText(StringFormatter.formatTime(hour + ":" + minute));
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        view.findViewById(R.id.endTimePickerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar currentTime = Calendar.getInstance();

                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        TextView timeText = getView().findViewById(R.id.endTimeLabel);

                        timeText.setText(StringFormatter.formatTime(hour + ":" + minute));
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
