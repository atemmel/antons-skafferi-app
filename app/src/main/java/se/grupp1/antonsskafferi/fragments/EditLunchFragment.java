package se.grupp1.antonsskafferi.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;

public class EditLunchFragment extends Fragment
{
    private int dayId = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.fragment_edit_lunch, container, false);

        ((RadioButton)root.findViewById(R.id.mondayRadio)).setChecked(true);

        ((RadioGroup)root.findViewById(R.id.daysRadioButtons)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                handleRadioButtonClicked(root.findViewById(checkedId));
            }
        });

        root.findViewById(R.id.addLunchButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText titleEditText = root.findViewById(R.id.titleLunchInputText);
                String title = titleEditText.getText().toString();

                EditText typeEditText = root.findViewById(R.id.typeLunchInputText);
                String type = typeEditText.getText().toString();

                //EditText dateEditText = root.findViewById(R.id.dateLunchInputText);
                //String date = dateEditText.getText().toString();

                boolean emptyInputField = false;

                if(title.isEmpty()){
                    emptyInputField = true;

                    titleEditText.setError("Lägg till en titel");
                }
                if(type.isEmpty()){
                    emptyInputField = true;
                    typeEditText.setError("Lägg till en typ");

                }
                /*if(date.isEmpty()){
                    emptyInputField = true;
                    dateEditText.setError("Lägg till ett datum");

                }*/

                if(emptyInputField) return;

                addLunch(title, type);
            }
        });

        root.findViewById(R.id.deleteLunchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllLunches();
            }
        });

        return root;
    }

    private void addLunch(String title, String type){

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
        JSONObject object = new JSONObject();

        try
        {
            object.put("mealname", title);

            object.put("days", dayId);
            object.put("type", type);

            HttpRequest request = new HttpRequest(response);
            request.setRequestMethod("POST");
            request.setPayload(object.toString());
            request.execute(DatabaseURL.postLunch);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void deleteAllLunches()
    {
        HttpRequest.Response response = new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status) {
                if(status != 200)
                {
                    Toast.makeText(getActivity(), "Kunde inte ta bort från databsen, var vänlig försök igen. Felkod: " + status,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        };

        HttpRequest request = new HttpRequest(response);
        request.setRequestMethod("DELETE");
        request.execute(DatabaseURL.deleteAllLunches);

    }


    private void handleRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.mondayRadio:
                if (checked) dayId = 1;
                break;
            case R.id.tuesdayRadio:
                if (checked) dayId = 2;
                break;
            case R.id.wednesdayRadio:
                if (checked) dayId = 3;
                break;
            case R.id.thursdayRadio:
                if (checked) dayId = 4;
                break;
            case R.id.fridayRadio:
                if (checked) dayId = 5;
                break;

        }
    }


}