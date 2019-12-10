package se.grupp1.antonsskafferi.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.data.BookingData;
import se.grupp1.antonsskafferi.popups.BookingOptionsPopup;

public class BookingCardComponent extends CardView
{

    public BookingCardComponent(final Context context, final BookingData bookingData)
    {
        super(context);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.component_booking, this, true);

        ((TextView) findViewById(R.id.dateLabel)).setText(bookingData.getDate());
        ((TextView) findViewById(R.id.nameLabel)).setText(bookingData.getFirstName());
        ((TextView) findViewById(R.id.BookingamountLabel)).setText(bookingData.getBookingAmount());
        ((TextView) findViewById(R.id.BookingtimeLabel)).setText(bookingData.getTime());

        View Btn = findViewById(R.id.options);

        Btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity parent = (FragmentActivity)getContext();
                FragmentTransaction ft = parent.getSupportFragmentManager().beginTransaction();




/*
                String firstName = bookingFirstName.getText().toString();
                String lastName = bookingLastName.getText().toString();
                String peopleAmount = bookingPeopleAmount.getText().toString();
                String phoneNr = bookingPhoneNr.getText().toString();
                String email = bookingEmail.getText().toString();
                String time = bookingTime.getText().toString();
                String date = bookingDate.getText().toString();
*/


                String tag = "dialog";

                BookingOptionsPopup popup = new BookingOptionsPopup(bookingData, new BookingOptionsPopup.Callback() {
                    @Override
                    public void onChanged(BookingData itemData) {


                    }
                });
                popup.show(ft, tag);


                //EditText bookingFirstName = findViewById(R.id.BookingFirstName);
                /*EditText bookingLastName = findViewById(R.id.BookingLastName);
                EditText bookingPeopleAmount =  findViewById(R.id.BookingPeopleAmount);
                EditText bookingPhoneNr =  findViewById(R.id.BookingPhoneNr);
                EditText bookingEmail =  findViewById(R.id.BookingEmail);
                TextView bookingTime =  findViewById(R.id.BookingTime);
                TextView bookingDate =  findViewById(R.id.BookingDate);*/
               // System.out.println(bookingData.getFirstName());

                //bookingFirstName.setText(bookingData.getFirstName());
                /*bookingLastName.setText(bookingData.getLastName());
                bookingPeopleAmount.setText(bookingData.getBookingAmount());
                bookingPhoneNr.setText(bookingData.getPhoneNr());
                bookingEmail.setText(bookingData.getEmail());
                bookingTime.setText(bookingData.getTime());
                bookingDate.setText(bookingData.getDate());*/
               // popup.show(ft, tag);

            }
        });

        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(0, 16, 0, 0);
        setLayoutParams(params);
        requestLayout();

    }


}
