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
}
