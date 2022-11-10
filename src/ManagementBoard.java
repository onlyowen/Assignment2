import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class ManagementBoard extends JFrame {
    int width = 1200;
    int height = 800;

    JFrame jFrame;
    TextArea textAreaUserId;
    JButton buttonAddUser;

    TextArea textAreaGroupId;
    JButton buttonAddGroup;
    JButton buttonOpenUserView;

    JButton buttonShowUserTotal;
    JButton buttonShowGroupTotal;
    JButton buttonShowMessageTotal;
    JButton buttonShowPositivePercentage;
    JTree tree;
//    Set<String> userIdSet;
    Set<String> groupIdSet;
    List<User>userList;
    List<UserView>userViewList;
    List<Message>messageList;
    List<String>positiveMessageList;
    public UserView createUserView(User user){
        UserView userView = new UserView(user,false);
        userView.setMmanagementBoard(this);
        userViewList.add(userView);

        return userView;
    }
    public void createTreeView(){
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("CS356");
        User user1 = new User("stu1");
        User user2 = new User("stu2");
        User user3 = new User("stu3");

        User user8 = new User("stu8");
        User user9 = new User("stu9");
        User user10 = new User("stu10");

        User john = new User("John");
        node1.add(new DefaultMutableTreeNode(user1));
        node1.add(new DefaultMutableTreeNode(user2));
        node1.add(new DefaultMutableTreeNode(user3));

        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("CS356Session01");

        node2.add(new DefaultMutableTreeNode(user8));
        node2.add(new DefaultMutableTreeNode(user9));
        node2.add(new DefaultMutableTreeNode(user10));

        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Root");

        top.add(new DefaultMutableTreeNode(john));
        userList.add(john);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        userList.add(user8);
        userList.add(user9);
        userList.add(user10);


        john.follow(user1);

        System.out.println("Message"+user1.fanList.size());
        createUserView(john);
        createUserView(user1);
        createUserView(user2);
        createUserView(user3);
        createUserView(user8);
        createUserView(user9);
        createUserView(user10);


        groupIdSet.add("CS356");
        groupIdSet.add("CS356Session01");
        groupIdSet.add("Root");
        top.add(node1);
        top.add(node2);
        tree = new JTree(top);
        tree.setBounds(0,10,500,780);
        jFrame.add(tree);
        tree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
                        .getLastSelectedPathComponent();

                if (node == null)
                    return;

                Object object = node.getUserObject();
                if (node.isLeaf()) {
                    User user = (User) object;
                    System.out.println("choose user: " + user.toString());
                }
                else {
                    System.out.println("choose group: " + object.toString());
                }

            }
        });
    }
    int getIndexByUserId(String id){
        for(int i = 0; i < userList.size(); i++){
            if(userList.get(i).id.equals(id)){
                return i;
            }
        }
        return -1;
    }
    void addUser(){
        System.out.println("--add user--");
        String userId = textAreaUserId.getText();
        if(userId.equals("")){
            return;
        }
        if(getIndexByUserId(userId) != -1){
            System.out.println("group id already exists");
            return;
        }
        User user = new User(userId);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null)
            return;
        if (node.isLeaf()) {
            node = (DefaultMutableTreeNode) node.getParent();

        }
        node.add(new DefaultMutableTreeNode(user));
        createUserView(user);
        for(int i=0;i<node.getChildCount();i++){
            if(node.getChildAt(i).toString().equals("Empty")){
                node.remove(i);
            }
        }
        userList.add(user);
        textAreaUserId.setText("");
        tree.updateUI();


    }
    void addGroup(){
        System.out.println("--add group--");
        String groupId = textAreaGroupId.getText();
        if(groupId.equals("")){
            return;
        }
        if(groupIdSet.contains(groupId)){
            System.out.println("group id already exists");
            return;
        }

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null)
            return;
        if (node.isLeaf()) {
            node = (DefaultMutableTreeNode) node.getParent();
        }
//        node.add(new DefaultMutableTreeNode(groupId));
        DefaultMutableTreeNode group = new DefaultMutableTreeNode(groupId);
        node.add(group);
        group.add(new DefaultMutableTreeNode("Empty"));
        groupIdSet.add(groupId);
        textAreaGroupId.setText("");
        tree.updateUI();

    }
    void updateAllFollowers(User user){
        for(int i = 0; i < user.fanList.size(); i++){
            User fan = user.fanList.get(i);
            int index = getIndexByUserId(fan.id);
            if(index != -1){

                UserView userView = userViewList.get(index);
                System.out.println("updateAllFollowers: "+fan.id);
                userView.updateNewsFeed(false);
            }
        }
        messageList.clear();
        for(User u:userList){
            for(Message m:u.messages){
                messageList.add(m);
            }
        }
        messageList.sort((a,b)-> (int) (b.time-a.time));
    }
    void openUserView(){
        System.out.println("openuserview--");
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null)
            return;
        if (node.isLeaf()) {
            User user = (User) node.getUserObject();
            int index = getIndexByUserId(user.id);
            if(index != -1){
                UserView userView = userViewList.get(index);
                userView.setVisible(true);
            }
        }
    }
    void init(){
//        userIdSet = new HashSet<>();
        groupIdSet = new HashSet<>();
        userList = new ArrayList<>();
        messageList = new ArrayList<>();
        userViewList = new ArrayList<>();

        positiveMessageList=List.of("good","great","happy","joyful");
        //Left
        createTreeView();
        //right up
        textAreaUserId = new TextArea();
        textAreaUserId.setBounds(500,10,300,100);
        jFrame.add(textAreaUserId);

        buttonAddUser = new JButton("Add User");
        buttonAddUser.setBounds(500+300,10,300,100);
        buttonAddUser.addActionListener(e -> addUser());
        jFrame.add(buttonAddUser);

        textAreaGroupId = new TextArea();
        textAreaGroupId.setBounds(500,10+100,300,100);
        jFrame.add(textAreaGroupId);

        buttonAddGroup = new JButton("Add Group");
        buttonAddGroup.setBounds(500+300,10+100,300,100);
        buttonAddGroup.addActionListener(e -> addGroup());
        jFrame.add(buttonAddGroup);

        buttonOpenUserView = new JButton("Open User View");
        buttonOpenUserView.setBounds(500,10+100+100+30,300*2,100);
        buttonOpenUserView.addActionListener(e -> openUserView());
        jFrame.add(buttonOpenUserView);

        //right down
        buttonShowUserTotal = new JButton("Show User Total");
        buttonShowUserTotal.setBounds(500,500,300,100);
        buttonShowUserTotal.addActionListener(e -> showUserTotal());
        jFrame.add(buttonShowUserTotal);

        buttonShowGroupTotal = new JButton("Show Group Total");
        buttonShowGroupTotal.setBounds(500+300,500,300,100);
        buttonShowGroupTotal.addActionListener(e -> showGroupTotal());
        jFrame.add(buttonShowGroupTotal);

        buttonShowMessageTotal = new JButton("Show Message Total");
        buttonShowMessageTotal.setBounds(500,500+100,300,100);
        buttonShowMessageTotal.addActionListener(e -> showMessagesTotal());
        jFrame.add(buttonShowMessageTotal);

        buttonShowPositivePercentage = new JButton("Show Positive Percentage");
        buttonShowPositivePercentage.setBounds(500+300,500+100,300,100);
        buttonShowPositivePercentage.addActionListener(e -> showPositivePercentage());
        jFrame.add(buttonShowPositivePercentage);

    }
    void showUserTotal(){
        System.out.println("showUserTotal");
        List<String>list=new ArrayList<>();
        for(User user:userList){
            String id=user.id;
            list.add(id);
        }
        ShowTotalView showTotalView = new ShowTotalView("Show User Total",list);

    }
    void showGroupTotal(){
        System.out.println("showGroupTotal");
        List<String>list=new ArrayList<>();
        for(String s:groupIdSet){
            list.add(s);
        }
        ShowTotalView showTotalView = new ShowTotalView("Show Group Total",list);
    }
    void showMessagesTotal(){
        System.out.println("showMessagesTotal");
        List<String>list=new ArrayList<>();
        for(Message m:messageList){
            list.add(m.toString());
        }
        ShowTotalView showTotalView = new ShowTotalView("Show Messages Total",list);
    }
    void showPositivePercentage() {
        System.out.println("showPositivePercentage");
        List<String> list = new ArrayList<>();
        for(Message message:messageList){
            String data=message.getData();

            for(String s:positiveMessageList){
                if(data.contains(s)){
                    list.add(message.toString());
                    break;
                }
            }

        }
        int totalsize=messageList.size();
        if(totalsize==0){
            totalsize=1;
        }
        ShowTotalView showTotalView = new ShowTotalView(String.format("Positive Percentage %.2f%%",list.size()*100.0/totalsize), list);
    }
    public ManagementBoard(){
        jFrame=this;
        jFrame.setTitle("Management Board");
        setBounds(300,100,width, height);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        setVisible(true);
    }
}
