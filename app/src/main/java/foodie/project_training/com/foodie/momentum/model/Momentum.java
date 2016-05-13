package foodie.project_training.com.foodie.Momentum.model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by beau- on 30/03/2016.
 */
public class Momentum {

    @SerializedName("_id")
    private Object  id;

    @SerializedName("userId")
    private String  userId;

    @SerializedName("content")
    private String  content;

    @SerializedName("location")
    private String  location;

    @SerializedName("postedAt")
    private String  postedAt;

    @SerializedName("deleted")
    private boolean deleted;

    public Momentum() {}

    public Momentum(Object id,
                    String userId,
                    String content,
                    String location,
                    String postedAt,
                    boolean deleted) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.location = location;
        this.postedAt = postedAt;
        this.deleted = deleted;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Momentum{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", location='" + location + '\'' +
                ", postedAt='" + postedAt + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
