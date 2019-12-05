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

import se.grupp1.antonsskafferi.data.ItemData;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;
import se.grupp1.antonsskafferi.components.MenuComponent;
import se.grupp1.antonsskafferi.popups.OrderSummaryPopup;
import se.grupp1.antonsskafferi.R;

public class NewOrderFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_new_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

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
                OrderSummaryPopup newFragment = OrderSummaryPopup.newInstance();

                newFragment.setItemData(getOrderedItems());

                newFragment.show(ft, "dialog");
            }
        });
    }

    private void getAllItems()
    {
        final String urlString = "http://10.0.2.2:8080/items";

        final LinearLayout foodList = getView().findViewById(R.id.foodList);
        final LinearLayout drinksList = getView().findViewById(R.id.drinksList);

        HttpRequest request = new HttpRequest(new HttpRequest.Response()
        {
            @Override
            public void processFinish(String output, int status) {
                try
                {
                    //System.out.println(output);

                    JSONArray jsonArr = new JSONArray(output);
                    for(int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject c = jsonArr.getJSONObject(i);

                        String title = c.getString("title");
                        int id = c.getInt("itemid");

                        System.out.println("FOOD: " + title);

                        if(c.getString("type").toUpperCase().equals("DRYCK"))
                            drinksList.addView(new MenuComponent(getContext(), new ItemData(id, title, 0, "")));
                        else
                            foodList.addView(new MenuComponent(getContext(), new ItemData(id, title, 0, "")));
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

    private ArrayList<ItemData> getOrderedItems()
    {
        ArrayList<ItemData> itemData = new ArrayList<>();

        LinearLayout drinksList = getView().findViewById(R.id.drinksList);

        //Börja på 1 för att skippa första textobjektet
        for(int i = 1; i < drinksList.getChildCount(); i++)
        {
            MenuComponent item = (MenuComponent)drinksList.getChildAt(i);

            if(item.getAmount() > 0)
            {
                itemData.add(new ItemData(1, item.getTitle(), item.getAmount(), item.getNote()));
            }
        }

        LinearLayout foodList = getView().findViewById(R.id.foodList);

        //Börja på 1 för att skippa första textobjektet
        for(int i = 1; i < foodList.getChildCount(); i++)
        {
            MenuComponent item = (MenuComponent)foodList.getChildAt(i);

            if(item.getAmount() > 0)
            {
                itemData.add(new ItemData(1, item.getTitle(), item.getAmount(), item.getNote()));
            }
        }

        return itemData;
    }
}
