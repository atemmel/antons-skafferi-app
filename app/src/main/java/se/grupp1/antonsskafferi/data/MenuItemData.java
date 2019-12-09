package se.grupp1.antonsskafferi.data;


import org.json.JSONException;
import org.json.JSONObject;

public class MenuItemData
{
    private int id = -1;
    private String title = "";
    private String description = "";
    private int price = -1;
    private int categoryId = 1;

    public MenuItemData(){}

    public MenuItemData(String title, String description, int price, int categoryId)
    {
        this.title = title;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public MenuItemData(int id, String title, String description, int price, int categoryId)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String toJSONString()
    {
        JSONObject object = new JSONObject();

        try {

            if(id > -1) object.put("itemid", id);

            object.put("title", title);
            object.put("price", price);
            object.put("description", description);
            object.put("itemcategory", categoryId);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return object.toString();
    }
}