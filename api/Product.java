package api;
import java.io.Serializable;


public class Product implements Serializable {

    private String id;
    private String title;
    private String description;
    private String category;
    private String subcategory;
    private String unit;
    private double price;
    private int quantity;
    private int orderFrequency;


    public Product(String title, String description, String category, String subcategory, double price, int quantity,String unit) {
        this.id = generateUniqueId();
        this.title = title;
        this.description = description;
        this.category = category;
        this.subcategory = subcategory;
        this.price = price;
        this.quantity = quantity;
        this.orderFrequency=0;
        this.unit=unit;


    }
    // Random Δημιουργία ID
    private String generateUniqueId() {
        return java.util.UUID.randomUUID().toString();
    }
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subcategory;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
    public int getOrderFrequency() {
        return orderFrequency;
    }

    public String getUnit() {
      return unit;
    }

    public void setTitle(String name) {
        this.title = name;
    }
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setSubCategory(String subCategory) {
        this.subcategory = subCategory;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void increaseOrderFrequency() {
        orderFrequency++;
    }


    @Override
    public String toString() {
        return "Product{" +
                "ID='" + id + '\'' +
                ", Τίτλος='" + title + '\'' +
                ", Περιγραφή='" + description + '\'' +
                ", Κατηγορία='" + category + '\'' +
                ", Υποκατηγορία='" + subcategory + '\'' +
                ", Τιμή=" + price +
                ", Ποσότητα=" + quantity +
                ", Συχνότητα παραγγελίας=" + orderFrequency +
                '}';
    }
        }


