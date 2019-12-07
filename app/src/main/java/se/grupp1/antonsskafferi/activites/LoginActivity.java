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

        evaluateCredentials(username, password);

    }

    void displayInvalidLogin()
    {
        Toast.makeText(this, "Username and password didn't match",
                Toast.LENGTH_LONG).show();
    }

    public void evaluateCredentials(String username, String password)
    {
        //TODO: När det känns lämpligt, ta bort detta
        //Tillåt inloggning som admin även om backend ej är igång
        if(username.equals("admin"))
        {
            IS_ADMIN = true;
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return;
        }

        HttpRequest request = new HttpRequest(new HttpRequest.Response() {

            //TODO: Visa någon form av laddningsanimation tills vi har fått ett svar från databasen

            @Override
            public void processFinish(String output, int status)
            {

                //Om det ej finns ett svar från databasen är inloggningen felaktig
                if(output.isEmpty())
                {
                    displayInvalidLogin();
                    return;
                }

                /*
                    Annars är inloggningen korrekt, kolla om det är en admin-inloggning eller ej
                    och skicka vidare användaren till appens startsida.
                 */
                try
                {
                    JSONObject obj = new JSONObject(output);

                    IS_ADMIN = obj.getBoolean("administrator");

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
