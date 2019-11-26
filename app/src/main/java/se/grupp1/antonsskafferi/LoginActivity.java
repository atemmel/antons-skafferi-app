package se.grupp1.antonsskafferi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity
{
    public static boolean IS_ADMIN = false;

    private ArrayList<User> users = new ArrayList<>();

    enum LoginResponse
    {
        AdminLogin,
        UserLogin,
        InvalidLogin
    }

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

        users.add(new User("admin", "asd", true));
        users.add(new User("user", "asd", false));
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

        LoginResponse loginResponse = evaluateLogin(username, password);



        if(loginResponse != LoginResponse.InvalidLogin)
        {
            if(loginResponse == LoginResponse.AdminLogin) IS_ADMIN = true;
            else                                          IS_ADMIN = false;

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        else
        {
            passwordEditText.setError("Username and password didn't match");
        }
    }

    public LoginResponse evaluateLogin(String username, String password)
    {
        for(int i = 0; i < users.size(); i++)
        {
            User user = users.get(i);

            if (username.equals(user.username) && password.equals(user.password))
            {
                if (user.isAdmin)   return LoginResponse.AdminLogin;
                else                return LoginResponse.UserLogin;
            }
        }

        return LoginResponse.InvalidLogin;
    }

    //Ska bytas ut mot databas senare...
    class User
    {
        public String username;
        public String password;
        public boolean isAdmin;

        User(String username, String password, Boolean isAdmin)
        {
            this.username = username;
            this.password = password;
            this.isAdmin = isAdmin;
        }
    }
}
