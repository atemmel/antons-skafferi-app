package se.grupp1.antonsskafferi.data;

public class ItemData {
    private int amount;
    private int id;
    private String title;
    private String description;
    int price;
    int categoryId;


    public ItemData(int amount, int id, String title, String description, int price, int categoryId){
        this.amount = amount;
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public int getId(){
        return id;
    }

    public int getAmount(){
        return this.amount;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public int getPrice(){
        return this.price;
    }

    public int getCategoryId(){
        return this.categoryId;
    }
}
