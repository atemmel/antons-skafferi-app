package se.grupp1.antonsskafferi.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.data.MenuItemData;
import se.grupp1.antonsskafferi.data.OrderItemData;
import se.grupp1.antonsskafferi.popups.EditDinnerMenuPopup;

public class EditDinnerRowComponent extends CardView
{
    private MenuItemData itemData;

    public EditDinnerRowComponent(Context context)
    {
        super(context);
    }

    public EditDinnerRowComponent(Context context, MenuItemData itemData)
    {
        super(context);

        this.itemData = itemData;

        GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f));

        gridParams.setMargins(16, 8,16,8);

        setLayoutParams(gridParams);

        requestLayout();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.component_edit_dinner_item, this, true);


        findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEdit();
            }
        });

        this.setRadius(32f);

        ((TextView)findViewById(R.id.titleText)).setText(itemData.getTitle());
    }

    private void startEdit()
    {
        FragmentActivity parent = (FragmentActivity)getContext();
        FragmentTransaction ft = parent.getSupportFragmentManager().beginTransaction();

        String tag = "dialog";

        EditDinnerMenuPopup popup = new EditDinnerMenuPopup(itemData);

        popup.show(ft, tag);
    }

}
