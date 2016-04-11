package foodie.project_training.com.foodie.User.model;

import java.util.List;

import foodie.project_training.com.foodie.Coupon.model.Coupon;
import foodie.project_training.com.foodie.Discount.model.Discount;
import foodie.project_training.com.foodie.Momentum.model.Momentum;

/**
 * Created by beau- on 10/04/2016.
 */
public class User {

    private String  id;
    private String  username;
    private String  password;
    private String  lastName;
    private String  fullName;
    private int     sex;
    private int     userType;
    private List<Momentum>  myMomentumList;
    private List<Coupon>    myCouponList;
    private List<Discount>  myDiscountList;

    public User(String id,
                String username,
                String password,
                String lastName,
                String fullName,
                int sex,
                int userType,
                List<Momentum> myMomentumList,
                List<Coupon> myCouponList,
                List<Discount> myDiscountList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.fullName = fullName;
        this.sex = sex;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", sex=" + sex +
                ", userType=" + userType +
                ", myMomentumList=" + myMomentumList +
                ", myCouponList=" + myCouponList +
                ", myDiscountList=" + myDiscountList +
                '}';
    }
}
