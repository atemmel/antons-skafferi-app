package se.grupp1.antonsskafferi.data;


public class MenuItemData
{
    private int id;
    private String title;
    private String description;
    private int price;
    private int categoryId;

    public MenuItemData(int id, String title, String description, int price, int categoryId)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getCategoryId() {
        return categoryId;
    }
}