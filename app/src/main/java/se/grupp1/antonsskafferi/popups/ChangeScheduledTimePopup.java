package se.grupp1.antonsskafferi.popups;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;
import se.grupp1.antonsskafferi.lib.StringFormatter;


public class ChangeScheduledTimePopup extends DialogFragment {

    private String user = "";
    private String startTime = "";
    private String endTime = "";
    private String chosenDate = "";
    private String current_user ="";
    private String workId = "";
    private String current_workId = "";

    public ChangeScheduledTimePopup(String user, String startTime, String endTime, String workId) {
        setUser(user);
        setStartTime(startTime);
        setEndTime(endTime);
        setWorkId(workId);
    }

    public void setUser(String user){
        this.user = user;
    }
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }
    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public void setWorkId(String workId) { this.workId = workId;}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.popup_change_sheduled_time, container, false);

        SharedPreferences prefs = getContext().getSharedPreferences("loginProfile", Context.MODE_PRIVATE);
        current_user = prefs.getString("username", "");

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_bg);

        TextView userName = root.findViewById(R.id.nameText);
        userName.setText(user);

        TextView time = root.findViewById(R.id.timeText);
        time.setText(startTime + "-" + endTime);

        root.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        final TextView chosenDateView = root.findViewById(R.id.chosenDateView);

        root.findViewById(R.id.sendRequestButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emptyTimeDropdown()){
                    noTimesInDropdownToast();
                } else{
                    sendRequestDialog();
                }
            }
        });

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
                        chosenDate = StringFormatter.formatDate(year + "-" + (month + 1) + "-" + day);
                        chosenDateView.setText(StringFormatter.formatDate(year + "-" + (month + 1) + "-" + day));
                        loadTimesToDropdown();
                    }
                }, year, month, day);
                datepickerdialogview.show();
            }
        });

        return root;
    }

    private void sendRequestDialog()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == DialogInterface.BUTTON_POSITIVE){
                    sendRequest();
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String message =    "Du är påväg att skicka en förfrågan till \"" + user + "\" om att byta tid. \n" +
                "Är du säker?";

        builder.setMessage(message).setPositiveButton("Ja", dialogClickListener)
                .setNegativeButton("Avbryt", dialogClickListener).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    private void sendRequest(){
        HttpRequest httpRequest = new HttpRequest(new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status)
            {
                Toast.makeText(getActivity(), output.trim(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        httpRequest.setRequestMethod("POST");
        httpRequest.execute(DatabaseURL.switchEmployeeSchedualId + current_user + DatabaseURL.secondUser + user + DatabaseURL.scheduleone + current_workId + DatabaseURL.scheduletwo + workId);

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

                        Integer workscheduleid = c.getInt("workingscheduleid");
                        String start = c.getString("start");
                        String end = c.getString("end");

                        current_workId = workscheduleid.toString();
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
        httpRequest.execute(DatabaseURL.getWorkScheduleByNameAndDate + current_user + DatabaseURL.getGetWorkingScheduleByDate + chosenDate);
    }

    private Boolean emptyTimeDropdown() {
        Spinner dropdown = getView().findViewById(R.id.timeSpinner);

        if(dropdown != null && dropdown.getSelectedItem() != "" ) {
            System.out.println("No item");
            return true;
        }
        return false;
    }

    private void noTimesInDropdownToast()
    {
        Toast.makeText(getActivity(), "Du har inga inbokade \npass " + chosenDate + ".",
                Toast.LENGTH_SHORT
        ).show();
    }
}
