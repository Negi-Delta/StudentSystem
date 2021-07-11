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
public class AddEletiveWin extends JDialog {
    public AddEletiveWin(JFrame parent) {
        super(parent, "添加选课");
        initialize();
        this.setVisible(true);
    }

    JComboBox<Course> courseJComboBox;
    TopButton addButton = new TopButton("添加");
    private void initialize() {
        this.setLayout(new FlowLayout());
        this.setBackground(new Color(245, 236, 255));
        this.setIconImage(new ImageIcon(getClass().getResource("/icon/01.jpg")).getImage());
        this.setSize(210, 75);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);


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
                courseJComboBox.addItem(null);
                addButton.setEnabled(false);
            }
            this.add(courseJComboBox);
        }

        //----------addButton
        {
//            addButton = new TopButton("添加");
            addButton.addActionListener(e->{
                String addCourse = ((Course)courseJComboBox.getSelectedItem()).getCourseID();
                System.out.println("["+MainWin.currentUser.getIdNumber()+","+addCourse+"]");
                GradeDao.addGrade(MainWin.currentUser.getIdNumber(), addCourse);
            });
            this.add(addButton);
        }
    }

    public static void main(String[] args) {
        new AddEletiveWin(null);
    }
}
