package swingUI;

import dao.ConnectDao;
import dao.CourseDao;
import dao.GradeDao;
import model.Grade;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Delta
 * Created in 2021-07-11 01:37
 */
public class AddEletiveWin extends JDialog {
    public AddEletiveWin() {
        initialize();
        this.setVisible(true);
    }

    TextAreaPanel electiveArea;
    AddWinTagButton addElectiveButton;

    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(600, 400);
        this.setMinimumSize(new Dimension(600, 400));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //----------------------------------------中央栏------------------------------------------------------------栏位------------------
        //--------------------CardPanel----------------------------------------JPanel--------------------
        CardLayout centerCard = new CardLayout();
        JPanel cardPanel = new JPanel(centerCard);

        //--------------------TextAreaPanel----------------------------------------JPanel--------------------
        electiveArea = new TextAreaPanel();
        electiveArea.hintText.setText("请在右侧输入需要添加的选课信息\n\n" +
                                      "添加格式：\n    九位课程ID\n\n以半角或圆角逗号隔开\n" +
                                      "如：\nD20192001，\nD20192002，\nD20192003, \nD20192004"
        );

        cardPanel.add(electiveArea, "Elective");
        this.add(cardPanel, BorderLayout.CENTER);


        //----------------------------------------顶部栏------------------------------------------------------------栏位--------------------
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(0, 0));
        topPanel.setPreferredSize(new Dimension(0, 35));
        topPanel.setBackground(new Color(245, 236, 255));

        //--------------------leftTopJP----------------------------------------JPanel--------------------
        JPanel leftTopJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
        {
            leftTopJP.setOpaque(false);
            addElectiveButton = new AddWinTagButton("选课");

            leftTopJP.add(addElectiveButton);
            addElectiveButton.setBorder(new TitledBorder(
                    new EmptyBorder(0, 0, 0, 0), "▄▄▄▄▄▄",
                    TitledBorder.CENTER, TitledBorder.BOTTOM,
                    new Font("Arial", Font.ITALIC, 10), Color.magenta
            ));
        }
        topPanel.add(leftTopJP, BorderLayout.WEST);

        //--------------------rightTopJP----------------------------------------JPanel--------------------
        JPanel rightTopJP = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 2));
        {
            rightTopJP.setOpaque(false);
            JButton addItemButton = new JButton("添加");
            JButton clearAreaButton = new JButton("清空");
            Color rightButtonColor = new Color(206, 255, 255);
            addItemButton.setBackground(rightButtonColor);
            clearAreaButton.setBackground(rightButtonColor);
            Dimension rightButtonDimension = new Dimension(60, 30);
            addItemButton.setPreferredSize(rightButtonDimension);
            clearAreaButton.setPreferredSize(rightButtonDimension);
            EtchedBorder rightButtonBorder = new EtchedBorder();
            addItemButton.setBorder(rightButtonBorder);
            clearAreaButton.setBorder(rightButtonBorder);
            {
                //--------------------RightButtonListener----------------------------------------Listener--------------------
                clearAreaButton.addActionListener(e -> {
                    electiveArea.addItemArea.setText("");
                });
                addItemButton.addActionListener(e -> {
                    //--------------------SQL Here--------------------
                    Switch:
                    {
                        String[] electives = electiveArea.splitArea();
                        for (String eletive : electives) {
                            if (!eletive.matches("D\\d{8}")) {
                                JOptionPane.showMessageDialog(AddEletiveWin.this, "存在格式错误：\n" + eletive, "",
                                                              JOptionPane.ERROR_MESSAGE
                                );
                                break Switch;
                            }
                        }
                        ArrayList<Grade> eletiveList = new ArrayList<>();
                        for (String eletive : electives) {
                            eletiveList.add(new Grade(MainWin.currentUser.getIdNumber(), eletive, null));
                        }
                        ArrayList<Integer> existEletive = GradeDao.containGrade(eletiveList);
                        if (existEletive.size() > 0) {
                            StringBuffer sb = new StringBuffer();
                            for (Integer integer : existEletive) {
                                sb.append(electives[integer]);
                            }
                            JOptionPane.showMessageDialog(AddEletiveWin.this,
                                                          "以下选课已存在：\n" + sb.toString(), "",
                                                          JOptionPane.WARNING_MESSAGE
                            );
                            break Switch;
                        }
                        for (Grade grade : eletiveList) {
                            if (!CourseDao.containCourse(grade.getCourseID())) {
                                JOptionPane.showMessageDialog(AddEletiveWin.this,
                                                              "以下课程不存在：\n" +
                                                              grade.getCourseID(), "",
                                                              JOptionPane.ERROR_MESSAGE
                                );
                                break Switch;
                            }
                        }
                        GradeDao.addGrade(eletiveList);
                        JOptionPane.showMessageDialog(AddEletiveWin.this, "添加成功！");
                    }
                });
            }
            rightTopJP.add(clearAreaButton);
            rightTopJP.add(addItemButton);
        }
        topPanel.add(rightTopJP, BorderLayout.EAST);

        this.add(topPanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        new AddEletiveWin();
    }
}
