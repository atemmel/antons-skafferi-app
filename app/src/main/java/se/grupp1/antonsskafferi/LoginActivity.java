package se.grupp1.antonsskafferi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoginActivity extends AppCompatActivity
{
    public static final String IS_ADMIN = "IS_ADMIN";

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
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

            boolean isAdmin = false;

            if(loginResponse == LoginResponse.AdminLogin) isAdmin = true;

            intent.putExtra(IS_ADMIN, isAdmin);

            startActivity(intent);
        }
        else
        {
            passwordEditText.setError("Username and password didn't match");
        }
    }

    public LoginResponse evaluateLogin(String username, String password)
    {
        //Iterator<User> iterator = users.iterator();

        //while(iterator.hasNext())

        for(int i = 0; i < users.size(); i++)
        {
            User user = users.get(i);

           if(username.equals(user.username) && password.equals(user.password))
               if(user.isAdmin)  return LoginResponse.AdminLogin;
               else              return LoginResponse.UserLogin;
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
