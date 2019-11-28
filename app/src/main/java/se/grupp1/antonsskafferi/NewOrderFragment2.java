package se.grupp1.antonsskafferi;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class NewOrderFragment2 extends Fragment
{

    ArrayList<String> food = new ArrayList<>();
    ArrayList<String> drinks = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_new_order2, container, false);
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

    }


}
