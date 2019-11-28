package se.grupp1.antonsskafferi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class OrderSummaryFragment extends DialogFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ArrayList<NewOrderFragment.Order> orders = new ArrayList<>();



    public OrderSummaryFragment() {
        // Required empty public constructor
    }


    public static OrderSummaryFragment newInstance()
    {
        OrderSummaryFragment fragment = new OrderSummaryFragment();
        /*Bundle args = new Bundle();
        args.putParcelableArray(ARG_ORDERS, orders);
        fragment.setArguments(args);*/

        //orders = orders;x

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_order_summary, container, false);

        LinearLayout list = v.findViewById(R.id.summaryItemsList);

        for(int i = 0; i < orders.size(); i++)
        {
            list.addView(new MenuObject(this.getContext(), orders.get(i).getName()));
        }

        return v;
    }

    public void setOrders(ArrayList<NewOrderFragment.Order> orders)
    {
        this.orders = orders;
    }

}
