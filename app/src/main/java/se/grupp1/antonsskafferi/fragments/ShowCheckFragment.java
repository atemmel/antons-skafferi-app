package se.grupp1.antonsskafferi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.components.SummaryCardComponent;
import se.grupp1.antonsskafferi.data.ItemData;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;

public class ShowCheckFragment extends Fragment {
    private int tableId;
    int total = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_show_check, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        tableId = getArguments().getInt("tableId");
        checkout(view);

    }

    public void checkout(final View view){
        HttpRequest request = new HttpRequest(new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status) {
                try {
                    JSONArray jsonArr = new JSONArray(output);
                    System.out.println("TOTAL" + jsonArr.length());
                    System.out.println("TABLEID: " + tableId);

                    for(int i = 0; i < jsonArr.length(); i++){
                        JSONObject order = jsonArr.getJSONObject(i);
                        JSONObject item = order.getJSONObject("item");

                        ItemData tmpItem = new ItemData(order.getInt("amount"), item.getInt("itemid"), item.getString("title"), item.getString("description"), item.getInt("price"), item.getInt("itemcategory"));
                        SummaryCardComponent orderCard = new SummaryCardComponent(getContext(), tmpItem);

                        total = total + (item.getInt("price") * order.getInt("amount"));

                        ((LinearLayout)getView().findViewById(R.id.summaryLinear)).addView(orderCard);

                    }
                    ((TextView)getView().findViewById(R.id.summaryTotal)).append("" + total);
            }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        request.setRequestMethod("GET");
        request.execute(DatabaseURL.summaryByTable + tableId);
        System.out.println(DatabaseURL.summaryByTable + tableId);
    }

    public void setupImage(View view){
        //TextView textView = view.findViewById(R.id.textViewTitle);
        //textView.setText("" + summary.getTotal());
    }
}