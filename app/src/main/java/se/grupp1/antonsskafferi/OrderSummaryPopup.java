package se.grupp1.antonsskafferi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class OrderSummaryPopup extends DialogFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ArrayList<NewOrderFragment.Order> orders = new ArrayList<>();



    public OrderSummaryPopup() {
        // Required empty public constructor
    }


    public static OrderSummaryPopup newInstance()
    {
        OrderSummaryPopup fragment = new OrderSummaryPopup();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_order_summary, container, false);

        //getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_bg);

        LinearLayout list = v.findViewById(R.id.summaryItemsList);

        for(int i = 0; i < orders.size(); i++)
        {
            NewOrderFragment.Order order = orders.get(i);

            list.addView(new MenuObject(this.getContext(), order.getName(), order.getCount(), order.getNote()));
        }

        v.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    public void setOrders(ArrayList<NewOrderFragment.Order> orders)
    {
        this.orders = orders;
    }

}
