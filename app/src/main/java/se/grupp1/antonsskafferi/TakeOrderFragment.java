package se.grupp1.antonsskafferi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class TakeOrderFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_takeorder, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(getView());

        view.findViewById(R.id.newOrderButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                navController.navigate(R.id.navigation_pick_table);
            }
        });


        view.findViewById(R.id.viewOrdersButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                navController.navigate(R.id.navigation_view_orders);
            }
        });
    }
}
