package se.grupp1.antonsskafferi;import android.content.Intent;import android.os.Bundle;import android.view.Menu;import android.view.MenuInflater;import android.view.MenuItem;import android.view.View;import androidx.appcompat.app.AppCompatActivity;public class MainActivity extends AppCompatActivity{    //Add the three dot menu bar    @Override    public boolean onCreateOptionsMenu(Menu menu)    {        MenuInflater inflater = getMenuInflater();        inflater.inflate(R.menu.three_dot_menu, menu);        return true;    }    //When an item in the three dot menu bar is clicked, handle it    @Override    public boolean onOptionsItemSelected(MenuItem item)    {        // Handle item selection        switch (item.getItemId())        {            case R.id.settingsButton:                startActivity(new Intent(MainActivity.this, SettingsActivity.class));                return true;            case R.id.logoutButton:                logout();                return true;            default:                return super.onOptionsItemSelected(item);        }    }    @Override    protected void onCreate(Bundle savedInstanceState)    {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        findViewById(R.id.bookingButton).setOnClickListener(new View.OnClickListener()        {            @Override            public void onClick(View v)            {                startActivity(new Intent(MainActivity.this, BookingActivity.class));            }        });        findViewById(R.id.scheduleButton).setOnClickListener(new View.OnClickListener()        {            @Override            public void onClick(View v)            {                startActivity(new Intent(MainActivity.this, ScheduleActivity.class));            }        });    }    public void logout()    {        startActivity(new Intent(MainActivity.this, LoginActivity.class));    }}