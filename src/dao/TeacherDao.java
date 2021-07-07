package dao;

import model.Student;
import model.Teacher;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Delta
 * Created in 2021-07-07 13:24
 */
public class TeacherDao {
    public static ArrayList<Teacher> getTeacherList() {
        ArrayList<Teacher> teacherList = new ArrayList<>();
        Statement sta = null;
        ResultSet res = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.createStatement();
            res = sta.executeQuery("SELECT * FROM teacher");
            while (res.next()) {
                teacherList.add(new Teacher(
                        res.getString("idNumber"),
                        res.getString("password"),
                        ""
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (sta != null) {
                    sta.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            ConnectDao.closeConnection();
            return teacherList;
        }
    }

    public static ArrayList<Integer> containTeacher(ArrayList<Teacher> teacherList){
        ArrayList<Integer> res = new ArrayList<>();
        int index = -1;
        ArrayList<Teacher> curTeacher = TeacherDao.getTeacherList();
        for (Teacher sTeacher : teacherList) {
            index++;
            if (curTeacher.contains(sTeacher)) {
                res.add(index);
                break;
            }
        }
        return res;
    }

    public static void addTeacher(ArrayList<Teacher> teacherList) {
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("INSERT INTO teacher VALUES (?, ?)");
            for (Teacher sTeacher : teacherList) {
                sta.setString(1, sTeacher.getIdNumber());
                sta.setString(2, sTeacher.getPassword());
                sta.addBatch();
            }
            sta.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sta != null) sta.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
//            ConnectDao.closeConnection();
        }
    }

    public static void delTeacher(ArrayList<Teacher> teacherList){
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("DELETE FROM teacher where teacherID=?");
            for (Teacher sTeacher : teacherList) {
                sta.setString(1, sTeacher.getIdNumber());
                sta.addBatch();
            }
            sta.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sta != null) sta.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
//            ConnectDao.closeConnection();
        }
    }
    public static void delTeacher(int... ids){
        ArrayList<Teacher> teachers = new ArrayList<>();
        for (int id : ids) {
            teachers.add(new Teacher(String.valueOf(id), "", ""));
        }
        delTeacher(teachers);
    }
}
