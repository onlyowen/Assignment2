import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ShowTotalView extends JFrame {
    JFrame jFrame;
    TextArea textArea;
    List<String> list;
    void init(){
        textArea = new TextArea();
        textArea.setBounds(0,0,400,400);
        jFrame.add(textArea);

        textArea.setFont(new Font("Calibri", Font.PLAIN, 20));
        for (String s : list) {
            textArea.append(s + "\n");
        }

    }
    public ShowTotalView(String title, List<String> list){
        jFrame = this;
        this.list = list;
        this.setTitle(title);
        setLayout(null);
        this.setBounds(400,200,400,400);
        init();
        this.setVisible(true);
    }

//    public static void main(String[] args) {
//        //test case
//        List<String>l= List.of("a","b","c","b","c","b","c","b","c","b","c","b","c","b","c","b","c","b","c"
//                ,"b","c","b","c","b","c","b","c","aaaa","BBBB","aaaa","BBBB","aaaa","BBBB","aaaa","BBBB","aaaa","BBBB"
//                ,"aaaa","BBBB","aaaa","BBBB","aaaa","BBBB","aaaa","BBBB","aaaa","BBBB");
//        ShowTotalView showTotalView = new ShowTotalView("Show User Total", l);
//    }
}
