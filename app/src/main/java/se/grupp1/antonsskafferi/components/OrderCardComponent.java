package se.grupp1.antonsskafferi.components;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import se.grupp1.antonsskafferi.R;

public class OrderCardComponent extends CardView
{
    private boolean readyStatus = false;

    final static private String READY_STRING = "Klar";
    final static private String NOT_READY_STRING = "Beställd";

    //ArrayList<String> items = new ArrayList<>();

    public OrderCardComponent(Context context, int tableId)
    {
        super(context);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.component_order, this, true);

        ((TextView) findViewById(R.id.tableIdLabel)).setText(Integer.toString(tableId));

        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(0, 16, 0, 0);
        setLayoutParams(params);
        requestLayout();

        setReady(readyStatus);
    }

    public void setReady(boolean readyStatus)
    {
        this.readyStatus = readyStatus;

        TextView readyText = findViewById(R.id.orderStatusLabel);

        if(readyStatus)
        {
            readyText.setText(READY_STRING);
            readyText.setTypeface(readyText.getTypeface(), Typeface.BOLD);
        }
        else
        {
            readyText.setText(NOT_READY_STRING);
            readyText.setTypeface(readyText.getTypeface(), Typeface.NORMAL);
        }
    }

    public void addItem(int count, String name)
    {
        LinearLayout itemsList = (LinearLayout) findViewById(R.id.orderItemsList);

        TextView textView = new TextView(getContext());
        textView.setText(count + " " + name);
        textView.setTextAppearance(getContext(), android.R.style.TextAppearance_Material_Body1); //_AppCompat_Body1);
        textView.setPadding(8, 0,0,0);

        itemsList.addView(textView);
    }

}
