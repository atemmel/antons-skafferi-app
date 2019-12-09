package se.grupp1.antonsskafferi.data;

public class ScheduleItemData {

    private int date;
    private String name;
    private int startTime;
    private int endTime;


    public ScheduleItemData(int date, String name, int startTime, int endTime)
    {
        this.date = date;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;

    }

    public int getDate()
    {
        return date;
    }

    public String getName()
    {
        return name;
    }

    public void setStartTime(int startTime)
    {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime)
    {
        this.endTime = endTime;
    }

    public int getStartTime()
    {
        return startTime;
    }

    public int getEndTime()
    {
        return endTime;
    }
}
