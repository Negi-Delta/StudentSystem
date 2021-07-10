package swingUI;

import dao.ClassDao;
import dao.CourseDao;
import dao.GradeDao;
import dao.StudentDao;
import model.Grade;
import model.Student;
import model.Teacher;
import model.Users;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * @author Delta
 * Created in 2021-07-04 18:14
 */
/*------------------------------------MainWindows------------------------------------*/
public class MainWin extends JFrame {
    public static Users currentUser;

    public MainWin(LoginWin parentLoginFrame) {
        super("Student Performance Management System");//学生成绩管理系统
        initialize(parentLoginFrame);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1585, 880);
        this.setMinimumSize(new Dimension(1585, 880));
        this.setMaximumSize(new Dimension(1920, 1080));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    JPanel mainjp;
    TopPanel topPanel;
    LeftPanel leftPanel;
    CenterPanel centerPanel;

    private void initialize(LoginWin parentLoginFrame) {
        this.setIconImage(new ImageIcon(getClass().getResource("/icon/01.jpg")).getImage());

        mainjp = new JPanel();
        mainjp.setLayout(new BorderLayout());

        //--------------------TopPanel--------------------//
        topPanel = new TopPanel();
        {
            /*
                int mouseXInMainWin = MouseInfo.getPointerInfo().getLocation().x - MainWin.this.getBounds().x;
                int mouseYInMainWin = MouseInfo.getPointerInfo().getLocation().y - MainWin.this.getBounds().y;
            */
            topPanel.rightTopJP.setBounds(this.getBounds().width - 37, 0, 180, 37);
            /*--------------------Timer----------------------------------------Timer--------------------*/
            JButton stopShow = new JButton();
            Timer timerShow = new Timer(15, e -> {
                topPanel.rightTopJP.setBounds(Math.max(topPanel.rightTopJP.getBounds().x - 5, MainWin.this.getBounds().width - 37 - 160), 0, 180, 37);
                if (topPanel.rightTopJP.getBounds().x < MainWin.this.getBounds().width - 37 - 110)
                    topPanel.arrowLabel.setText("►");
                topPanel.rightTopJP.repaint();
                if (topPanel.rightTopJP.getBounds().x == MainWin.this.getBounds().width - 37 - 160) stopShow.doClick();
                System.out.println("in");
            });
            stopShow.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timerShow.stop();
                }
            });
            JButton stopHide = new JButton();
            Timer timerHide = new Timer(15, e -> {
                topPanel.rightTopJP.setBounds(Math.min(topPanel.rightTopJP.getBounds().x + 1, MainWin.this.getBounds().width - 37), 0, 180, 37);
                if (topPanel.rightTopJP.getBounds().x > MainWin.this.getBounds().width - 37 - 70)
                    topPanel.arrowLabel.setText("◄");
                topPanel.rightTopJP.repaint();
                if (topPanel.rightTopJP.getBounds().x == MainWin.this.getBounds().width - 37) stopHide.doClick();
                System.out.println("out");
            });
            stopHide.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timerHide.stop();
                }
            });
            /*--------------------MouseListener----------------------------------------MouseListener--------------------*/
            topPanel.addMouseListener(new MouseListener() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    timerHide.stop();
                    timerShow.start();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    int mouseXInMainWin = MouseInfo.getPointerInfo().getLocation().x - MainWin.this.getBounds().x;
                    int mouseYInMainWin = MouseInfo.getPointerInfo().getLocation().y - MainWin.this.getBounds().y;
                    if (mouseXInMainWin > MainWin.this.getBounds().width - 10 || mouseXInMainWin < 10 || mouseYInMainWin < 35 || mouseYInMainWin > 62) {
                        timerShow.stop();
                        timerHide.start();
                        System.out.println(mouseXInMainWin);
                        System.out.println(mouseYInMainWin);
                    }
                }

                public void mouseClicked(MouseEvent e) {
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseReleased(MouseEvent e) {
                }
            });

            topPanel.logoutButton.addActionListener(e -> {
                this.dispose();
                parentLoginFrame.setVisible(true);
            });

            topPanel.addButton.addActionListener(e -> {
                new AddItemWin(this);
            });
        }
        mainjp.add(topPanel, BorderLayout.NORTH);


        //--------------------LeftPanel--------------------//
        leftPanel = new LeftPanel();
        mainjp.add(leftPanel, BorderLayout.WEST);

        //--------------------CenterPanel--------------------//
        centerPanel = new CenterPanel();
        mainjp.add(centerPanel, BorderLayout.CENTER);

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                topPanel.rightTopJP.setBounds(MainWin.this.getBounds().width - 37, 0, 180, 37);
                topPanel.repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        if (MainWin.currentUser instanceof Student){
            studentMod();
        } else {
            teacherMod();
        }
        this.add(mainjp);
    }

    private void studentMod(){
        topPanel.leftTopJP.setVisible(false);
        leftPanel.setVisible(false);
    }

    private void teacherMod(){
        topPanel.leftTopJP.setVisible(true);
        leftPanel.setVisible(true);
    }
}

/*----------中央栏--------------------中央栏--------------------中央栏--------------------中央栏----------*/
class CenterPanel extends JPanel {
    JScrollPane centerJSP;
    JPanel centerBox = new JPanel();
    ArrayList<CenterNode> gradeList;

    public CenterPanel() {
        this.setLayout(new BorderLayout());
        centerJSP = new JScrollPane();
//        centerJSP.setPreferredSize(new Dimension(1340, 800));
        centerJSP.setBorder(new EtchedBorder());
//        centerBox.setOpaque(false);
//        centerBox.setBorder(new LineBorder(Color.red));
        gradeList = new ArrayList<>();
        if (MainWin.currentUser instanceof Student) {
            //学生登录
            Student student = (Student) MainWin.currentUser;
            ArrayList<Grade> studentGrades = GradeDao.getStudentGrade(student.getIdNumber());
            if( studentGrades.size()>0 ){
                //学生有选课信息
                for (Grade grade : studentGrades) {
                    gradeList.add(new CenterNode(
                            ClassDao.getClassName(student.getClassID()), student.getIdNumber(), student.getName(),
                            CourseDao.getCourseName(grade.getCourseID()), grade.getGrade()
                    ));
                }
            } else {
                //学生无选课信息
                gradeList.add(new CenterNode(
                        ClassDao.getClassName(student.getClassID()), student.getIdNumber(), student.getName(),
                        null, null
                ));
            }
        } else {
            //教师登录
            ArrayList<Student> students = StudentDao.getStudentList();
            for (Student student : students) {
                ArrayList<Grade> studentGrades = GradeDao.getStudentGrade(student.getIdNumber());
                if( studentGrades.size()>0 ){
                    //学生有选课信息
                    for (Grade grade : studentGrades) {
                        gradeList.add(new CenterNode(
                                ClassDao.getClassName(student.getClassID()), student.getIdNumber(), student.getName(),
                                CourseDao.getCourseName(grade.getCourseID()), grade.getGrade()
                        ));
                    }
                } else {
                    //学生无选课信息
                    gradeList.add(new CenterNode(
                            ClassDao.getClassName(student.getClassID()), student.getIdNumber(), student.getName(),
                            null, null
                    ));
                }
            }
        }
        setCenterBox("id");
        this.add(centerJSP);
    }

    public void setCenterBox(String sortStandard) {
        centerBox.setLayout(new GridLayout(Math.max(gradeList.size(), 20), 1));

        //排序
        Collections.sort(gradeList);
        //添加数据组件
        Iterator<CenterNode> listIterator = gradeList.iterator();
        while (listIterator.hasNext()) {
            centerBox.add(listIterator.next());
        }

        centerJSP.setViewportView(centerBox);
    }
}

/*--------------------CenterNode--------------------*/
class CenterNode extends JPanel implements Comparable {
    String sClass;
    String idNumber;
    String name;
    String course;
    String grade;

    public CenterNode(String sClass, String idNumber, String name, String course, String grade) {
        this.sClass = sClass;
        this.idNumber = idNumber;
        this.name = name;
        this.course = course;
        this.grade = grade;
        initialize();
    }

    JCheckBox nodeCheckBox;
    NodeLabel sClassJL;
    NodeLabel idNumberJL;
    NodeLabel nameJL;
    NodeLabel courseJL;
    NodeLabel gradeJL;
    NodeButton editButton;
    NodeButton deleteButton;

    private void initialize() {
        this.setLayout(new BorderLayout(0, 0));
        this.setPreferredSize(new Dimension(1300, 40));
        nodeCheckBox = new JCheckBox();
        nodeCheckBox.setBackground(new Color(236, 255, 255));
        this.add(nodeCheckBox, BorderLayout.WEST);
        {
            /*--------------------nodeJLabelsJP--------------------*/
            JPanel nodeJLabelsJP = new JPanel();
            nodeJLabelsJP.setLayout(new FlowLayout(FlowLayout.CENTER, 0, -1));
            nodeJLabelsJP.setBackground(new Color(236, 255, 255));
            sClassJL = new NodeLabel(this.sClass, 200);
            idNumberJL = new NodeLabel(this.idNumber, 150);
            nameJL = new NodeLabel(this.name, 200);
            courseJL = new NodeLabel(this.course, 450);
            gradeJL = new NodeLabel(this.grade, 100);
            nodeJLabelsJP.add(sClassJL);
            nodeJLabelsJP.add(idNumberJL);
            nodeJLabelsJP.add(nameJL);
            nodeJLabelsJP.add(courseJL);
            nodeJLabelsJP.add(gradeJL);
            this.add(nodeJLabelsJP, BorderLayout.CENTER);
        }
        if (MainWin.currentUser instanceof Teacher) {
            {
                /*--------------------nodeButtonsJP--------------------*/
                JPanel nodeButtonsJP = new JPanel();
                nodeButtonsJP.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
                nodeButtonsJP.setBackground(new Color(236, 255, 255));
                editButton = new NodeButton("编辑", 90);
                deleteButton = new NodeButton("删除", 90);
                nodeButtonsJP.add(editButton);
                nodeButtonsJP.add(deleteButton);
                this.add(nodeButtonsJP, BorderLayout.EAST);
            }
        }

        this.setBorder(new LineBorder(new Color(0, 150, 150)));
//        this.setBackground(new Color(236, 255, 255));
        this.setBackground(Color.pink);
    }

    /*--------------------NodeLabel--------------------*/
    class NodeLabel extends JLabel {
        public NodeLabel(String text, int width) {
            super(text);
            setFont(new Font("等线", Font.PLAIN, 25));
            setPreferredSize(new Dimension(width, 40));
            setBorder(new LineBorder(new Color(0, 150, 150)));
        }
    }

    /*--------------------NodeButton--------------------*/
    class NodeButton extends JButton {
        public NodeButton(String text, int width) {
            super(text);
            setFont(new Font("等线", Font.PLAIN, 20));
            setPreferredSize(new Dimension(width, 30));
            setBackground(new Color(236, 255, 255));
            setBorder(new EtchedBorder());
        }
    }

    @Override
    public String toString() {
        return "CenterNode{" +
                "sClass='" + sClass + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", grade=" + grade +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof CenterNode) {
            CenterNode student = (CenterNode) o;
            //默认按照id从小到大
            return this.idNumber.compareTo(student.idNumber);
        }
        throw new RuntimeException("CenterNodeTypeMatchException");
    }
}


/*----------左侧烂--------------------左侧烂--------------------左侧烂--------------------左侧烂----------*/
class LeftPanel extends JPanel {
    JTabbedPane filter;
    JComboBox classJComboBox;
    JComboBox courseJComboBox;
    JTabbedPane sort;
    JRadioButton idUp;
    JRadioButton idDown;
    ButtonGroup idButtonGroup;
    JRadioButton gradeUp;
    JRadioButton gradeDown;
    ButtonGroup gradeButtonGroup;
    JButton refresh;

    public LeftPanel() {
        this.setBackground(new Color(244, 255, 235));
        this.setPreferredSize(new Dimension(230, 2));
        this.setLayout(new FlowLayout());

        /*--------------------filter--------------------*/
        filter = new JTabbedPane();//筛选
        {
            //----------classJComboBox
            classJComboBox = new JComboBox<>();
            classJComboBox.addItem("NULL");
            classJComboBox.addItem("ClassA");
            classJComboBox.addItem("ClassB");
            classJComboBox.addItem("ClassC");
            filter.addTab("班级", classJComboBox);
        }
        {
            //----------courseJComboBox
            courseJComboBox = new JComboBox<>();
            courseJComboBox.addItem("NULL");
            courseJComboBox.addItem("CourseA");
            courseJComboBox.addItem("CourseB");
            courseJComboBox.addItem("CourseC");
            filter.addTab("课程", courseJComboBox);
        }
        filter.setPreferredSize(new Dimension(200, 80));
        filter.setBorder(new TitledBorder("筛选"));
        this.add(filter);

        /*--------------------sort--------------------*/
        sort = new JTabbedPane();//排序
        {
            //----------|--idJRadioButtonDown
            idUp = new JRadioButton("按Id升序排列");
            idDown = new JRadioButton("按Id降序排列");
            //----------|--idButtonGroup
            idButtonGroup = new ButtonGroup();
            idButtonGroup.add(idUp);
            idButtonGroup.add(idDown);
            JPanel idButtonPanel = new JPanel();
            idButtonPanel.add(idUp);
            idButtonPanel.add(idDown);
            sort.addTab("ID", idButtonPanel);
        }
        {
            //----------|--CourseJRadioButtonDown
            gradeUp = new JRadioButton("按成绩升序排列");
            gradeDown = new JRadioButton("按成绩降序排列");
            //----------|--CourseButtonGroup
            gradeButtonGroup = new ButtonGroup();
            gradeButtonGroup.add(gradeUp);
            gradeButtonGroup.add(gradeDown);
            JPanel gradeButtonPanel = new JPanel();
            gradeButtonPanel.add(gradeUp);
            gradeButtonPanel.add(gradeDown);
            sort.addTab("Grade", gradeButtonPanel);
        }
        sort.setPreferredSize(new Dimension(200, 120));
        sort.setBorder(new TitledBorder("排序"));
        this.add(sort);

        /*--------------------fresh--------------------*/
        refresh = new JButton("刷新");//刷新
        refresh.addActionListener(e -> {
            this.repaint();
            System.out.println("repaint");
        });
        this.add(refresh);
    }
}

/*----------顶部栏--------------------顶部栏--------------------顶部栏--------------------顶部栏----------*/
class TopPanel extends JPanel {
    TopButton addButton;
    TopButton delButton;
    TopButton aboutButton;
    TopButton logoutButton;
    JPanel leftTopJP;
    JPanel rightTopJP;
    JLabel arrowLabel;

    public TopPanel() {
        this.setBackground(new Color(245, 236, 255));
//        this.setLayout(new GridLayout(1, 2));
//        this.setLayout(new BorderLayout());
        this.setLayout(null);
        this.setPreferredSize(new Dimension(1570, 37));

        /*--------------------leftTopJP--------------------*/
        leftTopJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftTopJP.setBounds(0, 0, 500, 37);
        {
            leftTopJP.setOpaque(false);
            addButton = new TopButton("添加");
            delButton = new TopButton("删除选中");
            delButton.addActionListener(e -> {

            });
            leftTopJP.add(addButton);
            leftTopJP.add(delButton);
        }
        this.add(leftTopJP);

        /*--------------------rightTopJP--------------------*/
        rightTopJP = new JPanel();
//        rightTopJP.setBounds(1550, 0, 180, 37);
        rightTopJP.setBorder(new LineBorder(new Color(255, 179, 254)));
        {
            //1550,37
//            rightTopJP.setOpaque(false);箭头
            arrowLabel = new JLabel("◄");
            arrowLabel.setForeground(new Color(179, 89, 255));
            aboutButton = new TopButton("关于");
            logoutButton = new TopButton("退出登录");
            logoutButton.setBorder(new LineBorder(new Color(255, 77, 77)));
            rightTopJP.add(arrowLabel);
            rightTopJP.add(aboutButton);
            rightTopJP.add(logoutButton);
        }
        this.add(rightTopJP, BorderLayout.EAST);
    }

    class TopButton extends JButton {
        public TopButton(String text) {
            super(text);
            this.setBackground(new Color(206, 255, 255));
            this.setPreferredSize(new Dimension(73, 26));
            this.setBorder(new EtchedBorder());
        }
    }
}
