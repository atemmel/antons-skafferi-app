package se.grupp1.antonsskafferi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class NewOrderFragment extends Fragment
{

    private ArrayList<String> food = new ArrayList<>();
    ArrayList<String> drinks = new ArrayList<>();

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

        drinks.add("Coca Cola");
        drinks.add("Fanta");
        drinks.add("Ramlösa");

        getDishes();


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
                OrderSummaryFragment newFragment = OrderSummaryFragment.newInstance();

                newFragment.setOrders(getOrders());

                newFragment.show(ft, "dialog");
            }
        });
    }

    private void getDishes()
    {
        final String urlString = "http://10.0.2.2:8080/items";

        HttpRequest request = new HttpRequest(new HttpRequest.Response()
        {
            @Override
            public void processFinish(String output) {
                try
                {
                    System.out.println(output);

                    JSONArray jsonArr = new JSONArray(output);
                    for(int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject c = jsonArr.getJSONObject(i);

                        String title = c.getString("title");

                        System.out.println("FOOD: " + title);

                        food.add(title);
                    }

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    LinearLayout foodList = getView().findViewById(R.id.foodList);

                    for(int i = 0; i < food.size(); i++)
                    {
                        foodList.addView(new MenuObject(getContext(), food.get(i)));
                    }

                    LinearLayout drinksList = getView().findViewById(R.id.drinksList);

                    for(int i = 0; i < drinks.size(); i++)
                    {
                        drinksList.addView(new MenuObject(getContext(), drinks.get(i)));
                    }
                }

            }
        });

        request.setRequestMethod("GET");

        request.execute(urlString);
    }

    private ArrayList<Order> getOrders()
    {
        ArrayList<Order> orders = new ArrayList<>();

        LinearLayout foodList = getView().findViewById(R.id.foodList);

        //Börja på 1 för att skippa första textobjektet
        for(int i = 1; i < foodList.getChildCount(); i++)
        {
            MenuObject item = (MenuObject)foodList.getChildAt(i);

            if(item.getCount() > 0) {
                orders.add(new Order(item.getName(), item.getCount()));
            }
        }

        return orders;
    }

    class Order
    {
        private String name;
        private int count;

        Order(String name, int count)
        {
            this.name = name;
            this.count = count;
        }

        public String getName()
        {
            return name;
        }

        public int getCount()
        {
            return count;
        }
    }
}
