package swingUI;

import com.sun.xml.internal.ws.client.sei.SEIStub;
import dao.StudentDao;
import dao.TeacherDao;
import model.Student;
import model.Teacher;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

/**
 * @author Delta
 * Created in 2021-07-04 19:41
 */
public class LoginWin extends JFrame {
    public LoginWin() {
        super("欢迎");
        this.setLayout(null);
        this.setIconImage(new ImageIcon(getClass().getResource("/icon/01.jpg")).getImage());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //--------------------------登录注册组件---------------------------------------//
        LoginPanel loginPanel = new LoginPanel();
        //loginPanel.setBackground(new Color(187, 255, 232));
        LoginPanel registerPanel = new LoginPanel();
        //registerPanel.setBackground(new Color(255, 187, 232));
        loginPanel.mainButton.setText("登录");
        loginPanel.minorButton.setText("转到注册");
        registerPanel.mainButton.setText("注册");
        registerPanel.minorButton.setText("转到登录");
        registerPanel.welcome.setText("注 册");
        loginPanel.setOpaque(false);
        registerPanel.setOpaque(false);

        //--------------------------登录注册组件容器---------------------------------------//
        JPanel MainJP = new JPanel();
        MainJP.setLayout(new GridLayout(1, 2));
        //        MainJP.setBackground(Color.pink);
        MainJP.add(loginPanel, "Login");
        MainJP.add(registerPanel, "Signin");
        MainJP.setBounds(0, 0, 800, 271);
        MainJP.setBackground(new Color(187, 255, 232));

        //---------------------------------------Timer和监听事件--------------------------//
        {
            //-------------Timer转到注册
            JButton endToSign = new JButton();//关停Timer
            Timer ToSign = new Timer(15, e -> {
            /*
            187->255
            255->187
            232->232
             */
                int r, g;//用于最终赋值
                r = MainJP.getBackground().getRed();
                g = MainJP.getBackground().getGreen();
                int cstep = 2;
                r += cstep;
                g -= cstep;
                try {
                    MainJP.setBackground(new Color(r, g, 232));
                } catch (IllegalArgumentException exception) {
                }
                final int step = 12;
                if ((int) MainJP.getBounds().getX() - step >= -408) {
                    MainJP.setBounds((int) MainJP.getBounds().getX() - step, 0, 800, 271);
                } else {
                    MainJP.setBounds(-408, 0, 800, 271);
                    endToSign.doClick();
                }
            });
            endToSign.addActionListener(e -> {
                ToSign.stop();
                loginPanel.enableButton();
                registerPanel.enableButton();
            });
            //-------------Timer转到登录
            JButton endToLog = new JButton();//关停Timer
            Timer ToLog = new Timer(15, e -> {
                int r, g;//用于最终赋值
                r = MainJP.getBackground().getRed();
                g = MainJP.getBackground().getGreen();
                int cstep = 2;
                r -= cstep;
                g += cstep;
                try {
                    MainJP.setBackground(new Color(r, g, 232));
                } catch (IllegalArgumentException exception) {
                }
                final int step = 12;
                if ((int) MainJP.getBounds().getX() + step <= 0) {
                    MainJP.setBounds((int) MainJP.getBounds().getX() + step, 0, 800, 271);
                } else {
                    MainJP.setBounds(0, 0, 800, 271);
                    endToLog.doClick();
                }
            });
            endToLog.addActionListener(e -> {
                ToLog.stop();
                loginPanel.enableButton();
                registerPanel.enableButton();
            });

            //-------------登录主按钮---登录--->转到主界面
            loginPanel.mainButton.addActionListener(e -> {
                String account = loginPanel.jtAccount.getText();
                if (account.matches("S\\d{8}")) {
                    //匹配学生ID
                    Student loginStudent = StudentDao.getStudent(account);
                    if (loginStudent == null) {
                        //不存在此ID
                        JOptionPane.showMessageDialog(LoginWin.this, "不存在此学生ID，请联系管理员添加", "", JOptionPane.WARNING_MESSAGE);
                    } else if (loginStudent.getPassword() == null) {
                        //存在此ID,但未注册
                        int answer = JOptionPane.showConfirmDialog(LoginWin.this, "该学生尚未注册，是否转到注册界面？", "提示", JOptionPane.YES_NO_OPTION);
                        if (answer == JOptionPane.YES_OPTION) {
                            loginPanel.minorButton.doClick();
                            registerPanel.jtAccount.setText(account);
                        }
                    } else {
                        //存在ID,且已注册
                        String password = loginPanel.jtPassword.getText();
                        if (loginStudent.getPassword().equals(password)) {
                            //密码正确
                            JOptionPane.showMessageDialog(LoginWin.this, "登录成功！", "", JOptionPane.INFORMATION_MESSAGE);
                            LoginWin.this.dispose();
                            MainWin.currentUser = loginStudent;
                            new MainWin(this);
                        } else {
                            //密码错误
                            JOptionPane.showMessageDialog(LoginWin.this, "密码错误", "错误", JOptionPane.ERROR_MESSAGE);
                            loginPanel.jtPassword.setForeground(Color.red);
                            loginPanel.jlPassword.setForeground(Color.red);
                        }
                    }
                } else if (account.matches("T\\d{8}")) {
                    //匹配教师ID
                    Teacher loginteacher = TeacherDao.getTeacher(account);
                    if(loginteacher == null){
                        //不存在此ID
                        JOptionPane.showMessageDialog(LoginWin.this, "不存在此ID", "", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String password = loginPanel.jtPassword.getText();
                        if (loginteacher.getPassword().equals(password)) {
                            //密码正确
                            JOptionPane.showMessageDialog(LoginWin.this, "登录成功！", "", JOptionPane.INFORMATION_MESSAGE);
                            LoginWin.this.dispose();
                            MainWin.currentUser = loginteacher;
                            new MainWin(this);
                        } else {
                            //密码错误
                            JOptionPane.showMessageDialog(LoginWin.this, "密码错误", "错误", JOptionPane.ERROR_MESSAGE);
                            loginPanel.jtPassword.setForeground(Color.red);
                            loginPanel.jlPassword.setForeground(Color.red);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginWin.this, "ID格式有误", "错误", JOptionPane.ERROR_MESSAGE);
                    loginPanel.jtAccount.setForeground(Color.red);
                    loginPanel.jlAccount.setForeground(Color.red);
                }
            });
            //-------------登录次按钮---转到注册
            loginPanel.minorButton.addActionListener(e -> {
                loginPanel.disableButton();
                registerPanel.disableButton();
                ToSign.start();
                loginPanel.clear();
                registerPanel.clear();
            });
            //-------------注册主按钮---注册--->转到登录
            registerPanel.mainButton.addActionListener(e -> {
                String account = registerPanel.jtAccount.getText();
                if (account.matches("S\\d{8}")) {
                    //匹配学生ID
                    Student loginStudent = StudentDao.getStudent(account);
                    if (loginStudent == null) {
                        //不存在此ID
                        JOptionPane.showMessageDialog(LoginWin.this, "不存在此学生ID，请联系管理员添加", "", JOptionPane.WARNING_MESSAGE);
                    } else if (loginStudent.getPassword() == null) {
                        //存在此ID,且未注册
                        String password = registerPanel.jtPassword.getText();
                        if ( password.length()<21 && password.length()>5 ) {
                            //密码长度正确
                            StudentDao.regist(account, password);
                            JOptionPane.showMessageDialog(LoginWin.this, "注册成功！\n返回登录界面");
                            registerPanel.minorButton.doClick();
                            loginPanel.jtAccount.setText(account);
                        } else {
                            //密码长度越界
                            JOptionPane.showMessageDialog(LoginWin.this, "密码长应为6-20位", "错误", JOptionPane.ERROR_MESSAGE);
                            registerPanel.jtPassword.setForeground(Color.red);
                            registerPanel.jlPassword.setForeground(Color.red);
                        }
                    } else {
                        //存在ID,但已注册
                        int answer = JOptionPane.showConfirmDialog(LoginWin.this, "该学生已注册，是否转到登录界面？", "提示", JOptionPane.YES_NO_OPTION);
                        if (answer == JOptionPane.YES_OPTION) {
                            registerPanel.minorButton.doClick();
                            loginPanel.jtAccount.setText(account);
                        }
                    }
                } else if (account.matches("T\\d{8}")) {
                    //匹配教师ID
                    Teacher loginteacher = TeacherDao.getTeacher(account);
                    if(loginteacher == null){
                        //不存在此ID
                        String password = loginPanel.jtPassword.getText();
                        if ( password.length()<21 && password.length()>5 ) {
                            //密码长度正确
                            JOptionPane.showMessageDialog(LoginWin.this, "注册成功！\n返回登录界面");
                            registerPanel.minorButton.doClick();
                            loginPanel.jtAccount.setText(account);
                        } else {
                            //密码长度越界
                            JOptionPane.showMessageDialog(LoginWin.this, "密码长应为6-20位", "错误", JOptionPane.ERROR_MESSAGE);
                            registerPanel.jtPassword.setForeground(Color.red);
                            registerPanel.jlPassword.setForeground(Color.red);
                        }
                    } else {
                        JOptionPane.showMessageDialog(LoginWin.this, "不存在此ID", "", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginWin.this, "ID格式有误", "错误", JOptionPane.ERROR_MESSAGE);
                    registerPanel.jtAccount.setForeground(Color.red);
                    registerPanel.jlAccount.setForeground(Color.red);
                }
            });
            //-------------注册次按钮---转到登录
            registerPanel.minorButton.addActionListener(e -> {
                loginPanel.disableButton();
                registerPanel.disableButton();
                ToLog.start();
                loginPanel.clear();
                registerPanel.clear();
            });
        }

        //--------------------------向窗口添加组件、设置窗口属性--------------------------//
        this.add(MainJP);
        this.setSize(400, 270);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginWin();
    }
}

/**
 * LoginWin内使用的登录界面
 */
class LoginPanel extends JPanel {
    JButton mainButton;
    JButton minorButton;
    JTextField jtAccount;
    JPasswordField jtPassword;
    JLabel welcome;
    JLabel jlAccount;
    JLabel jlPassword;

    public LoginPanel() {
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(374, 250));

        //-----------------页顶欢迎语
        welcome = new JLabel("欢迎使用成绩管理系统");
        welcome.setFont(new Font("等线", Font.BOLD, 30));
        welcome.setPreferredSize(new Dimension(360, 50));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setBorder(new EtchedBorder());

        //-----------------定义公用字体及尺寸
        Font font = new Font("等线", Font.BOLD, 20);
        Font font2 = new Font("等线", Font.PLAIN, 15);
        Dimension preSizeL = new Dimension(90, 45);
        Dimension preSizeT = new Dimension(250, 45);

        //-----------------提示语与输入框
        jlAccount = new JLabel("登录ID： ");
        jtAccount = new JTextField("请输入ID");
        jlPassword = new JLabel("密码： ");
        jtPassword = new JPasswordField();

        //-----------------初始化提示语与输入框
        jlAccount.setFont(font);
        jlPassword.setFont(font);
        jlAccount.setPreferredSize(preSizeL);
        jlPassword.setPreferredSize(preSizeL);
        jtAccount.setPreferredSize(preSizeT);
        jtPassword.setPreferredSize(preSizeT);
        jtAccount.setForeground(Color.gray);
        jtAccount.setFont(font2);
        jtPassword.setFont(font2);
        jtAccount.setBorder(new LineBorder(Color.blue));

        //-----------------输入框焦点监听
        jtAccount.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jtAccount.getText().equals("请输入ID")) {
                    jtAccount.setText("");
                }
                jtAccount.setForeground(Color.black);
                jlAccount.setForeground(Color.black);
                jtAccount.setBorder(new LineBorder(Color.blue));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jtAccount.getText().equals("")) {
                    jtAccount.setText("请输入ID");
                    jtAccount.setForeground(Color.gray);
                }
                jtAccount.setBorder(new LineBorder(Color.gray));
            }
        });
        jtPassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jtPassword.setForeground(Color.black);
                jlPassword.setForeground(Color.black);
                jtPassword.setBorder(new LineBorder(Color.blue));
            }

            @Override
            public void focusLost(FocusEvent e) {
                jtPassword.setBorder(new LineBorder(Color.gray));
            }
        });
        //-----------------输入框回车监听
        jtAccount.addActionListener(e->{
            mainButton.doClick();
        });
        jtPassword.addActionListener(e->{
            mainButton.doClick();
        });


        //-----------------主次按钮
        mainButton = new JButton();
        minorButton = new JButton();
        mainButton.setFont(new Font("等线", Font.BOLD, 25));
        minorButton.setFont(new Font("等线", Font.PLAIN, 14));
        mainButton.setPreferredSize(preSizeT);
        minorButton.setPreferredSize(preSizeL);

        //-----------------添加控件
        this.add(welcome);
        this.add(jlAccount);
        this.add(jtAccount);
        this.add(jlPassword);
        this.add(jtPassword);
        this.add(minorButton);
        this.add(mainButton);
    }

    //-----------------清除错误提示
    void clear() {
        jtAccount.setText("");
        jtPassword.setText("");
    }

    //-----------------禁用按钮
    void disableButton() {
        mainButton.setEnabled(false);
        minorButton.setEnabled(false);
    }

    //-----------------启用按钮
    void enableButton() {
        mainButton.setEnabled(true);
        minorButton.setEnabled(true);
    }
}
