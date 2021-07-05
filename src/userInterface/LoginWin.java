package userInterface;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @author Delta
 * Created in 2021-07-04 19:41
 */
public class LoginWin extends JFrame {
    public LoginWin(JFrame parentFrame) {
        super("欢迎");
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //-------------登录注册组件
        LoginPanel loginPanel = new LoginPanel();
//        loginPanel.setBackground(new Color(187, 255, 232));
        LoginPanel registerPanel = new LoginPanel();
//        registerPanel.setBackground(new Color(255, 187, 232));
        loginPanel.mainButton.setText("登录");
        loginPanel.minorButton.setText("转到注册");
        registerPanel.mainButton.setText("注册");
        registerPanel.minorButton.setText("转到登录");
        registerPanel.welcome.setText("注 册");
        loginPanel.setOpaque(false);
        registerPanel.setOpaque(false);

        //-------------登录注册组件容器
        JPanel MainJP = new JPanel();
        MainJP.setLayout(new GridLayout(1, 2));
        //        MainJP.setBackground(Color.pink);
        MainJP.add(loginPanel, "Login");
        MainJP.add(registerPanel, "Signin");
        MainJP.setBounds(0, 0, 800, 271);
        MainJP.setBackground(new Color(187, 255, 232));

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
            } catch (IllegalArgumentException exception) {}
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
            } catch (IllegalArgumentException exception) {}
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
            JOptionPane.showMessageDialog(LoginWin.this, "登录成功！");
            LoginWin.this.setVisible(false);
            LoginWin.this.setEnabled(false);
            parentFrame.setVisible(true);
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
            JOptionPane.showMessageDialog(LoginWin.this, "注册成功！\n返回登录界面");
            registerPanel.minorButton.doClick();
            loginPanel.clear();
            loginPanel.jtAccount.setText(registerPanel.jtAccount.getText());//填充注册账号
            registerPanel.clear();
        });
        //-------------注册次按钮---转到登录
        registerPanel.minorButton.addActionListener(e -> {
            loginPanel.disableButton();
            registerPanel.disableButton();
            ToLog.start();
            loginPanel.clear();
        });

        //-------------向窗口添加组件、设置窗口属性
        this.add(MainJP);
        this.setSize(400, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

/**
 * LoginWin内使用的登录界面
 */
class LoginPanel extends JPanel {
    JButton mainButton;
    JButton minorButton;
    JTextField jtAccount;
    JTextField jtPassword;
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


        //-----------------定义公用字体及尺寸
        Font font = new Font("等线", Font.BOLD, 20);
        Font font2 = new Font("等线", Font.PLAIN, 15);
        Dimension preSizeL = new Dimension(90, 45);
        Dimension preSizeT = new Dimension(250, 45);

        //-----------------提示语与输入框
        jlAccount = new JLabel("用户名： ");
        jtAccount = new JTextField("请输入用户名");
        jlPassword = new JLabel("密码： ");
        jtPassword = new JTextField("请输入密码");

        //-----------------初始化提示语与输入框
        jlAccount.setFont(font);
        jlPassword.setFont(font);
        jlAccount.setPreferredSize(preSizeL);
        jlPassword.setPreferredSize(preSizeL);
        jtAccount.setPreferredSize(preSizeT);
        jtPassword.setPreferredSize(preSizeT);
        jtAccount.setForeground(Color.gray);
        jtPassword.setForeground(Color.gray);
        jtAccount.setFont(font2);
        jtPassword.setFont(font2);
        //-----------------输入框焦点监听
        jtAccount.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(jtAccount.getText().equals("请输入用户名")){
                    jtAccount.setText("");
                }
                jlAccount.setForeground(Color.black);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(jtAccount.getText().equals("")){
                    jtAccount.setText("请输入用户名");
                    jlAccount.setForeground(Color.red);
                }
            }
        });
        jtPassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jtPassword.getText().equals("请输入密码")){
                    jtPassword.setText("");
                }
                jlPassword.setForeground(Color.black);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(jtPassword.getText().equals("")){
                    jtPassword.setText("请输入密码");
                    jlPassword.setForeground(Color.red);
                }
            }
        });

        //-----------------主次按钮
        mainButton = new JButton();
        minorButton = new JButton();
        mainButton.setFont(new Font("等线", Font.BOLD, 25));
        minorButton.setFont(new Font("等线", Font.PLAIN, 14));
        mainButton.setPreferredSize(preSizeT);
        minorButton.setPreferredSize(preSizeL);



        welcome.setBorder(new EtchedBorder());
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
    void clear(){
        jtAccount.setText("");
        jtPassword.setText("");
        jlAccount.setForeground(Color.black);
        jlPassword.setForeground(Color.black);
    }
    //-----------------禁用按钮
    void disableButton(){
        mainButton.setEnabled(false);
        minorButton.setEnabled(false);
    }
    //-----------------启用按钮
    void enableButton(){
        mainButton.setEnabled(true);
        minorButton.setEnabled(true);
    }
}
