package se.grupp1.antonsskafferi.data;

import org.json.JSONException;
import org.json.JSONObject;

public class ScheduleData {

    private int id = -1;
    private String date;
    private String name;
    private String startTime;
    private String endTime;


    public ScheduleData(int id, String date, String name, String startTime, String endTime)
    {
        this.id = id;
        this.date = date;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;

    }
    public ScheduleData(String date, String name, String startTime, String endTime)
    {
        this.date = date;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;

    }
    public ScheduleData(String name)
    {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getDate()
    {
        return date;
    }

    public String getName()
    {
        return name;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public String toJSONString()
    {
        JSONObject object = new JSONObject();

        try {

            if(id > -1) object.put("employeeid", id);

            object.put("date", date);
            object.put("name", name);
            object.put("starttime", startTime);
            object.put("endtime", endTime);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return object.toString();
    }
}
