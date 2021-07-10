package swingUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author Delta
 * Created in 2021-07-09 10:37
 */
public class AboutWin extends JDialog {
    public AboutWin(Frame parent) {
        super(parent, "关于");
        initialize();
        this.setVisible(true);
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(400, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel centerPanel = new JPanel(new GridLayout(0, 1));
        centerPanel.setBackground(new Color(206, 255, 255));
        {
            JLabel titleLabel = new JLabel("学生成绩管理系统", SwingConstants.CENTER);
            JLabel jLabel1 = new JLabel("作者: Negi-Delta", SwingConstants.CENTER);
            JLabel jLabel2 = new JLabel("Git: Negi-Delta", SwingConstants.CENTER);

            Font titleFont = new Font("等线", Font.BOLD, 30);
            Font labelFont = new Font("等线", Font.PLAIN, 20);
            titleLabel.setFont(titleFont);
            jLabel1.setFont(labelFont);
            jLabel2.setFont(labelFont);

            titleLabel.setBorder(new TitledBorder(
                    new EmptyBorder(0, 0, 0, 0), "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄",
                    TitledBorder.CENTER, TitledBorder.BOTTOM,
                    new Font("Arial", Font.ITALIC, 10), Color.magenta));
//            titleLabel.setBorder(new CompoundBorder(new TitledBorder(
//                    new EmptyBorder(0, 0, 0, 0), "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄",
//                    TitledBorder.CENTER, TitledBorder.BOTTOM,
//                    new Font("Arial", Font.ITALIC, 10), Color.magenta), null));

            centerPanel.add(titleLabel);
            centerPanel.add(jLabel1);
            centerPanel.add(jLabel2);
        }
        this.add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new AboutWin(null);
    }
}
