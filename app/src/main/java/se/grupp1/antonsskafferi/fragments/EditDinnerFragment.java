package se.grupp1.antonsskafferi.fragments;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.components.EditDinnerRowComponent;
import se.grupp1.antonsskafferi.components.MenuComponent;
import se.grupp1.antonsskafferi.data.MenuItemData;
import se.grupp1.antonsskafferi.data.OrderItemData;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;

public class EditDinnerFragment extends Fragment
{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_edit_dinner, container, false);

        //((LinearLayout) root.findViewById(R.id.dinnerMenuItemsList)).addView(new EditDinnerRowComponent(getContext(), new MenuItemData(1, "Carbonara", "test", 115, 1)));

        //((LinearLayout) root.findViewById(R.id.dinnerMenuItemsList)).addView(new EditDinnerRowComponent(getContext(), new MenuItemData(1, "Carbonara", "test", 115, 1)));

        root.findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEdit();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        loadAllItems();
    }

    private void startEdit()
    {
        //Starta popup här...
    }

    private void loadAllItems()
    {
        final LinearLayout itemsList = getView().findViewById(R.id.dinnerMenuItemsList);

        HttpRequest httpRequest = new HttpRequest(new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status)
            {
                try
                {
                    JSONArray jsonArr = new JSONArray(output);

                    for(int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject c = jsonArr.getJSONObject(i);

                        int id = c.getInt("itemid");
                        String title = c.getString("title");
                        int price = c.getInt("price");
                        String description = c.getString("description");
                        int categoryId = c.getInt("itemcategory");

                        MenuItemData item = new MenuItemData(id, title, description, price, categoryId);

                        itemsList.addView(new EditDinnerRowComponent(getContext(), item));

                    }

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        httpRequest.setRequestMethod("GET");
        httpRequest.execute(DatabaseURL.getItems);
    }
}
