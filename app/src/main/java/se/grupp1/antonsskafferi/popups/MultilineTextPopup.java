package se.grupp1.antonsskafferi.popups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import se.grupp1.antonsskafferi.R;

public class MultilineTextPopup extends DialogFragment
{
    private String startText = "";

    Response response;

    public interface Response
    {
        void getResponse(String text);
    }

    public MultilineTextPopup()
    {

    }

    public static MultilineTextPopup newInstance(Response response, String text)
    {
        MultilineTextPopup fragment = new MultilineTextPopup();

        fragment.response = response;

        fragment.startText = text;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.popup_multiline_text, container, false);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_bg);

        v.findViewById(R.id.okButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult();
            }
        });

        v.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        EditText noteText = v.findViewById(R.id.noteText);
        noteText.setText(startText);

        return v;
    }

    @Override
    public int show(FragmentTransaction ft, String tag)
    {
        return super.show(ft, tag);
    }

    private void sendResult()
    {
        String text = ((EditText)getView().findViewById(R.id.noteText)).getText().toString();

        this.response.getResponse(text);

        dismiss();
    }
}