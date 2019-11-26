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

        if(LoginActivity.IS_ADMIN)  addPreferencesFromResource(R.xml.admin_preferences);

        /*EditLunchFragment nextFrag = new EditLunchFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();*/
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if(LoginActivity.IS_ADMIN) {

            final NavController navController = Navigation.findNavController(getView());

            Preference preference = findPreference("editLunchPreference");
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    navController.navigate(R.id.navigation_edit_lunch);
                    //Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();

                    // Create new fragment and transaction
                    //Fragment newFragment = new EditLunchFragment();

                    return true;
                }
            });
        }
    }
}