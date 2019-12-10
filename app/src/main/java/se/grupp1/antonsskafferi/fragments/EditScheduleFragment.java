package se.grupp1.antonsskafferi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;

public class EditScheduleFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_edit_schedule, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        loadUsersToDropdown();
        addListenerOnSpinnerEmployeeSelection();

    }

    private void loadUsersToDropdown()
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

    public void addListenerOnSpinnerEmployeeSelection() {
        Spinner dropdown = getView().findViewById(R.id.employeeDropdown);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object user = parent.getItemAtPosition(pos);

                System.out.println(user.toString());
                //loadEmployees(user.toString());

            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadEmployees(String user)
    {
        final String usr = user;

        HttpRequest httpRequest = new HttpRequest(new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status)
            {
                try
                {
                    JSONArray jsonArr = new JSONArray(output);

                    Map<String, String> usersToEmployees = new HashMap<>();

                    for(int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject c = jsonArr.getJSONObject(i);

                        String employeeid = c.getString("employeeid");
                        String username = c.getString("username");

                        usersToEmployees.put(username, employeeid);
                    }
                    //TODO: Only one employee can have a certain name?
                    String employeeidToUser = usersToEmployees.get(usr);

                    loadDatesToDropdown(employeeidToUser);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        httpRequest.setRequestMethod("GET");
        httpRequest.execute(DatabaseURL.getEmployees);
    }


    private void loadDatesToDropdown(String employeeidToUser)
    {
        final String employeeId = employeeidToUser;
        final Spinner dropdown = getView().findViewById(R.id.dateDropdown);

        HttpRequest httpRequest = new HttpRequest(new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status)
            {
                try
                {
                    JSONArray jsonArr = new JSONArray(output);

                    Map<String, String> usersToEmployees = new HashMap<>();

                    for(int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject c = jsonArr.getJSONObject(i);

                        String employee = c.getString("employeeid");
                        String username = c.getString("username");

                        usersToEmployees.put(username, employee);
                    }

                    //String id = usersToEmployees.get(usr);

                    //ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, users);
                    //dropdown.setAdapter(adapter);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        httpRequest.setRequestMethod("GET");
        httpRequest.execute(DatabaseURL.getEmployees);
    }

}
