package swingUI;

import dao.ClassDao;
import dao.CourseDao;
import dao.GradeDao;
import dao.StudentDao;
import model.Course;
import model.SClass;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Delta
 * Created in 2021-07-11 21:03
 */
public class EditWin extends JDialog {
    public EditWin(JFrame parent, HashSet<CenterNodeInfo> NodesID) {
        super(parent);
        initialize(NodesID);
        this.setVisible(true);
    }

    private void initialize(HashSet<CenterNodeInfo> NodesID) {
        int size = NodesID.size();
        this.setLayout(new BorderLayout());
        this.setIconImage(new ImageIcon(getClass().getResource("/icon/01.jpg")).getImage());
        this.setSize(400, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.setBackground(new Color(236, 255, 255));

        //--------------------CheckBox--------------------
        DelCheckBox editName = new DelCheckBox("姓名");
        DelCheckBox editClass = new DelCheckBox("分班");
        DelCheckBox editElective = new DelCheckBox("选课");
        DelCheckBox editGrade = new DelCheckBox("成绩");

        JTextField nameTextField = new JTextField(15);
        JComboBox<SClass> classComboBox = new JComboBox<>();
        JComboBox<Course> electiveComboBox = new JComboBox<>();
        JTextField gradeTextField = new JTextField(5);
        {
            if (size > 1) {
                editName.setSelected(false);
                editName.setEnabled(false);
                editElective.setSelected(false);
                editElective.setEnabled(false);
            }
            nameTextField.setEnabled(false);
            classComboBox.setEnabled(false);
            electiveComboBox.setEnabled(false);
            gradeTextField.setEnabled(false);
        }
        //--------------------ComboBox--------------------
        {
            ArrayList<SClass> classList = ClassDao.getClassList();
            if (classList.size() > 0) {
                for (SClass sClass : classList) {
                    classComboBox.addItem(sClass);
                }
            } else {
                classComboBox.addItem(new SClass(null, "NULL"));
                editClass.setEnabled(false);
            }
        }
        {
            ArrayList<Course> courseList = CourseDao.getCourseList();
            if (courseList.size() > 0) {
                for (Course course : courseList) {
                    electiveComboBox.addItem(course);
                }
            } else {
                electiveComboBox.addItem(new Course(null, "NULL"));
                editElective.setEnabled(false);
            }
        }

        //--------------------BoxActionListener
        {
            editName.addActionListener(e -> {
                if (editName.isSelected()) {
                    nameTextField.setEnabled(true);
                } else {
                    nameTextField.setEnabled(false);
                }
            });
            editClass.addActionListener(e -> {
                if (editClass.isSelected()) {
                    classComboBox.setEnabled(true);
                } else {
                    classComboBox.setEnabled(false);
                }
            });
            editElective.addActionListener(e -> {
                if (editElective.isSelected()) {
                    electiveComboBox.setEnabled(true);
                    gradeTextField.setEnabled(true);
                    editGrade.setSelected(true);
                    editGrade.setEnabled(false);
                } else {
                    electiveComboBox.setEnabled(false);
                    editGrade.setEnabled(true);
                }
            });
            editGrade.addActionListener(e -> {
                if (editGrade.isSelected()) {
                    gradeTextField.setEnabled(true);
                } else {
                    gradeTextField.setEnabled(false);
                }
            });
        }

        transparentPanel nameArea = new transparentPanel();
        transparentPanel classArea = new transparentPanel();
        transparentPanel electiveArea = new transparentPanel();
        transparentPanel gradeArea = new transparentPanel();
        nameArea.add(editName);
        nameArea.add(nameTextField);
        classArea.add(editClass);
        classArea.add(classComboBox);
        electiveArea.add(editElective);
        electiveArea.add(electiveComboBox);
        gradeArea.add(editGrade);
        gradeArea.add(gradeTextField);

        transparentPanel editArea = new transparentPanel(new GridLayout(0, 1));
        editArea.add(nameArea);
        editArea.add(classArea);
        editArea.add(electiveArea);
        editArea.add(gradeArea);
        editArea.setBorder(new TitledBorder("编辑学生信息"));

        jPanel.add(editArea, BorderLayout.CENTER);
        //--------------------EditButton--------------------
        JButton editButton = new JButton("提交");
        {
            editButton.setBackground(new Color(171, 255, 255));
            editButton.setFont(new Font("等线", Font.BOLD, 20));
            editButton.setForeground(Color.black);
            editButton.addActionListener(e -> {
                if (size<1) return;
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "确认提交?", "",
                                                                            JOptionPane.YES_NO_OPTION
                )) {
                    for (CenterNodeInfo id : NodesID) {
                        if (editName.isSelected())
                            if (nameTextField.getText().matches("[\\w[\\u4E00-\\u9FA5]]{1,10}")) {
                                StudentDao.updateName(id.idNumber, editName.getText());
                            } else {
                                JOptionPane.showMessageDialog(this,
                                                              "存在格式错误\n可能的错误：姓名长度应在两位至十位", "ERROR",
                                                              JOptionPane.ERROR_MESSAGE
                                );
                                return;
                            }
                        if (editClass.isSelected()) {
                            StudentDao.updateClass(id.idNumber,
                                                   ((SClass) classComboBox.getSelectedItem()).getClassID()
                            );
                        }
                        if (editElective.isSelected()) {
                            if (Integer.parseInt(gradeTextField.getText()) < 1000) {
                                GradeDao.updateGrade(id.idNumber,
                                                     ((Course) electiveComboBox.getSelectedItem()).getCourseID(),
                                                     gradeTextField.getText()
                                );
                            } else {
                                JOptionPane.showMessageDialog(this,
                                                              "存在格式错误\n可能的错误：成绩值应为小于1000的整数", "ERROR",
                                                              JOptionPane.ERROR_MESSAGE
                                );
                            }
                        } else {
                            if (editGrade.isSelected()) {
                                if (gradeTextField.getText().matches("\\d{1,3}")) {
                                    GradeDao.updateGrade(id.idNumber,
                                                         id.courseID,
                                                         gradeTextField.getText()
                                    );
                                } else {
                                    JOptionPane.showMessageDialog(this,
                                                                  "存在格式错误\n可能的错误：成绩值应为小于1000的整数", "ERROR",
                                                                  JOptionPane.ERROR_MESSAGE
                                    );
                                    return;
                                }
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(this, "提交成功!");
                    this.dispose();
                }
            });
        }
        transparentPanel jPanel1 = new transparentPanel();
        jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel1.add(editButton);
        jPanel.add(jPanel1, BorderLayout.SOUTH);

        this.add(jPanel);
    }

    public static void main(String[] args) {
        new EditWin(null, null);
    }
}

class transparentPanel extends JPanel {
    public transparentPanel() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setOpaque(false);
    }

    public transparentPanel(LayoutManager layoutManager) {
        super(layoutManager);
        this.setOpaque(false);
    }
}