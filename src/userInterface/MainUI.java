package userInterface;

import javax.swing.*;
import java.awt.*;

/**
 * @author Delta
 * Created in 2021-07-04 18:14
 */
public class MainUI extends JFrame {
    public MainUI(String winTitle) {
        super(winTitle);
        JPanel mainjp = new JPanel();
        mainjp.setLayout(new BorderLayout());

        /*
        JComboBox jComboBox = new JComboBox();
        Checkbox checkbox = new Checkbox("123456");
        jComboBox.add(checkbox);
        JPanel jPanel2 = new JPanel();
        jPanel2.add(jComboBox);

        mainjp.add(jPanel2, BorderLayout.CENTER);
         */
        this.add(mainjp);
    }
}

class Teststusys {
    public static void main(String[] args) {
        JFrame jFrame = new MainUI("GradesManagerSystem");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(1600,900);
        jFrame.setLocationRelativeTo(null);
//        jFrame.setVisible(true);
        new LoginWin(jFrame);
    }
}
