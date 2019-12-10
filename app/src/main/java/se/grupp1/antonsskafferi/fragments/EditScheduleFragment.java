package se.grupp1.antonsskafferi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.data.ScheduleData;
import se.grupp1.antonsskafferi.data.UserData;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;

public class EditScheduleFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_edit_schedule, container, false);
        /*Spinner dropdown = root.findViewById(R.id.employeeDropdown);

        String[] items = new String[]{"Employee1", "Employee2", "Employee3"};
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);*/

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        loadEmployees();
    }

    private void loadEmployees()
    {
        final Spinner dropdown = getView().findViewById(R.id.employeeDropdown);

        HttpRequest httpRequest = new HttpRequest(new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status)
            {
                try
                {
                    JSONArray jsonArr = new JSONArray(output);

                    ArrayList<String> users = new ArrayList<String>();

                    for(int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject c = jsonArr.getJSONObject(i);

                        String name = c.getString("username");

                        users.add(name);
                    }

                    ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, users);
                    dropdown.setAdapter(adapter);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        httpRequest.setRequestMethod("GET");
        httpRequest.execute(DatabaseURL.getUsers);
    }


}
