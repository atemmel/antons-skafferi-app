package se.grupp1.antonsskafferi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.activites.MainActivity;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;

public class EditUsersFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_edit_users, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    public void registerUser(){
        HttpRequest request = new HttpRequest(new HttpRequest.Response() {

            //TODO: Visa någon form av laddningsanimation tills vi har fått ett svar från databasen

            @Override
            public void processFinish(String output, int status)
            {
                //Om det ej finns et
                /*
                    Annars är inloggningen korrekt, kolla om det är en admin-inloggning eller ej
                    och skicka vidare användaren till appens startsida.
                 */
                try
                {
                    JSONObject obj = new JSONObject(output);

                    //IS_ADMIN = obj.getBoolean("administrator");

                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        request.setRequestMethod("GET");
        request.execute(DatabaseURL.addUser);
    }
}
