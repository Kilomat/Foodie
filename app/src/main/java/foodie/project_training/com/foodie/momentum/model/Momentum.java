package foodie.project_training.com.foodie.Momentum.model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by beau- on 30/03/2016.
 */
public class Momentum {

    @SerializedName("_id")
    private String  id;

    @SerializedName("userId")
    private String  userId;

    @SerializedName("content")
    private String  content;

    @SerializedName("location")
    private String  location;

    @SerializedName("postedAt")
    private String  postedAt;

    public Momentum() {}

    public Momentum(String id, String userId, String content, String location, String postedAt) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.location = location;
        this.postedAt = postedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public String toString() {
        return "Momentum{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", location='" + location + '\'' +
                ", postedAt='" + postedAt + '\'' +
                '}';
    }
}
