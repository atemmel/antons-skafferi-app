package se.grupp1.antonsskafferi.data;

import org.json.JSONException;
import org.json.JSONObject;

public class UserData {
    private String name;
    private Boolean admin;
    private String password;

    public UserData(String name, Boolean admin, String password)
    {
        this.name = name;
        this.admin = admin;
        this.password = password;
    }

    public Boolean getAdmin()
    {
        return admin;
    }

    public String getName()
    {
        return name;
    }

    public String toJSONString()
    {
        JSONObject object = new JSONObject();

        try {
            object.put("name", name);
            object.put("administrator", admin);
            object.put("password", password);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return object.toString();
    }
}

