package se.grupp1.antonsskafferi.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import se.grupp1.antonsskafferi.R;

public class EditLunchFragment extends Fragment
{

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.fragment_edit_lunch, container, false);

        Spinner spinner = root.findViewById(R.id.alternativeDropdown);

        ArrayList<String> alternativeArray = new ArrayList<>();

        alternativeArray.add("Alternativ 1");
        alternativeArray.add("Alternativ 2");
        alternativeArray.add("Alternativ 3");


        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, alternativeArray);

        spinner.setAdapter(adapter);


        root.findViewById(R.id.addLunchButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText titleEditText = root.findViewById(R.id.titleLunchInputText);
                String title = titleEditText.getText().toString();

                EditText descriptionEditText = root.findViewById(R.id.typeLunchInputText);
                String description = descriptionEditText.getText().toString();

                //EditText dateEditText = root.findViewById(R.id.dateLunchInputText);
                //String date = dateEditText.getText().toString();

                boolean emptyInputField = false;

                if(title.isEmpty()){
                    emptyInputField = true;

                    titleEditText.setError("Lägg till en titel");
                }
                if(description.isEmpty()){
                    emptyInputField = true;
                    descriptionEditText.setError("Lägg till en beskrivning");

                }
                /*if(date.isEmpty()){
                    emptyInputField = true;
                    dateEditText.setError("Lägg till ett datum");

                }*/

                if(emptyInputField) return;

                addLunch(title, description);
            }
        });

        return root;
    }

    public void addLunch(String title, String description){

        //Send to POST request
    }


}