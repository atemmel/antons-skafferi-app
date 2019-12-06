package se.grupp1.antonsskafferi.popups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private MenuItemData itemData;

    private Map<Integer, String> category_map = new HashMap<>();
    private Map<String, Integer> inverse_category_map = new HashMap<>();


    public EditDinnerMenuPopup(MenuItemData itemData)
    {
        this.itemData = itemData;
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

        return v;
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

        //TODO: Move this so categories are only loaded once, not every time an item is edited
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

        TextView price = getView().findViewById(R.id.editPriceText);

        price.setText(Integer.toString(itemData.getPrice()));
    }
}
