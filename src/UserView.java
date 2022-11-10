import javax.swing.*;
import javax.swing.text.html.ListView;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserView extends JFrame {
    User user;
    JFrame jFrame;

    TextArea textAreaUserId;
    JButton buttonFollowUser;
    JList listViewCurrentFollow;

    TextArea TweetMessage;
    JButton buttonPostTweet;

    JList listViewNewsFeed;
    JButton closeButton;
    ManagementBoard managementBoard;


    void setMmanagementBoard(ManagementBoard managementBoard) {
        this.managementBoard = managementBoard;
    }
    void followUser(){
        String id = textAreaUserId.getText();
        int index = managementBoard.getIndexByUserId(id);
        if(index == -1 || id.equals(user.id)){
            JOptionPane.showMessageDialog(jFrame,"User not found");
        }else{
            User searchUser = managementBoard.userList.get(index);
            this.user.follow(searchUser);
            textAreaUserId.setText("");
            DefaultListModel followListModel = new DefaultListModel();
            followListModel.addElement("Current Following");
            for(User s : user.followList){
                followListModel.addElement(String.format(" - %s", s.id));
            }
            listViewCurrentFollow.setModel(followListModel);
            listViewCurrentFollow.updateUI();
            updateNewsFeed(true);
        }
    }
    void init(){

        textAreaUserId = new TextArea();
        textAreaUserId.setBounds(10,10,200,50);

        jFrame.add(textAreaUserId);


        buttonFollowUser = new JButton("Follow User");
        buttonFollowUser.setBounds(200,10,200,50);
        buttonFollowUser.addActionListener(e -> followUser());
        jFrame.add(buttonFollowUser);



        DefaultListModel followListModel = new DefaultListModel();
        followListModel.addElement("Current Following");
        for(User s : user.followList){
            followListModel.addElement(String.format(" - %s", s.id));
        }
        listViewCurrentFollow = new JList(followListModel);
        listViewCurrentFollow.setBounds(10,70,390,120);
        jFrame.add(listViewCurrentFollow);

        TweetMessage = new TextArea();
        TweetMessage.setBounds(10,200,200,50);
        jFrame.add(TweetMessage);

        buttonPostTweet = new JButton("Post Tweet");
        buttonPostTweet.setBounds(200,200,200,50);
        buttonPostTweet.addActionListener(e-> sendMessage());
        jFrame.add(buttonPostTweet);

        DefaultListModel feedListModel = new DefaultListModel();
//        feedListModel.addElement("News Feed");
//        List<Message>newsfeed=new ArrayList<>();
//        for(User s : user.followList){
//            newsfeed.addAll(s.messages);
//        }
//        newsfeed.addAll(user.messages);
//        newsfeed.sort((a,b)-> (int) (b.time-a.time));
//        for(Message m : newsfeed){
//            feedListModel.addElement(String.format(" - %s:%s", m.getSenderId(), m.getData()));
//        }

        listViewNewsFeed = new JList(followListModel);
        updateNewsFeed(false);
        listViewNewsFeed.setBounds(10,260,390,120);
        jFrame.add(listViewNewsFeed);



        closeButton = new JButton("Close");
        closeButton.setBounds(0,400,400,30);
        closeButton.addActionListener(e -> jFrame.setVisible(false));
        jFrame.add(closeButton);
    }
    void updateNewsFeed(boolean  active){
        System.out.println("updateNewsFeed: "+user.id+" active: "+active );
        DefaultListModel feedListModel = new DefaultListModel();
        feedListModel.addElement("News Feed");
        List<Message>newsfeed=new ArrayList<>();
        for(User s : user.followList){
            newsfeed.addAll(s.messages);
        }
        newsfeed.addAll(user.messages);
        newsfeed.sort((a,b)-> (int) (b.time-a.time));
        for(Message m : newsfeed){
            feedListModel.addElement(String.format(" - %s:%s", m.getSenderId(), m.getData()));
        }
        listViewNewsFeed.setModel(feedListModel);
        listViewNewsFeed.updateUI();
        if(active){
            managementBoard.updateAllFollowers(user);
        }
    }
    void sendMessage(){
        System.out.println("-- send message --");
        String data = TweetMessage.getText();
        if(data.length() > 0){
            user.sendMessage(data);
            TweetMessage.setText("");
        }
        updateNewsFeed(true);

    }
    public UserView(User user,boolean isVisiable){
        jFrame = this;
        this.user = user;
        this.setTitle(user.id);
        setLayout(null);
        init();
        this.setSize(420,500);
        this.setVisible(isVisiable);
    }
//    public static void main(String[] args) {
//        User currentUser = new User("Tom");
//        User followUser = new User("Bob");
//        currentUser.follow(followUser);
//
//        followUser.sendMessage("happy");
//        UserView userView = new UserView(currentUser,true);
//
//    }
}
