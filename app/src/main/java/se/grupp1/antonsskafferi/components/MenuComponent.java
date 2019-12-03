package se.grupp1.antonsskafferi.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.popups.MultilineTextPopup;


public class MenuComponent extends LinearLayout
{
    private int numberOfItems = 0;

    private String note = "";

    private String name;

    public MenuComponent(Context context, String text)
    {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.component_menu_item, this, true);

        init(text, numberOfItems, note);
    }

    public MenuComponent(Context context, String text, int numberOfItems, String note)
    {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.component_menu_item, this, true);

        init(text, numberOfItems, note);
    }

    public void init(String text, int numberOfItems, String note)
    {
        this.numberOfItems = numberOfItems;

        this.note = note;

        updateText();

        TextView textView = (TextView) getChildAt(0);

        textView.setText(text);

        this.name = text;

        this.setPadding(0, 32, 0, 0);

        final ImageButton editButton = (ImageButton)getChildAt(2);

        editButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentActivity parent = (FragmentActivity)getContext();
                FragmentTransaction ft = parent.getSupportFragmentManager().beginTransaction();

                MultilineTextPopup editPopup = MultilineTextPopup.newInstance(new MultilineTextPopup.Response() {
                    @Override
                    public void getResponse(String text) {
                        setNote(text);
                    }
                }, getNote());

                editPopup.show(ft, "dialog");
            }
        });


        FloatingActionButton minusButton = (FloatingActionButton)getChildAt(3);

        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseCount();
            }
        });

        FloatingActionButton plusButton = (FloatingActionButton)getChildAt(5);

        plusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCount();
            }
        });
    }

    public MenuComponent(Context context, AttributeSet attr)
    {
        super(context,attr);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.component_menu_item, this, true);
    }

    protected void increaseCount()
    {
        numberOfItems++;

        updateText();
    }

    protected void decreaseCount()
    {
        numberOfItems--;

        if(numberOfItems < 0) numberOfItems = 0;

        updateText();
    }

    private void updateText()
    {
        TextView counter = (TextView) getChildAt(4);

        counter.setText( Integer.toString(numberOfItems));
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getName()
    {
        return name;
    }

    public int getCount()
    {
        return numberOfItems;
    }

    public String getNote() { return note;}
}
