package se.grupp1.antonsskafferi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.activites.MainActivity;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;

public class EditUsersFragment extends Fragment {
    private boolean adminAccount;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_edit_users, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState)
    {


        super.onViewCreated(view, savedInstanceState);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.registerCheckbox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                adminAccount = isChecked;
            }
        });



        view.findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText firstName = view.findViewById(R.id.registerFirstName);
                String fName = firstName.getText().toString();

                EditText lastName = view.findViewById(R.id.registerLastName);
                String lName = lastName.getText().toString();

                EditText password1 = view.findViewById(R.id.registerPassword1);
                String pass1 = password1.getText().toString();

                EditText password2 = view.findViewById(R.id.registerPassword2);
                String pass2 = password2.getText().toString();


                if(pass1.equals(pass2)){
                    registerUser(fName, lName,  pass1, adminAccount);
                }

                firstName.getText().clear();
                lastName.getText().clear();
                password1.getText().clear();
                password2.getText().clear();

            }
        });
    }



    public void registerUser(String firstName, String lastName, String password, boolean admin){

        HttpRequest request = new HttpRequest(new HttpRequest.Response() {


            @Override
            public void processFinish(String output, int status)
            {
                try
                {
                    JSONObject obj = new JSONObject(output);
                    String userid = obj.getString("username");

                    Toast toast = Toast.makeText(getActivity(), "Konto skapad med användarnamn: " + userid, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        request.setRequestMethod("POST");
        System.out.println("URL: " + DatabaseURL.addUserFirstName + firstName + DatabaseURL.addUserLastName + lastName + DatabaseURL.addUserPassword + password + DatabaseURL.addUserAdmin + admin);
        request.execute(DatabaseURL.addUserFirstName + firstName + DatabaseURL.addUserLastName + lastName + DatabaseURL.addUserPassword + password + DatabaseURL.addUserAdmin + admin);
    }
}
