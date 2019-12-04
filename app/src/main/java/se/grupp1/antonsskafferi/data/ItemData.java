package se.grupp1.antonsskafferi.data;

public class ItemData
{
    private int id;
    private String title;
    private int amount;
    private String note;

    public ItemData(int id, String title, int amount, String note)
    {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.note = note;
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public void increaseAmount()
    {
        amount++;
    }

    public void decreaseAmount()
    {
        amount--;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public int getAmount()
    {
        return amount;
    }

    public String getNote()
    {
        return note;
    }
}
