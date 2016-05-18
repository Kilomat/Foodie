package foodie.project_training.com.foodie.Restaurant.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import foodie.project_training.com.foodie.Coupon.model.Coupon;
import foodie.project_training.com.foodie.Discount.model.Discount;
import foodie.project_training.com.foodie.Dish.model.Dish;
import foodie.project_training.com.foodie.Menu.model.Menu;
import foodie.project_training.com.foodie.Momentum.model.Momentum;
import foodie.project_training.com.foodie.Position.model.Position;
import foodie.project_training.com.foodie.Seat.model.Seat;
import foodie.project_training.com.foodie.User.model.User;

/**
 * Created by beau- on 10/04/2016.
 */
public class Restaurant implements Serializable {

    @SerializedName("_id")
    private Object  id;

    @SerializedName("userId")
    private String  userId;

    @SerializedName("name")
    private String  name;

    @SerializedName("adress")
    private String  address;

    @SerializedName("city")
    private String  city;

    @SerializedName("description")
    private String  description;

    @SerializedName("places")
    private int     place;

    private int     reservedPlaces;

    public Restaurant() {}

    public Restaurant(Object id, String userId, String name, String address, String city, String description, int place) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.description = description;
        this.place = place;
    }

    public Restaurant(Object id, String userId, String name, String address, String city, String description, int place, int reservedPlaces) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.description = description;
        this.place = place;
        this.reservedPlaces = reservedPlaces;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getReservedPlaces() {
        return reservedPlaces;
    }

    public void setReservedPlaces(int reservedPlaces) {
        this.reservedPlaces = reservedPlaces;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", place=" + place +
                ", reservedPlaces=" + reservedPlaces +
                '}';
    }
}
