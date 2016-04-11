package foodie.project_training.com.foodie.restaurant.model;

import com.google.gson.annotations.SerializedName;

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
public class Restaurant {

    
    private String  id;
    private String  name;
    private User    manager;
    private int     phoneNumber;
    private List<Menu>  menuList;
    private List<Dish>  dishList;
    private List<Seat>  seatList;
    private List<Coupon> couponList;
    private List<Discount> discountList;
    private List<Momentum>  momentumList;
    private Position position;

    public Restaurant(String id,
                      String name,
                      User manager,
                      int phoneNumber,
                      List<Menu> menuList,
                      List<Dish> dishList,
                      List<Seat> seatList,
                      List<Coupon> couponList,
                      List<Discount> discountList,
                      List<Momentum> momentumList,
                      Position position) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.phoneNumber = phoneNumber;
        this.menuList = menuList;
        this.dishList = dishList;
        this.seatList = seatList;
        this.couponList = couponList;
        this.discountList = discountList;
        this.momentumList = momentumList;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }

    public List<Discount> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(List<Discount> discountList) {
        this.discountList = discountList;
    }

    public List<Momentum> getMomentumList() {
        return momentumList;
    }

    public void setMomentumList(List<Momentum> momentumList) {
        this.momentumList = momentumList;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", manager=" + manager +
                ", phoneNumber=" + phoneNumber +
                ", menuList=" + menuList +
                ", dishList=" + dishList +
                ", seatList=" + seatList +
                ", couponList=" + couponList +
                ", discountList=" + discountList +
                ", momentumList=" + momentumList +
                ", position=" + position +
                '}';
    }
}
