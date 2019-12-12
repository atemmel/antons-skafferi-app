package se.grupp1.antonsskafferi.components;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.data.MenuItemData;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;
import se.grupp1.antonsskafferi.popups.EditDinnerMenuPopup;

public class EditDinnerRowComponent extends CardView
{
    private MenuItemData itemData;

    public EditDinnerRowComponent(Context context)
    {
        super(context);
    }

    public EditDinnerRowComponent(Context context, MenuItemData itemData)
    {
        super(context);

        this.itemData = itemData;

        GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f));

        gridParams.setMargins(16, 8,16,8);

        setLayoutParams(gridParams);

        requestLayout();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.component_edit_dinner_item, this, true);


        findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEdit();
            }
        });

        findViewById(R.id.deleteButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog();
            }
        });

        this.setRadius(32f);

        ((TextView)findViewById(R.id.titleText)).setText(itemData.getTitle());
    }

    private void startEdit()
    {
        FragmentActivity parent = (FragmentActivity)getContext();
        FragmentTransaction ft = parent.getSupportFragmentManager().beginTransaction();

        String tag = "dialog";

        EditDinnerMenuPopup popup = new EditDinnerMenuPopup(itemData, new EditDinnerMenuPopup.Callback() {
            @Override
            public void onChanged(MenuItemData itemData)
            {
                updateData(itemData);
            }
        });

        popup.show(ft, tag);
    }

    private void updateData(MenuItemData itemData)
    {
        this.itemData = itemData;

        ((TextView)findViewById(R.id.titleText)).setText(itemData.getTitle());
    }

    private void deleteDialog()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == DialogInterface.BUTTON_POSITIVE)
                {
                    delete();
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String message =    "Du är påväg att ta bort \"" + itemData.getTitle() + "\" från menyn. \n" +
                            "Är du säker?";

        builder.setMessage(message).setPositiveButton("Ja", dialogClickListener)
                .setNegativeButton("Avbryt", dialogClickListener).show();
    }

    private void delete()
    {
        HttpRequest.Response response = new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status)
            {
                if(status != 200) {
                    Toast.makeText(getContext(), "Kunde inte ta bort objekt, var vänlig försök igen. Felkod: " + status,
                            Toast.LENGTH_SHORT
                    ).show();
                }

                killSelf();
            }
        };
        HttpRequest httpRequest = new HttpRequest(response);
        httpRequest.setRequestMethod("DELETE");

        httpRequest.execute(DatabaseURL.deleteItem + itemData.getId());
    }

    private void killSelf()
    {
        ((ViewGroup) getParent()).removeView(this);
    }
}
