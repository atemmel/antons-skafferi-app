package se.grupp1.antonsskafferi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class ViewOrdersFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_view_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout orderList = view.findViewById(R.id.orderList);

        OrderListObject order1 = new OrderListObject(getContext(), "3");

        order1.setReady(true);
        order1.addItem(2, "Köttbullar");
        order1.addItem(1, "Pizza");
        order1.addItem(3, "Coca Cola");

        orderList.addView(order1);


        OrderListObject order2 = new OrderListObject(getContext(), "5");

        order2.addItem(1, "Lasagne");
        order2.addItem(1, "Carbonara");
        order2.addItem(1, "Fanta");
        order2.addItem(1, "Ramlösa");

        orderList.addView(order2);

    }
}
