package se.grupp1.antonsskafferi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat
{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        Preference logoutButtonPreference = findPreference("logoutButtonPreference");
        logoutButtonPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(getActivity().getApplication(), LoginActivity.class));
                return true;
            }
        });

        if(LoginActivity.IS_ADMIN)  addPreferencesFromResource(R.xml.admin_preferences);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if(LoginActivity.IS_ADMIN) {

            final NavController navController = Navigation.findNavController(getView());

            Preference lunchPreference = findPreference("editLunchPreference");
            lunchPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    navController.navigate(R.id.navigation_edit_lunch);

                    return true;
                }
            });

            Preference dinnerPreference = findPreference("editDinnerPreference");
            dinnerPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    navController.navigate(R.id.navigation_edit_dinner);

                    return true;
                }
            });

            Preference eventPreference = findPreference("editEventPreference");
            eventPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    navController.navigate(R.id.navigation_event);

                    return true;
                }
            });

            Preference schedulePreference = findPreference("editSchedulePreference");
            schedulePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    navController.navigate(R.id.navigation_edit_schedule);

                    return true;
                }
            });
        }
    }
}