package swingUI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * @author Delta
 * Created in 2021-07-08 17:48
 */
public class AddItemWin extends JDialog {

    public AddItemWin(JFrame parent) {
        super(parent, "添加项目");
        initialize();
        this.setVisible(true);
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(600, 400);
        this.setMinimumSize(new Dimension(600, 400));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        /*----------------------------------------中央栏----------------------------------------*//*--------------------栏位------------------*/
        /*--------------------CardPanel--------------------*//*--------------------JPanel--------------------*/
        CardLayout centerCard = new CardLayout();
        JPanel cardPanel = new JPanel(centerCard);

        /*--------------------TextAreaPanel--------------------*//*--------------------JPanel--------------------*/
        TextAreaPanel courseArea = new TextAreaPanel();
        TextAreaPanel classArea = new TextAreaPanel();
        TextAreaPanel studentArea = new TextAreaPanel();
        TextAreaPanel electiveArea = new TextAreaPanel();
        courseArea.hintText.setText("请在右侧输入需要添加的课程信息\n\n" +
                "添加格式：\n    九位课程ID + 课程名称\n\n以逗号隔开\n" +
                "如：\nD20192001高等数学，\nD20192002Java，\nD20192003C++, \nD20192004C#"
        );
        classArea.hintText.setText("请在右侧输入需要添加的班级信息\n\n" +
                "添加格式：\n    九位班级ID + 班级名称\n\n以逗号隔开\n" +
                "如：\nC20192001虚拟现实191，\nD20192002虚拟现实192，\nD20192003虚拟现实193, \nD20192004虚拟现实194"
        );
        studentArea.hintText.setText("请在右侧输入需要添加的学生信息\n\n" + "添加格式：\n    九位学生ID + 姓名\n\n以逗号隔开\n" + "如：\nS20192001刘一，\nS20192002陈二，\nS20192003张三, \nS20192004李四");
        electiveArea.hintText.setText("请在右侧输入需要添加的选课信息\n\n" +
                "添加格式：\n    学生ID + 多个课程ID\n\n以逗号隔开\n" +
                "如：\nS20192001D20192001D20192002，" +
                "\nS20192002D20192003，" +
                "\nS20192003D20192001D20192003, " +
                "\nS20192004D20192002"
        );

        cardPanel.add(courseArea, "Course");
        cardPanel.add(classArea, "Class");
        cardPanel.add(studentArea, "Student");
        cardPanel.add(electiveArea, "Elective");
        this.add(cardPanel, BorderLayout.CENTER);


        /*----------------------------------------顶部栏----------------------------------------*//*--------------------栏位--------------------*/
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(0, 0));
        topPanel.setPreferredSize(new Dimension(0, 35));
        topPanel.setBackground(new Color(245, 236, 255));

        /*--------------------leftTopJP--------------------*//*--------------------JPanel--------------------*/
        JPanel leftTopJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
        {
            leftTopJP.setOpaque(false);
            AddWinTopButton addCourseButton = new AddWinTopButton("课程");
            AddWinTopButton addClassButton = new AddWinTopButton("班级");
            AddWinTopButton addStudentButton = new AddWinTopButton("学生");
            AddWinTopButton addElectiveButton = new AddWinTopButton("选课");

            /*--------------------TagButtonListener--------------------*//*--------------------Listener--------------------*/
            addCourseButton.addActionListener(e -> {
                if (addCourseButton.isSelected()) {
                    resetBorder(addCourseButton, addClassButton, addStudentButton, addElectiveButton);
                    centerCard.show(cardPanel, "Course");
                }
            });
            addClassButton.addActionListener(e -> {
                if (addClassButton.isSelected()) {
                    resetBorder(addClassButton, addCourseButton, addStudentButton, addElectiveButton);
                    centerCard.show(cardPanel, "Class");
                }
            });
            addStudentButton.addActionListener(e -> {
                if (addStudentButton.isSelected()) {
                    resetBorder(addStudentButton, addCourseButton, addClassButton, addElectiveButton);
                    centerCard.show(cardPanel, "Student");
                }
            });
            addElectiveButton.addActionListener(e -> {
                if (addElectiveButton.isSelected()) {
                    resetBorder(addElectiveButton, addCourseButton, addClassButton, addStudentButton);
                    centerCard.show(cardPanel, "Elective");
                }
            });
            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(addCourseButton);
            buttonGroup.add(addClassButton);
            buttonGroup.add(addStudentButton);
            buttonGroup.add(addElectiveButton);
            leftTopJP.add(addCourseButton);
            leftTopJP.add(addClassButton);
            leftTopJP.add(addStudentButton);
            leftTopJP.add(addElectiveButton);
            resetBorder(addCourseButton, addClassButton, addStudentButton, addElectiveButton);
        }
        topPanel.add(leftTopJP, BorderLayout.WEST);

        /*--------------------rightTopJP--------------------*//*--------------------JPanel--------------------*/
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
                /*--------------------RightButtonListener--------------------*//*--------------------Listener--------------------*/
                clearAreaButton.addActionListener(e -> {
                    courseArea.addItemArea.setText("");
                    classArea.addItemArea.setText("");
                    studentArea.addItemArea.setText("");
                    electiveArea.addItemArea.setText("");
                });
                addItemButton.addActionListener(e -> {
                    /*--------------------SQL Here--------------------*/
                });
            }
            rightTopJP.add(clearAreaButton);
            rightTopJP.add(addItemButton);
        }
        topPanel.add(rightTopJP, BorderLayout.EAST);

        this.add(topPanel, BorderLayout.NORTH);
    }

    /*--------------------resetBorder--------------------*//*--------------------Method--------------------*/
    EmptyBorder emptyBorder = new EmptyBorder(0, 0, 0, 0);
    TitledBorder tagBorder = new TitledBorder(
            new EmptyBorder(0, 0, 0, 0), "▄▄▄▄▄▄",
            TitledBorder.CENTER, TitledBorder.BOTTOM,
            new Font("Arial", Font.ITALIC, 10), Color.magenta);

    private void resetBorder(AddWinTopButton tag, AddWinTopButton b1, AddWinTopButton b2, AddWinTopButton b3) {
        tag.setBorder(tagBorder);
        b1.setBorder(emptyBorder);
        b2.setBorder(emptyBorder);
        b3.setBorder(emptyBorder);
    }

    public static void main(String[] args) {
        new AddItemWin(null);
    }
}


/*--------------------TextAreaPanel--------------------*//*--------------------JPanelClass--------------------*/
class TextAreaPanel extends JPanel {
    JTextArea hintText;
    JTextArea addItemArea;

    public TextAreaPanel() {
        this.setLayout(new BorderLayout());
        {
            hintText = new JTextArea();
            hintText.setBorder(new EtchedBorder());
            hintText.setBackground(new Color(244, 255, 235));
            hintText.setPreferredSize(new Dimension(210, 0));
            hintText.setEditable(false);
            this.add(hintText, BorderLayout.WEST);
        }
        {
            JScrollPane textAreaScrollPane = new JScrollPane();
            {
                addItemArea = new JTextArea(10, 30);
                addItemArea.setLineWrap(true);
                addItemArea.setBackground(new Color(206, 255, 255));
                String[] items = addItemArea.getText().split("(\\s*[,，]{1,}\\s*){1,}|\\s{1,}");
                for (String item : items) {
                    System.out.println(item);
                }

                textAreaScrollPane.setViewportView(addItemArea);
            }
            this.add(textAreaScrollPane, BorderLayout.CENTER);
        }
    }
}

/*--------------------AddWinTopButton--------------------*//*--------------------JButtonClass--------------------*/
class AddWinTopButton extends JToggleButton {
    public AddWinTopButton(String text) {
        super(text);
//        this.setOpaque(false);
        this.setBackground(new Color(206, 255, 255));
        this.setFont(new Font("等线", Font.BOLD, 16));
//            this.setForeground(Color.magenta);
        this.setPreferredSize(new Dimension(60, 30));
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        /*this.setBorder(
                new CompoundBorder(
                        //外边框
                        new TitledBorder(
                                new EmptyBorder(0,0,0,0),
                                "▄▄▄▄▄▄",
                                TitledBorder.CENTER,
                                TitledBorder.BOTTOM,
                                new Font("Arial", Font.ITALIC, 10),
                                Color.magenta
                        ),
                        //内边框
                        null
                )
        );*/
    }
}
