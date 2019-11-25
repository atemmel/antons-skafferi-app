package se.grupp1.antonsskafferi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class AdminFragment extends Fragment
{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_admin, container, false);

        //setContentView(R.layout.fragment_admin);

        //setTitle("Admin");


        //Kopiera dessa två rader för att lägga en tillbaka-pil uppe till vänster
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        /*findViewById(R.id.editLunchButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), EditLunchActivity.class));
            }
        });*/
        return root;
    }

    /*public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        return true;
    }*/


}
