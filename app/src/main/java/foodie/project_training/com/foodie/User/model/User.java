package foodie.project_training.com.foodie.User.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import foodie.project_training.com.foodie.Coupon.model.Coupon;
import foodie.project_training.com.foodie.Discount.model.Discount;
import foodie.project_training.com.foodie.Momentum.model.Momentum;

/**
 * Created by beau- on 10/04/2016.
 */
public class User {

    @SerializedName("_id")
    private String  id;

    @SerializedName("email")
    private String  email;

    @SerializedName("password")
    private String  password;

    @SerializedName("lastName")
    private String  lastName;

    @SerializedName("firstName")
    private String  firstName;

    @SerializedName("gender")
    private int     gender;

    private int     userType;
    private List<Momentum>  myMomentumList;
    private List<Coupon>    myCouponList;
    private List<Discount>  myDiscountList;

    public User(String id,
                String email,
                String password) {

        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(String id,
                String email,
                String password,
                String lastName,
                String firstName,
                int gender,
                int userType,
                List<Momentum> myMomentumList,
                List<Coupon> myCouponList,
                List<Discount> myDiscountList) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.userType = userType;
        this.myMomentumList = myMomentumList;
        this.myCouponList = myCouponList;
        this.myDiscountList = myDiscountList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public List<Momentum> getMyMomentumList() {
        return myMomentumList;
    }

    public void setMyMomentumList(List<Momentum> myMomentumList) {
        this.myMomentumList = myMomentumList;
    }

    public List<Coupon> getMyCouponList() {
        return myCouponList;
    }

    public void setMyCouponList(List<Coupon> myCouponList) {
        this.myCouponList = myCouponList;
    }

    public List<Discount> getMyDiscountList() {
        return myDiscountList;
    }

    public void setMyDiscountList(List<Discount> myDiscountList) {
        this.myDiscountList = myDiscountList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", gender=" + gender +
                ", userType=" + userType +
                ", myMomentumList=" + myMomentumList +
                ", myCouponList=" + myCouponList +
                ", myDiscountList=" + myDiscountList +
                '}';
    }
}
