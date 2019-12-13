package se.grupp1.antonsskafferi.popups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;

public class ShowBookingForTablePopup extends DialogFragment
{
    int customerId;

    public ShowBookingForTablePopup(int customerId)
    {
        this.customerId = customerId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.popup_show_booking_for_table, container, false);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_bg);

        v.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        HttpRequest.Response response = new HttpRequest.Response()
        {
            @Override
            public void processFinish(String output, int status)
            {
                TextView firstnameText = getView().findViewById(R.id.firstnameText);
                TextView lastnameText = getView().findViewById(R.id.lastnameText);
                TextView sizeofcompanyText = getView().findViewById(R.id.sizeofcompanyText);
                TextView timeText = getView().findViewById(R.id.timeText);
                TextView emailText = getView().findViewById(R.id.emailText);
                TextView phoneText = getView().findViewById(R.id.phoneText);

                try
                {
                    JSONObject obj = new JSONObject(output);

                    firstnameText.setText(obj.getString("firstname"));
                    lastnameText.setText(obj.getString("lastname"));
                    sizeofcompanyText.setText(Integer.toString(obj.getInt("sizeofcompany")));
                    timeText.setText(obj.getString("bookingtime"));
                    emailText.setText(obj.getString("email"));
                    phoneText.setText(obj.getString("phone"));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        HttpRequest request = new HttpRequest(response);
        request.setRequestMethod("GET");

        String requestUrl = DatabaseURL.getCustomerById + customerId;
        System.out.println(requestUrl);

        request.execute(requestUrl);

    }
}
