package swingUI;

import dao.ClassDao;
import dao.CourseDao;
import dao.GradeDao;
import dao.StudentDao;
import model.Course;
import model.Grade;
import model.SClass;
import model.Student;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    TextAreaPanel courseArea;
    TextAreaPanel classArea;
    TextAreaPanel studentArea;
    AddWinTagButton addCourseButton;
    AddWinTagButton addClassButton;
    AddWinTagButton addStudentButton;
    int selectedTag = 0;

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
        courseArea = new TextAreaPanel();
        classArea = new TextAreaPanel();
        studentArea = new TextAreaPanel();
        courseArea.hintText.setText("请在右侧输入需要添加的课程信息\n\n" +
                                    "添加格式：\n    九位课程ID + 课程名称\n\n以半角或圆角逗号隔开\n课程名不超过20位\n" +
                                    "如：\nD20192001高等数学，\nD20192002Java，\nD20192003C++, \nD20192004C#"
        );
        classArea.hintText.setText("请在右侧输入需要添加的班级信息\n\n" +
                                   "添加格式：\n    九位班级ID + 班级名称\n\n以半角或圆角逗号隔开\n课程名不超过20位\n" +
                                   "如：\nC20192001虚拟现实191班，\nC20192002虚拟现实192班，\nC20192003虚拟现实193班, \nC20192004虚拟现实194班"
        );
        studentArea.hintText.setText("请在右侧输入需要添加的学生信息\n\n" +
                                     "添加格式：\n    九位学生ID + 姓名\n\n以半角或圆角逗号隔开\n学生姓名不超过10位\n" +
                                     "如：\nS20192001刘一，\nS20192002陈二，\nS20192003张三, \nS20192004李四");

        cardPanel.add(courseArea, "Course");
        cardPanel.add(classArea, "Class");
        cardPanel.add(studentArea, "Student");
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
            addCourseButton = new AddWinTagButton("课程");
            addClassButton = new AddWinTagButton("班级");
            addStudentButton = new AddWinTagButton("学生");

            //--------------------TagButtonListener----------------------------------------Listener--------------------
            {
                addCourseButton.addActionListener(e -> {
                    if (addCourseButton.isSelected()) {
                        selectedTag = 0;
                        resetBorder(addCourseButton, addClassButton, addStudentButton);
                        centerCard.show(cardPanel, "Course");
                    }
                });
                addClassButton.addActionListener(e -> {
                    if (addClassButton.isSelected()) {
                        selectedTag = 1;
                        resetBorder(addClassButton, addCourseButton, addStudentButton);
                        centerCard.show(cardPanel, "Class");
                    }
                });
                addStudentButton.addActionListener(e -> {
                    if (addStudentButton.isSelected()) {
                        selectedTag = 2;
                        resetBorder(addStudentButton, addCourseButton, addClassButton);
                        centerCard.show(cardPanel, "Student");
                    }
                });
            }
            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(addCourseButton);
            buttonGroup.add(addClassButton);
            buttonGroup.add(addStudentButton);
            leftTopJP.add(addCourseButton);
            leftTopJP.add(addClassButton);
            leftTopJP.add(addStudentButton);
            resetBorder(addCourseButton, addClassButton, addStudentButton);
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
                    courseArea.addItemArea.setText("");
                    classArea.addItemArea.setText("");
                    studentArea.addItemArea.setText("");
                });
                addItemButton.addActionListener(e -> {
                    //--------------------SQL Here--------------------
                    Switch:
                    switch (selectedTag) {
                        case 0:
                            //Course
                        {
                            String[] courses = courseArea.splitArea();
                            for (String course : courses) {
                                if (!course.matches("D\\d{8}[\\w[-+#*][\\u4E00-\\u9FA5]]{1,20}")) {
                                    JOptionPane.showMessageDialog(AddItemWin.this, "存在格式错误：\n" + course, "",
                                                                  JOptionPane.ERROR_MESSAGE
                                    );
                                    break Switch;
                                }
                            }
                            ArrayList<Course> courseList = new ArrayList<>();
                            for (String course : courses) {
                                courseList.add(new Course(course.substring(0, 9), course.substring(9)));
                            }
                            ArrayList<Integer> existCourse = CourseDao.containCourses(courseList);
                            if (existCourse.size() > 0) {
                                StringBuffer sb = new StringBuffer();
                                for (Integer integer : existCourse) {
                                    sb.append(courses[integer]);
                                }
                                JOptionPane.showMessageDialog(AddItemWin.this,
                                                              "以下课程已存在：\n" + sb.toString().substring(0, 9), "",
                                                              JOptionPane.WARNING_MESSAGE
                                );
                                break Switch;
                            }
                            CourseDao.addCourse(courseList);
                            JOptionPane.showMessageDialog(AddItemWin.this, "添加成功！");
                            break;
                        }
                        case 1:
                            //Class
                        {
                            String[] classes = classArea.splitArea();
                            for (String sclass : classes) {
                                if (!sclass.matches("C\\d{8}[\\w[-+#*][\\u4E00-\\u9FA5]]{1,20}")) {
                                    JOptionPane.showMessageDialog(AddItemWin.this, "存在格式错误：\n" + sclass, "",
                                                                  JOptionPane.ERROR_MESSAGE
                                    );
                                    break Switch;
                                }
                            }
                            ArrayList<SClass> classList = new ArrayList<>();
                            for (String sclass : classes) {
                                classList.add(new SClass(sclass.substring(0, 9), sclass.substring(9)));
                            }
                            ArrayList<Integer> existClass = ClassDao.containClass(classList);
                            if (existClass.size() > 0) {
                                StringBuffer sb = new StringBuffer();
                                for (Integer integer : existClass) {
                                    sb.append(classes[integer]);
                                }
                                JOptionPane.showMessageDialog(AddItemWin.this,
                                                              "以下班级已存在：\n" + sb.toString().substring(0, 9), "",
                                                              JOptionPane.WARNING_MESSAGE
                                );
                                break Switch;
                            }
                            ClassDao.addClass(classList);
                            JOptionPane.showMessageDialog(AddItemWin.this, "添加成功！");
                            break;
                        }
                        case 2:
                            //Student
                        {
                            String[] students = studentArea.splitArea();
                            for (String student : students) {
                                if (!student.matches("S\\d{8}[\\w[\\u4E00-\\u9FA5]]{1,10}")) {
                                    JOptionPane.showMessageDialog(AddItemWin.this, "存在格式错误：\n" + student, "",
                                                                  JOptionPane.ERROR_MESSAGE
                                    );
                                    break Switch;
                                }
                            }
                            ArrayList<Student> studentList = new ArrayList<>();
                            for (String student : students) {
                                studentList.add(new Student(student.substring(0, 9), student.substring(9)));
                            }
                            ArrayList<Integer> existStudent = StudentDao.containStudents(studentList);
                            if (existStudent.size() > 0) {
                                StringBuffer sb = new StringBuffer();
                                for (Integer integer : existStudent) {
                                    sb.append(students[integer]);
                                }
                                JOptionPane.showMessageDialog(AddItemWin.this,
                                                              "以下学生已存在：\n" + sb.toString().substring(0, 9), "",
                                                              JOptionPane.WARNING_MESSAGE
                                );
                                break Switch;
                            }
                            StudentDao.addStudents(studentList);
                            JOptionPane.showMessageDialog(AddItemWin.this, "添加成功！");
                            break;
                        }
                        default:
                            throw new RuntimeException("非法Tag下标");
                    }
                });
            }
            rightTopJP.add(clearAreaButton);
            rightTopJP.add(addItemButton);
        }
        topPanel.add(rightTopJP, BorderLayout.EAST);

        this.add(topPanel, BorderLayout.NORTH);
    }

    //--------------------resetBorder----------------------------------------Method--------------------
    EmptyBorder emptyBorder = new EmptyBorder(0, 0, 0, 0);
    TitledBorder tagBorder = new TitledBorder(
            new EmptyBorder(0, 0, 0, 0), "▄▄▄▄▄▄",
            TitledBorder.CENTER, TitledBorder.BOTTOM,
            new Font("Arial", Font.ITALIC, 10), Color.magenta
    );

    private void resetBorder(AddWinTagButton tag, AddWinTagButton b1, AddWinTagButton b2) {
        tag.setBorder(tagBorder);
        b1.setBorder(emptyBorder);
        b2.setBorder(emptyBorder);
    }

    public static void main(String[] args) {
        new AddItemWin(null);
    }
}


//--------------------TextAreaPanel----------------------------------------JPanelClass--------------------
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

                textAreaScrollPane.setViewportView(addItemArea);
            }
            this.add(textAreaScrollPane, BorderLayout.CENTER);
        }
    }

    public String[] splitArea() {
        String[] items = addItemArea.getText().trim().split("([\\s\\n]*[,，]{1,}[\\s\\n]*){1,}|[\\s\\n]{1,}");
        System.out.print("[");
        for (String item : items) {
            System.out.println(item);
        }
        System.out.print("]");
        return items;
    }
}

//--------------------AddWinTagButton----------------------------------------JButtonClass--------------------
class AddWinTagButton extends JToggleButton {
    public AddWinTagButton(String text) {
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
