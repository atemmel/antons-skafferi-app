package se.grupp1.antonsskafferi;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;

public class NewOrderFragment extends Fragment
{

    ArrayList<String> food = new ArrayList<>();
    ArrayList<String> drinks = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_new_order, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        food.add("Carbonara");
        food.add("Lasagne");
        food.add("Oxfilé");
        food.add("Köttbullar");

        drinks.add("Coca Cola");
        drinks.add("Fanta");
        drinks.add("Ramlösa");

        LinearLayout drinksList = view.findViewById(R.id.drinksList);
        for(int i = 0; i < drinks.size(); i++)
        {
            drinksList.addView(new MenuObject(this.getContext(), drinks.get(i)));
        }

        LinearLayout foodList = view.findViewById(R.id.foodList);

        for(int i = 0; i < food.size(); i++)
        {
            foodList.addView(new MenuObject(this.getContext(), food.get(i)));
        }


        view.findViewById(R.id.summaryButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    ArrayList<Order> getOrders()
    {
        ArrayList<Order> orders = new ArrayList<>();

        LinearLayout foodList = getView().findViewById(R.id.foodList);

        //Börja på 1 för att skippa första textobjektet
        for(int i = 1; i < foodList.getChildCount(); i++)
        {
            MenuObject item = (MenuObject)foodList.getChildAt(i);

            if(item.getCount() > 0)
                orders.add(new Order(item.getName(), item.getCount()));
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
