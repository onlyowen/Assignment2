import java.util.ArrayList;
import java.util.List;

public class User {
    String id;
    List<User> followList;
    List<User> fanList;
    List<Message>messages;

    public User(String id) {
        this.id = id;
        followList = new ArrayList<>();
        fanList = new ArrayList<>();
        messages = new ArrayList<>();
    }
    public void sendMessage(String data){
        Message message = new Message(id,data);
        messages.add(message);
    }
    public void follow(User user){
        followList.add(user);
        user.fanList.add(this);
    }

    @Override
    public String toString() {
        return id;
    }
}

