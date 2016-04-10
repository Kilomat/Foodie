package foodie.project_training.com.foodie.momentum.model;

import android.widget.ImageView;


/**
 * Created by beau- on 30/03/2016.
 */
public class Momentum {

    private int  id;
    private int     type;
    private String  title;
    private String  username;
    private int profile;
    private int picture;
    private String  comment;
    private String  date;
    private String  place;
    private boolean myLike;
    private int     like;

    public Momentum(int id, int type, String title, String username, int profile, int picture, String comment, String date, String place, boolean myLike, int like) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.username = username;
        this.profile = profile;
        this.picture = picture;
        this.comment = comment;
        this.date = date;
        this.place = place;
        this.myLike = myLike;
        this.like = like;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() { return type; }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isMyLike() {
        return myLike;
    }

    public void setMyLike(boolean myLike) {
        this.myLike = myLike;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
