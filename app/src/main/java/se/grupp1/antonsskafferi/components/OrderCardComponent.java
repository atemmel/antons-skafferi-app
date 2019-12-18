package se.grupp1.antonsskafferi.components;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;

public class OrderCardComponent extends CardView
{
    private int readyStatus = 0;

    final static private String READY_STRING = "Klar";
    final static private String NOT_READY_STRING = "Beställd";

    private int tableId;

    //ArrayList<String> items = new ArrayList<>();//

    public OrderCardComponent(Context context, int tableId)
    {
        super(context);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.component_order, this, true);

        this.tableId = tableId;

        ((TextView) findViewById(R.id.tableIdLabel)).setText(Integer.toString(tableId));

        findViewById(R.id.doneButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (which == DialogInterface.BUTTON_POSITIVE)
                        {
                                setDone();
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                String message =    "Detta ska bara göras efter du har lämnat maten. Beställningen kommer få lämnad-status. \n\n" +
                        "Är du säker att du vill fortsätta?";

                builder.setMessage(message).setPositiveButton("Ja", dialogClickListener)
                        .setNegativeButton("Avbryt", dialogClickListener).show();
            }
        });

        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(0, 16, 0, 0);
        setLayoutParams(params);
        requestLayout();

        setReady(readyStatus);
    }

    public void setDone()
    {
        HttpRequest request = new HttpRequest(new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status) {
                ((ViewGroup) getParent()).removeView(OrderCardComponent.this);
            }
        });

        request.setRequestMethod("POST");
        request.execute(DatabaseURL.setDelivered + tableId);
    }

    public void setReady(int readyStatus)
    {
        this.readyStatus = readyStatus;

        TextView readyText = findViewById(R.id.orderStatusLabel);

        if(readyStatus == 1)
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
        textView.setTextAppearance(getContext(), android.R.style.TextAppearance_Material_Body1);
        textView.setPadding(8, 0,0,0);

        itemsList.addView(textView);
    }

}
