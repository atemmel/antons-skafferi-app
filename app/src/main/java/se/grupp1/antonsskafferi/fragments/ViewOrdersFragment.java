package se.grupp1.antonsskafferi.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import se.grupp1.antonsskafferi.components.OrderCardComponent;
import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.components.TableCardComponent;
import se.grupp1.antonsskafferi.data.OrderItemData;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;
import se.grupp1.antonsskafferi.popups.EditDinnerMenuPopup;


public class ViewOrdersFragment extends Fragment
{
    public interface LoadingCallback
    {
        void finishedLoading();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_view_orders, container, false);

        final SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {

                loadOrders(new LoadingCallback() {
                    @Override
                    public void finishedLoading()
                    {
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        /*final LinearLayout orderList = getView().findViewById(R.id.orderList);

        OrderCardComponent order1 = new OrderCardComponent(getContext(), 3);

        order1.setReady(true);
        order1.addItem(2, "Köttbullar");
        order1.addItem(1, "Pizza");
        order1.addItem(3, "Coca Cola");

        orderList.addView(order1);


        OrderCardComponent order2 = new OrderCardComponent(getContext(), 5);

        order2.addItem(1, "Lasagne");
        order2.addItem(1, "Carbonara");
        order2.addItem(1, "Fanta");
        order2.addItem(1, "Ramlösa");

        orderList.addView(order2);*/

        loadOrders(new LoadingCallback() {
            @Override
            public void finishedLoading() {

            }
        });
    }

    private void loadOrders(final LoadingCallback callback)
    {
        final LinearLayout orderList = getView().findViewById(R.id.orderList);

        orderList.removeAllViews();

        HttpRequest.Response response = new HttpRequest.Response()
        {
            @Override
            public void processFinish(String output, int status)
            {
                System.out.println(status);

                if(status != 200)
                {
                    Toast.makeText(getActivity(), "Kunde inte hämta beställningar. Felkod: " + status,
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

                try {
                    JSONArray jsonArr = new JSONArray(output);

                    int prev_id = -1;

                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject obj = jsonArr.getJSONObject(i);

                        int id = obj.getJSONObject("dinnertable").getInt("dinnertableid");

                        if(id == prev_id)
                        {
                            OrderCardComponent order = (OrderCardComponent) orderList.getChildAt(orderList.getChildCount() -1);
                            order.addItem(obj.getInt("amount"), obj.getJSONObject("item").getString("title"));
                        }
                        else
                        {
                            OrderCardComponent order = new OrderCardComponent(getContext(), id);

                            order.addItem(obj.getInt("amount"), obj.getJSONObject("item").getString("title"));

                            orderList.addView(order);
                        }
                        prev_id = id;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    callback.finishedLoading();
                }
            }
        };

        HttpRequest httpRequest = new HttpRequest(response);
        httpRequest.setRequestMethod("GET");

        httpRequest.execute(DatabaseURL.getOrders);
    }
}
