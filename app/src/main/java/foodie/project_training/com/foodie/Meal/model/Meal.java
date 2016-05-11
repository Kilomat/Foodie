package foodie.project_training.com.foodie.Meal.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by beau- on 11/05/2016.
 */
public class Meal implements Serializable {

    @SerializedName("_id")
    private Object  id;

    @SerializedName("restaurantId")
    private String  restaurantId;

    @SerializedName("title")
    private String  title;

    @SerializedName("description")
    private String  description;

    @SerializedName("price")
    private int     price;

    @SerializedName("city")
    private String  city;

    public Meal() {}

    public Meal(Object id, String restaurantId, String title, String description, int price, String city) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.city = city;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", restaurantId='" + restaurantId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", city='" + city + '\'' +
                '}';
    }
}
