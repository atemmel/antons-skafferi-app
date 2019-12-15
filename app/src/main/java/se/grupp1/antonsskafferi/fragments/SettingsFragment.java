package se.grupp1.antonsskafferi.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import se.grupp1.antonsskafferi.activites.LoginActivity;
import se.grupp1.antonsskafferi.R;

public class SettingsFragment extends PreferenceFragmentCompat
{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        if(LoginActivity.IS_ADMIN)  addPreferencesFromResource(R.xml.admin_preferences);


        Preference logoutButtonPreference = findPreference("logoutButtonPreference");
        logoutButtonPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                SharedPreferences prefs = getContext().getSharedPreferences("loginProfile", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", "");
                editor.putString("password", "");
                editor.apply();

                startActivity(new Intent(getActivity().getApplication(), LoginActivity.class));
                return true;
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

       final NavController navController = Navigation.findNavController(getView());

        if(LoginActivity.IS_ADMIN) {

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

            Preference usersPreference = findPreference("editUsersPreference");
            usersPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    navController.navigate(R.id.navigation_edit_users);

                    return true;
                }
            });
        }
    }
}