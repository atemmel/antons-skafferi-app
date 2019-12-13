package se.grupp1.antonsskafferi.components;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.data.ItemData;

public class SummaryCardComponent extends CardView {

    public SummaryCardComponent(Context context, ItemData item){
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_summary_card, this, true);

        ((TextView)findViewById(R.id.summaryAmount)).setText("" + item.getAmount());
        ((TextView)findViewById(R.id.summaryTitle)).setText(item.getTitle());
        ((TextView)findViewById(R.id.summaryPrice)).setText("" + item.getPrice());
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(0, 16, 0, 0);
        setLayoutParams(params);
        requestLayout();

    }

}
