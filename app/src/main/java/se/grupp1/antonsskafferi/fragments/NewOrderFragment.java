package se.grupp1.antonsskafferi.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import se.grupp1.antonsskafferi.data.OrderItemData;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;
import se.grupp1.antonsskafferi.components.MenuComponent;
import se.grupp1.antonsskafferi.popups.OrderSummaryPopup;
import se.grupp1.antonsskafferi.R;

public class NewOrderFragment extends Fragment
{
    private int tableId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_new_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        tableId = getArguments().getInt("tableId");

        /*food.add("Carbonara");
        food.add("Lasagne");
        food.add("Oxfilé");
        food.add("Köttbullar");*/

        /*drinks.add("Coca Cola");
        drinks.add("Fanta");
        drinks.add("Ramlösa");*/

        getAllItems();

        view.findViewById(R.id.summaryButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                OrderSummaryPopup popup = new OrderSummaryPopup(tableId);

                popup.setItemData(getOrderedItems());


                popup.show(ft, "dialog");
            }
        });
    }

    private void getAllItems()
    {
        final LinearLayout foodList = getView().findViewById(R.id.foodList);
        final LinearLayout drinksList = getView().findViewById(R.id.drinksList);

        HttpRequest request = new HttpRequest(new HttpRequest.Response()
        {
            @Override
            public void processFinish(String output, int status) {
                try
                {
                    JSONArray jsonArr = new JSONArray(output);
                    for(int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject c = jsonArr.getJSONObject(i);

                        String title = c.getString("title");
                        int id = c.getInt("itemid");

                        if(c.getString("itemcategory").toUpperCase().equals("5"))   //5 är dryck
                        {
                            OrderItemData item = new OrderItemData(id, title, 0, "");
                            drinksList.addView(new MenuComponent(getContext(), item));
                        }
                        else {
                            OrderItemData item = new OrderItemData(id, title, 0, "");
                            foodList.addView(new MenuComponent(getContext(), item));
                        }
                    }

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        request.setRequestMethod("GET");

        request.execute(DatabaseURL.getItems);
    }

    private ArrayList<OrderItemData> getOrderedItems()
    {
        ArrayList<OrderItemData> itemData = new ArrayList<>();

        LinearLayout drinksList = getView().findViewById(R.id.drinksList);

        //Börja på 1 för att skippa första textobjektet
        for(int i = 1; i < drinksList.getChildCount(); i++)
        {
            MenuComponent item = (MenuComponent)drinksList.getChildAt(i);

            if(item.getAmount() > 0)
            {
                itemData.add(new OrderItemData(item.getId(), item.getTitle(), item.getAmount(), item.getNote()));
            }
        }

        LinearLayout foodList = getView().findViewById(R.id.foodList);

        //Börja på 1 för att skippa första textobjektet
        for(int i = 1; i < foodList.getChildCount(); i++)
        {
            MenuComponent item = (MenuComponent)foodList.getChildAt(i);

            if(item.getAmount() > 0)
            {
                itemData.add(new OrderItemData(item.getId(), item.getTitle(), item.getAmount(), item.getNote()));
            }
        }

        return itemData;
    }
}
