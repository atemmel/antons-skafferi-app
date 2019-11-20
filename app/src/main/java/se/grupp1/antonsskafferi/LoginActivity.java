package se.grupp1.antonsskafferi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Logga in");

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText usernameEditText = findViewById(R.id.usernameEditText);
                String username = usernameEditText.getText().toString();

                EditText passwordEditText = findViewById(R.id.passwordEditText);
                String password = passwordEditText.getText().toString();

                boolean emptyFields = false;

                if(username.isEmpty())
                {
                    emptyFields = true;
                    usernameEditText.setError("Skriv in ett användarnamn");
                }
                if(password.isEmpty())
                {
                    emptyFields = true;
                    passwordEditText.setError("Skriv in ett lösenord");
                }

                if(emptyFields) return;

                if(validLogin(username, password))
                {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else
                {
                    passwordEditText.setError("Username and password didn't match");
                }
            }
        });
    }

    public boolean validLogin(String username, String password)
    {
        //Checka mot databas här senare...

        String correctUsername = "admin";
        String correctPassword = "123";

        if(username.equals(correctUsername) && password.equals(correctPassword))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
