package se.grupp1.antonsskafferi.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.data.MenuItemData;
import se.grupp1.antonsskafferi.data.OrderItemData;

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

        //ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();

        //layoutParams.setMargins(16, 16, 16, 0);



        this.setRadius(32f);
        //this.setCardElevation(16f);


        ((TextView)findViewById(R.id.titleText)).setText(itemData.getTitle());
    }



}
