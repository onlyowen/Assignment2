import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ManagementBoard extends JFrame {
    int width = 1200;
    int height = 800;

    JFrame jFrame;
    TextArea textAreaUserId;
    JButton buttonAddUser;
    JButton buttonCheckUser;
    JButton buttonCheckGroup;
    TextArea textAreaGroupId;
    JButton buttonAddGroup;
    JButton buttonOpenUserView;
    JButton buttonLastUpdateUser;

    JButton buttonShowUserTotal;
    JButton buttonShowGroupTotal;
    JButton buttonShowMessageTotal;
    JButton buttonShowPositivePercentage;
    JTree tree;
//    Set<String> userIdSet;
    Set<Group> groupIdSet;
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
    User user1 = new User("stu1",dataTime(),dataTime());
    User user2 = new User("stu2",dataTime(),dataTime());
    User user3 = new User("stu3",dataTime(),dataTime());

    User user8 = new User("stu8",dataTime(),dataTime());
    User user9 = new User("stu9",dataTime(),dataTime());
    User user10 = new User("stu10",dataTime(),dataTime());

    public void createTreeView(){

        for (int i = 0;i < groupIdSet.size();i++){

        }
        Group gp1 = new Group("Css",dataTime());
        Group gp2 = new Group("Cs2",dataTime());
        Group gp3 = new Group("root",dataTime());
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(gp1);

        User john = new User("John",dataTime(),dataTime());
        node1.add(new DefaultMutableTreeNode(user1));
        node1.add(new DefaultMutableTreeNode(user2));
        node1.add(new DefaultMutableTreeNode(user3));

        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode(gp2);

        node2.add(new DefaultMutableTreeNode(user8));
        node2.add(new DefaultMutableTreeNode(user9));
        node2.add(new DefaultMutableTreeNode(user10));

        DefaultMutableTreeNode top = new DefaultMutableTreeNode(gp3);

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


        groupIdSet.add(gp1);
        groupIdSet.add(gp2);
        groupIdSet.add(gp3);
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
                    Group gp = (Group) object;
                    System.out.println("choose group: " + gp.getName()+"  "+gp.getCreatTime());
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

    void showLastUpdate(){
        List<User> lists = userList;
        SortClass sortClass = new SortClass();
        Collections.sort(lists,sortClass);
        System.out.println("last update user:");
        System.out.println(lists.get(0));

    }

    void checkUser(){

        System.out.println("--check user--");
        String userId = textAreaUserId.getText();
        System.out.println("user id: "+userId+"");
        if(userId.equals("")){
            System.out.println("error: cannot be empty");
            return;
        }
        if(getIndexByUserId(userId) != -1){
            System.out.println("error: user id already exists");
            return;
        }
        if (userId.contains(" ")){
            System.out.println("error: include spaces");
            return;
        }

        System.out.println("success:this id can use!");

    }

    void checkGroup(){
        System.out.println("--check group--");
        String name = textAreaGroupId.getText();
        Group groupId = new Group(name,dataTime());
        if(groupId.equals("")){
            return;
        }
        for (Group group:groupIdSet){
            if(group.getName().equals(groupId.getName())){
                System.out.println("error: group id already exists");
                return;
            }

        }
        if (groupId.getName().contains(" ")){
            System.out.println("error: include spaces");
            return;
        }
        System.out.println("success:this id can use!");

    }


    void addUser(){
        System.out.println("--add user--");
        String userId = textAreaUserId.getText();
        if(userId.equals("")){
            return;
        }
        if(getIndexByUserId(userId) != -1){
            System.out.println("error: group id already exists");
            return;
        }
        if (userId.contains(" ")){
            System.out.println("error: include spaces");
            return ;
        }
        User user = new User(userId,dataTime(),dataTime());
        System.out.println("new user"+user);

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

        String name = textAreaGroupId.getText();
        Group groupId = new Group(name,dataTime());
        if(groupId.equals("")){
            return;
        }

        for (Group group:groupIdSet){
            if(group.getName().equals(groupId.getName())){
                System.out.println("error: group id already exists");
                return;
            }

        }
        if(groupIdSet.contains(groupId.getName())){


        }

        if (groupId.getName().contains(" ")){
            System.out.println("error: include spaces");
            return ;
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

        positiveMessageList=this.listOf("good","great","happy","joyful");
        //Left
        createTreeView();
        //right up
        textAreaUserId = new TextArea();
        textAreaUserId.setBounds(500,10,300,100);
        jFrame.add(textAreaUserId);

        buttonAddUser = new JButton("Add User");
        buttonAddUser.setBounds(500+300,10,150,100);
        buttonAddUser.addActionListener(e -> addUser());
        jFrame.add(buttonAddUser);

        buttonCheckUser = new JButton("Check User");
        buttonCheckUser.setBounds(500+300+150+30,10,150,100);
        buttonCheckUser.addActionListener(e -> checkUser());
        jFrame.add(buttonCheckUser);


        textAreaGroupId = new TextArea();
        textAreaGroupId.setBounds(500,10+100,300,100);
        jFrame.add(textAreaGroupId);

        buttonAddGroup = new JButton("Add Group");
        buttonAddGroup.setBounds(500+300,10+100,150,100);
        buttonAddGroup.addActionListener(e -> addGroup());
        jFrame.add(buttonAddGroup);

        buttonCheckGroup = new JButton("Check Group");
        buttonCheckGroup.setBounds(500+300+180,10+100,150,100);
        buttonCheckGroup.addActionListener(e -> checkGroup());
        jFrame.add(buttonCheckGroup);



        buttonLastUpdateUser = new JButton("Last update user");
        buttonOpenUserView = new JButton("Open User View");
        buttonOpenUserView.setBounds(500,10+100+100+30,300*2,100);
        buttonLastUpdateUser.setBounds(500,10+100+100+150,300*2,100);
        buttonOpenUserView.addActionListener(e -> openUserView());
        buttonLastUpdateUser.addActionListener(e->showLastUpdate());
        jFrame.add(buttonOpenUserView);
        jFrame.add(buttonLastUpdateUser);

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
        for(Group s:groupIdSet){
            list.add(s.getName());
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
    @SafeVarargs
    public static <T> List<T> listOf(T... elements) {
        List<T> list = new ArrayList<>();
        for (T e : elements)
            list.add(e);
        return Collections.unmodifiableList(list);
    }

    public String  dataTime(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String dateStr = sdf.format(date);
            System.out.println(dateStr);
            return  dateStr;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
