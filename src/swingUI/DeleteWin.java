package swingUI;

import dao.GradeDao;
import dao.StudentDao;
import javafx.scene.control.ButtonType;
import sun.font.BidiUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashSet;

/**
 * @author Delta
 * Created in 2021-07-11 18:04
 */
public class DeleteWin extends JDialog {
    public DeleteWin(JFrame parent, HashSet<CenterNodeInfo> NodesID) {
        super(parent);
        initialize(NodesID);
        this.setVisible(true);
    }

    private void initialize(HashSet<CenterNodeInfo> NodesID) {
        this.setLayout(new BorderLayout());
        this.setIconImage(new ImageIcon(getClass().getResource("/icon/01.jpg")).getImage());
        this.setSize(400, 150);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(236, 255, 255));

        //--------------------JCheckBox--------------------
        DelCheckBox delStudent = new DelCheckBox("移除该学生");
        DelCheckBox delClass = new DelCheckBox("分班");
        DelCheckBox delElective = new DelCheckBox("选课");
        DelCheckBox delGrade = new DelCheckBox("成绩");

        //--------------------BoxActionListener
        {
            delStudent.addActionListener(e -> {
                if (delStudent.isSelected()) {
                    delClass.setSelected(false);
                    delElective.setSelected(false);
                    delGrade.setSelected(false);
                    delClass.setEnabled(false);
                    delElective.setEnabled(false);
                    delGrade.setEnabled(false);
                } else {
                    delClass.setEnabled(true);
                    delElective.setEnabled(true);
                    delGrade.setEnabled(true);
                }
            });
            delElective.addActionListener(e -> {
                if (delElective.isSelected()) {
                    delGrade.setSelected(false);
                    delGrade.setEnabled(false);
                } else {
                    delGrade.setEnabled(true);
                }
            });
        }

        transparentPanel buttonPanel = new transparentPanel();
        buttonPanel.add(delStudent);
        buttonPanel.add(delClass);
        buttonPanel.add(delElective);
        buttonPanel.add(delGrade);
        buttonPanel.setBorder(new TitledBorder("移除学生信息"));

        jPanel.add(buttonPanel);
        {
            JButton delButton = new JButton("移除");
            delButton.setBackground(new Color(153, 37, 37));
            delButton.setFont(new Font("等线", Font.BOLD, 20));
            delButton.setForeground(Color.white);
            delButton.addActionListener(e -> {
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "确认移除?", "",
                                                                            JOptionPane.YES_NO_OPTION,
                                                                            JOptionPane.WARNING_MESSAGE
                )) {
                    for (CenterNodeInfo id : NodesID) {
                        if (delStudent.isSelected()) {
                            StudentDao.delStudents(id.idNumber);
                        } else {
                            if (delClass.isSelected()) StudentDao.delStudentClass(id.idNumber);
                            if (delElective.isSelected()) {
                                GradeDao.delElective(id.idNumber, id.courseID);
                            } else {
                                if (delGrade.isSelected()) GradeDao.delGrade(id.idNumber, id.courseID);
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(this, "删除成功!");
                    this.dispose();
                }

            });
            jPanel.add(delButton);
        }
        this.add(jPanel);
    }

    public static void main(String[] args) {
        new DeleteWin(null, null);
    }
}

class DelCheckBox extends JCheckBox {
    public DelCheckBox(String text) {
        super(text);
        this.setOpaque(false);
        this.setFont(new Font("等线", Font.PLAIN, 20));
    }
}
