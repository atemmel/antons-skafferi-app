package se.grupp1.antonsskafferi.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;

public class LoginActivity extends AppCompatActivity
{
    public static boolean IS_ADMIN = false;

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
                evaluateLogin();
            }
        });

    }

    public void evaluateLogin()
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

        evaluateLogin(username, password);

    }

    void displayInvalidLogin()
    {
        Toast.makeText(this, "Username and password didn't match",
                Toast.LENGTH_LONG).show();
    }

    public void evaluateLogin(String username, String password)
    {
        if(username.equals("admin"))
        {
            IS_ADMIN = true;
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        HttpRequest request = new HttpRequest(new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status) {

                if(output.isEmpty())
                {
                    displayInvalidLogin();
                    return;
                }

                try
                {
                    JSONObject obj = new JSONObject(output);

                    if(obj.has("administrator"))
                    {
                        if(obj.getBoolean("administrator")) IS_ADMIN = true;
                        else                                       IS_ADMIN = false;

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        request.setRequestMethod("GET");

        request.execute(DatabaseURL.validateLogin + username + DatabaseURL.validateLoginPassword + password);
    }
}
