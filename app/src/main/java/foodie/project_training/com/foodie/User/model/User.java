package foodie.project_training.com.foodie.User.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import foodie.project_training.com.foodie.Coupon.model.Coupon;
import foodie.project_training.com.foodie.Discount.model.Discount;
import foodie.project_training.com.foodie.Momentum.model.Momentum;

/**
 * Created by beau- on 10/04/2016.
 */
public class User implements Serializable {

    @SerializedName("id")
    private String  id;

    @SerializedName("email")
    private String  email;

    @SerializedName("password")
    private String  password;

    @SerializedName("lastName")
    private String  lastName;

    @SerializedName("firstName")
    private String  firstName;

    @SerializedName("birthday")
    private String  birthday;

    @SerializedName("adress")
    private String  address;

    @SerializedName("city")
    private String  city;

    @SerializedName("zipcode")
    private int  zipcode;

    @SerializedName("bio")
    private String  bio;

    @SerializedName("gender")
    private String  gender;

    @SerializedName("phone")
    private String  phone;

    @SerializedName("notification")
    private String  notification;



    private int     userType;
    private List<Momentum>  myMomentumList;
    private List<Coupon>    myCouponList;
    private List<Discount>  myDiscountList;

    public User() {}

    public User(String email,
                String password) {
        this.email = email;
        this.password = password;
    }

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
                String birthday,
                String address,
                String city,
                int zipcode,
                String phone,
                String gender,
                int userType,
                List<Momentum> myMomentumList,
                List<Coupon> myCouponList,
                List<Discount> myDiscountList) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
        this.phone = phone;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
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
                ", birthday='" + birthday + '\'' +
                ", adress='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zipcode=" + zipcode +
                ", bio='" + bio + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", notification='" + notification + '\'' +
                ", userType=" + userType +
                ", myMomentumList=" + myMomentumList +
                ", myCouponList=" + myCouponList +
                ", myDiscountList=" + myDiscountList +
                '}';
    }
}
