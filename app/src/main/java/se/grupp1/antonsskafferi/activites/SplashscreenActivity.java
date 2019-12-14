package se.grupp1.antonsskafferi.activites;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import se.grupp1.antonsskafferi.R;

public class SplashscreenActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        SharedPreferences prefs = getSharedPreferences("loginProfile", Context.MODE_PRIVATE);
        String username = prefs.getString("username", "");
        String password = prefs.getString("password", "");

        LoginActivity.evaluateCredentials(username, password, new LoginActivity.LoginCheckCallback() {
            @Override
            public void onEvaluated(boolean correctLogin)
            {
                if(correctLogin)    goToMainActivity();
                else                goToLoginActivity();
            }
        });
    }

    void goToMainActivity()
    {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    void goToLoginActivity()
    {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}
