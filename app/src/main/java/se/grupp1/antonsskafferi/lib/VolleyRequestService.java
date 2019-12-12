package se.grupp1.antonsskafferi.lib;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRequestService {
    private static VolleyRequestService singleton;
    private RequestQueue requestQueue;

    private VolleyRequestService(Context context){
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleyRequestService getInstance(Context context){
        if(singleton == null){
            singleton = new VolleyRequestService(context);
        }
        return singleton;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }

    public void setupQueue(Context context){

    }
}
