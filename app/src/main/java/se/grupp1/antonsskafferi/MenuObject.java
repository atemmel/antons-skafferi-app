package se.grupp1.antonsskafferi;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MenuObject extends LinearLayout
{
    private int numberOfItems = 0;

    public MenuObject(Context context, String text)
    {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.object_menu_item, this, true);

        TextView textView = (TextView) getChildAt(0);

        textView.setText(text);

        //this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        this.setPadding(0, 32, 0, 0);

        FloatingActionButton minusButton = (FloatingActionButton)getChildAt(2);

        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseCount();
            }
        });

        FloatingActionButton plusButton = (FloatingActionButton)getChildAt(4);

        plusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCount();
            }
        });

    }

    public MenuObject(Context context, AttributeSet attr) {
        super(context,attr);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.object_menu_item, this, true);
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
        TextView counter = (TextView) getChildAt(3);

        counter.setText( Integer.toString(numberOfItems));
    }

}
