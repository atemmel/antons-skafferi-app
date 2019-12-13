package se.grupp1.antonsskafferi.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.lib.DatabaseURL;
import se.grupp1.antonsskafferi.lib.VolleyRequestService;

import static android.app.Activity.RESULT_OK;


public class EventFragment extends Fragment
{
    ImageView imageView;
    private static final int PICK_IMAGE = 100;
    Bitmap imageBitMap;
    Uri imageBitMap1;
    String date;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.fragment_event, container, false);
        imageView = root.findViewById(R.id.imageView);

        root.findViewById(R.id.addEventButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //sendImage();
                openGallery();

            }
        });


        root.findViewById(R.id.dateEventInputText).setOnClickListener(new View.OnClickListener()
        {
            Calendar calendar = Calendar.getInstance();


            @Override
            public void onClick(View v)

            {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date =  year + "-" + (month + 1 ) + "-" + day;
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }


        });

        root.findViewById(R.id.addEventButton2).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText titleEditText = root.findViewById(R.id.titleEventInputText);
                String title = titleEditText.getText().toString();

                EditText descriptionEditText = root.findViewById(R.id.descriptionEventInputText);
                String description = descriptionEditText.getText().toString();




                boolean emptyInputField = false;

                if(title.isEmpty()){
                    emptyInputField = true;

                    titleEditText.setError("Lägg till en titel");
                }

                if(description.isEmpty()){
                    emptyInputField = true;
                    descriptionEditText.setError("Lägg till en beskrivning");

                }


                if(emptyInputField) return;
                //openGallery();
                //String date = "hej";
                //(title, description, date);
                sendImage(title,  date);
            }
        });


        return root;
    }

    public void addEvent(){

    }

    public void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
                //imageBitMap = (Bitmap) data.getExtras().get("data");
                imageBitMap1 = data.getData();
                imageBitMap =  MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageBitMap1);
                imageView.setImageBitmap(imageBitMap);
                //imageView.setImageURI(imageBitMap);
            }
        }catch(Exception E){
            Toast.makeText(getActivity(), "DEN ÄR PAJ ", Toast.LENGTH_LONG);
            E.getStackTrace();
        }
    }

    public void sendImage(String title, String date){

        if(imageView.getDrawable() != null){


            //System.out.println("TITLE: " + title + "DESCRIPTION: " + description + "DATE: " + date);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitMap.compress(Bitmap.CompressFormat.JPEG, 0, baos);
            byte[] imageBytes = baos.toByteArray();

            //String image = get64BaseImage(imageBitMap);
            Map<String, Object> params = new HashMap<>();
            String name = "bild";
            params.put("title", title);
            params.put("date", date);
            params.put("imageName", name);
            params.put("image", imageBytes);


            JSONObject obj = new JSONObject(params);

            System.out.println(obj);

            //String URL = "http://10.250.117.161:8080/upload";
            String URL = DatabaseURL.testingImage;

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    URL,
                    new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast toast = Toast.makeText(getActivity(), "Event skapad." , Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();

                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.getStackTrace();
                    Toast toast = Toast.makeText(getActivity(), "ERROR: " + error, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            });

            request.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue queue = VolleyRequestService.getInstance(getContext()).getRequestQueue();

            queue.add(request);

            //VolleyRequestService.getInstance(getContext()).getRequestQueue().add(request);

        }

        Toast toast = Toast.makeText(getActivity(), "ERROR: Det finns ingen bild." , Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();



    }

    public String get64BaseImage (Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}