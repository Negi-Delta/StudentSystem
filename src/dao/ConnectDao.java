package dao;

import com.sun.javafx.logging.PulseLogger;

import java.sql.*;

/**
 * @author Delta
 * Created in 2021-07-04 17:55
 */
public class ConnectDao {
    private ConnectDao() {
    }

    private static Connection con = null;

    public static java.sql.Connection getConection() {
        if (con == null) {
            synchronized (ConnectDao.class) {
                if (con == null) {
                    String driver = "com.mysql.cj.jdbc.Driver";
                    String encoding = "?useUnicode=true&characterEncoding=utf8";
                    String url = "jdbc:mysql://localhost:3306/studentSystem"+encoding;
                    String user = "root";
                    String password = "Ikaros";

                    try {
                        Class.forName(driver);
                        con = DriverManager.getConnection(url, user, password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return con;
    }

    public static void closeConnection(){
        if ( con != null ){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
