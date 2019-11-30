package se.grupp1.antonsskafferi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class EventFragment extends Fragment
{

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.fragment_event, container, false);

        root.findViewById(R.id.addEventButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText titleEditText = root.findViewById(R.id.titleEventInputText);
                String title = titleEditText.getText().toString();

                EditText descriptionEditText = root.findViewById(R.id.descriptionEventInputText);
                String description = descriptionEditText.getText().toString();

                EditText dateEditText = root.findViewById(R.id.dateEventInputText);
                String date = dateEditText.getText().toString();

                boolean emptyInputField = false;

                if(title.isEmpty()){
                    emptyInputField = true;

                    titleEditText.setError("Lägg till en titel");
                }
                if(description.isEmpty()){
                    emptyInputField = true;
                    descriptionEditText.setError("Lägg till en beskrivning");

                }
                if(date.isEmpty()){
                    emptyInputField = true;
                    dateEditText.setError("Lägg till ett datum");

                }

                if(emptyInputField) return;

                addEvent(title, description, date);
            }
        });

        return root;
    }

    public void addEvent(String title, String description, String date){
        //Send to databaseConnection
    }

}