package se.grupp1.antonsskafferi;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class EditLunchFragment extends Fragment
{

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.fragment_edit_lunch, container, false);


        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Lägga till ny lunchmeny", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        root.findViewById(R.id.addLunchButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText titleEditText = root.findViewById(R.id.titleLunchInputText);
                String title = titleEditText.getText().toString();

                EditText descriptionEditText = root.findViewById(R.id.descriptionLunchInputText);
                String description = descriptionEditText.getText().toString();

                EditText dateEditText = root.findViewById(R.id.dateLunchInputText);
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

                addLunch(title, description, date);
            }
        });

        return root;
    }

    public void addLunch(String title, String description,String date){
        //Send to POST request
    }


}