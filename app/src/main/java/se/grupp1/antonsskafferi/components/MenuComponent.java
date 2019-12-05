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
import se.grupp1.antonsskafferi.data.ItemData;
import se.grupp1.antonsskafferi.popups.MultilineTextPopup;


public class MenuComponent extends LinearLayout
{
    private ItemData itemData;

    public MenuComponent(Context context, ItemData itemData)
    {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.component_menu_item, this, true);

        init(itemData);
    }

    public void init(ItemData itemData)
    {
        this.itemData = itemData;

        updateText();

        TextView textView = (TextView) getChildAt(0);

        textView.setText(itemData.getTitle());

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
                decreaseAmount();
            }
        });

        FloatingActionButton plusButton = (FloatingActionButton)getChildAt(5);

        plusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseAmount();
            }
        });
    }

    public MenuComponent(Context context, AttributeSet attr)
    {
        super(context,attr);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.component_menu_item, this, true);
    }

    protected void increaseAmount()
    {
        itemData.increaseAmount();

        updateText();
    }

    protected void decreaseAmount()
    {
        itemData.decreaseAmount();

        if(itemData.getAmount() < 0) itemData.setAmount(0);

        updateText();
    }

    private void updateText()
    {
        TextView counter = (TextView) getChildAt(4);

        counter.setText(Integer.toString(itemData.getAmount()));
    }

    public void setNote(String note)
    {
        itemData.setNote(note);
    }

    public int getId() {return itemData.getId();}

    public String getTitle()
    {
        return itemData.getTitle();
    }

    public int getAmount()
    {
        return itemData.getAmount();
    }

    public String getNote()
    {
        return itemData.getNote();
    }
}
