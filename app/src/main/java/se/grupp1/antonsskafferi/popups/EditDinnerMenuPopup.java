package se.grupp1.antonsskafferi.popups;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Collections;
import java.util.Map;
import java.util.Set;


import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.components.EditDinnerRowComponent;
import se.grupp1.antonsskafferi.data.MenuItemData;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.HttpRequest;

public class EditDinnerMenuPopup extends DialogFragment
{
    private MenuItemData itemData = new MenuItemData();

    private Map<Integer, String> category_map = new HashMap<>();
    private Map<String, Integer> inverse_category_map = new HashMap<>();

    Callback callback;

    public interface Callback
    {
        void onChanged(MenuItemData itemData);
    }

    public EditDinnerMenuPopup(Callback callback){
        this.callback = callback;
    }

    public EditDinnerMenuPopup(MenuItemData itemData, Callback callback)
    {
        this.itemData = itemData;

        this.callback = callback;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(params);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.popup_edit_dinner_popup, container, false);

        v.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        v.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        return v;
    }

    @Override
    public void dismiss()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if(which == DialogInterface.BUTTON_POSITIVE)
                    EditDinnerMenuPopup.super.dismiss();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String message =    "Du kommer förlora dina ändringar \n" +
                "Är du säker på att du vill fortsätta?";

        builder.setMessage(message).setPositiveButton("Ja", dialogClickListener)
                .setNegativeButton("Avbryt", dialogClickListener).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initialize_categories();

        fill_fields();
    }

    private void initialize_categories()
    {

        //TODO: Move this somewhere so categories are only loaded once, not every time an item is edited
        HttpRequest request = new HttpRequest(new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status) {
                try
                {
                    //En lista för alla items i spinners dropdown
                    List<String> spinnerArray =  new ArrayList<>();

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner spinner = getView().findViewById(R.id.category_dropdown);

                    JSONArray jsonArr = new JSONArray(output);

                    for(int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject c = jsonArr.getJSONObject(i);

                        int id = c.getInt("itemcategoryid");
                        String name = c.getString("name");

                        category_map.put(id, name);
                        inverse_category_map.put(name, id);

                        spinnerArray.add(name);
                    }

                    spinner.setAdapter(adapter);

                    spinner.setSelection(adapter.getPosition(category_map.get(itemData.getCategoryId())));

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        request.setRequestMethod("GET");
        request.execute(DatabaseURL.getCategories);
    }

    private void fill_fields()
    {
        if(itemData == null) return;

        TextView title = getView().findViewById(R.id.editTitleText);
        title.setText(itemData.getTitle());

        TextView description = getView().findViewById(R.id.editDescriptionText);
        description.setText(itemData.getDescription());

        TextView priceText = getView().findViewById(R.id.editPriceText);
        //String priceString = price.getText().toString();

        Integer price = itemData.getPrice();


        if(price != -1) priceText.setText(Integer.toString(itemData.getPrice()));
    }

    private void updateItemData()
    {
        EditText title = getView().findViewById(R.id.editTitleText);
        itemData.setTitle(title.getText().toString());

        EditText description = getView().findViewById(R.id.editDescriptionText);
        itemData.setDescription(description.getText().toString());

        EditText price = getView().findViewById(R.id.editPriceText);
        itemData.setPrice(Integer.parseInt(price.getText().toString()));

        Spinner categoryDropDown = getView().findViewById(R.id.category_dropdown);

        String categoryName = categoryDropDown.getSelectedItem().toString();

        itemData.setCategoryId(inverse_category_map.get(categoryName));
    }

    private Boolean emptyFields(){

        EditText titleEditText = getView().findViewById(R.id.editTitleText);
        String title = titleEditText.getText().toString();

        EditText descriptionEditText = getView().findViewById(R.id.editDescriptionText);
        String description = descriptionEditText.getText().toString();

        EditText priceEditText = getView().findViewById(R.id.editPriceText);
        String price = priceEditText.getText().toString();

        boolean emptyFields = false;

        if(title.isEmpty())
        {
            emptyFields = true;
            titleEditText.setError("Skriv in en titel");
        }
        if(description.isEmpty())
        {
            emptyFields = true;
            descriptionEditText.setError("Skriv in en beskrivning");
        }

        if(price.isEmpty())
        {
            emptyFields = true;
            priceEditText.setError("Skriv in ett pris");
        }

        if(emptyFields) return true;
        else return false;
    }

    private void submit()
    {
        if(emptyFields())   return;

        HttpRequest.Response response = new HttpRequest.Response() {
            @Override
            public void processFinish(String output, int status) {
                System.out.println(status);

                if(status != 200)
                {
                    Toast.makeText(getActivity(), "Kunde inte genomföra redigera, var vänlig försök igen. Felkod: " + status,
                            Toast.LENGTH_SHORT
                    ).show();
                }

                callback.onChanged(itemData);

                EditDinnerMenuPopup.super.dismiss();
            }
        };
        HttpRequest httpRequest = new HttpRequest(response);
        httpRequest.setRequestMethod("POST");

        updateItemData();

        String payload = itemData.toJSONString();
        System.out.println(payload);
        httpRequest.setPayload(payload);

        httpRequest.execute(DatabaseURL.insertItem);
    }


}
