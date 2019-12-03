package se.grupp1.antonsskafferi.popups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import se.grupp1.antonsskafferi.classes.HttpRequest;
import se.grupp1.antonsskafferi.components.MenuComponent;
import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.fragments.NewOrderFragment;

public class OrderSummaryPopup extends DialogFragment {

    private ArrayList<se.grupp1.antonsskafferi.fragments.NewOrderFragment.Item> items = new ArrayList<>();

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

        for(int i = 0; i < items.size(); i++)
        {
            NewOrderFragment.Item order = items.get(i);

            list.addView(new MenuComponent(this.getContext(), order.getName(), order.getCount(), order.getNote()));
        }

        v.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        v.findViewById(R.id.popupOrderButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postOrder();
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

    public void setItems(ArrayList<NewOrderFragment.Item> items)
    {
        this.items = items;
    }

    private void postOrder()
    {
        final String urlString = "http://10.0.2.2:8080/post/orders?order=";  //TODO: Move to a global constant of some sorts


        for(int i = 0; i < items.size(); i++)
        {
            NewOrderFragment.Item item = items.get(i);

            JSONObject object = new JSONObject();

            try {
                object.put("amount", item.getCount());
                object.put("note", item.getNote());
                object.put("ready", false);
                object.put("dinnertable", 2);   //TODO: Make this not hardcoded
                object.put("item", 3);          //TODO: Make this not hardcoded

                HttpRequest.Response response = new HttpRequest.Response() {
                    @Override
                    public void processFinish(String output) {
                        System.out.println(output);
                    }
                };

                HttpRequest httpRequest = new HttpRequest(response);
                httpRequest.setRequestMethod("POST");

                System.out.println(object.toString());
                httpRequest.setPayload(object.toString());
                httpRequest.execute(urlString);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

}
