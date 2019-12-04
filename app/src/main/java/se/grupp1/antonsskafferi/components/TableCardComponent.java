package se.grupp1.antonsskafferi.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import se.grupp1.antonsskafferi.R;

public class TableCardComponent extends CardView
{
    public TableCardComponent(Context context, String tableId)
    {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f));

        gridParams.setMargins(16, 0,16,32);

        setLayoutParams(gridParams);

        this.setRadius(32f);
        this.setCardElevation(16f);

        requestLayout();

        inflater.inflate(R.layout.component_table_card, this, true);


        ((TextView)findViewById(R.id.tableId)).setText(tableId);

        //((GridLayout.LayoutParams) this.getLayoutParams()).columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
    }
}
