package swingUI;

import dao.CourseDao;
import dao.GradeDao;
import model.Course;
import model.Grade;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Delta
 * Created in 2021-07-11 01:37
 */
public class AddElectiveWin extends JDialog {
    public AddElectiveWin(JFrame parent) {
        super(parent, "添加选课");
        initialize();
        this.setVisible(true);
    }

    JComboBox<Course> courseJComboBox;
    TopButton addButton = new TopButton("添加");
    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setIconImage(new ImageIcon(getClass().getResource("/icon/01.jpg")).getImage());
        this.setSize(210, 75);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(255, 179, 254));

        //----------courseJComboBox
        {
            courseJComboBox = new JComboBox<>();
            ArrayList<Course> courseList = CourseDao.getCourseList();
            {
                ArrayList<Grade> studentGrades = GradeDao.getStudentGrade(MainWin.currentUser.getIdNumber());
                for (Grade studentGrade : studentGrades) {
                    courseList.removeIf(course -> course.getCourseName().equals(CourseDao.getCourseName(studentGrade.getCourseID())));
                }
            }
            if (courseList.size()>0) {
                for (Course course : courseList) {
                    courseJComboBox.addItem(course);
                }
            } else {
                courseJComboBox.addItem(new Course("", "NULL"));
                addButton.setEnabled(false);
            }
            jPanel.add(courseJComboBox);
        }

        //----------addButton
        {
//            addButton = new TopButton("添加");
            addButton.addActionListener(e->{
                String addCourse = ((Course)courseJComboBox.getSelectedItem()).getCourseID();
                GradeDao.addElective(MainWin.currentUser.getIdNumber(), addCourse);
                JOptionPane.showMessageDialog(this, "添加成功!");
                this.dispose();
            });
            jPanel.add(addButton);
        }

        this.add(jPanel, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        new AddElectiveWin(null);
    }
}
