import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    String id;
    String creatTime;
    String updateTime;
    List<User> followList;
    List<User> fanList;
    List<Message>messages;

    public User() {
    }

    public User(String id, String creatTime, String updateTime) {
        this.id = id;
        this.creatTime = creatTime;
        this.updateTime= updateTime;
        followList = new ArrayList<>();
        fanList = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public User(String id) {
        this.id = id;
    }
    public void sendMessage(String data){
        Message message = new Message(id,data);
        messages.add(message);
    }
    public void follow(User user){
        followList.add(user);
        user.fanList.add(this);
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", creatTime='" + creatTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}

