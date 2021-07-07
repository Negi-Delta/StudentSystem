package dao;

import model.Student;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Delta
 * Created in 2021-07-07 13:23
 */
public class StudentDao {
    public static ArrayList<Student> getStudentList() {
        ArrayList<Student> studentList = new ArrayList<>();
        Statement sta = null;
        ResultSet res = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.createStatement();
            res = sta.executeQuery("SELECT * FROM students");
            while (res.next()) {
                studentList.add(new Student(
                        res.getString("idNumber"),
                        res.getString("password"),
                        res.getString("name"),
                        res.getString("classID")
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
            return studentList;
        }
    }

    public static ArrayList<Integer> containStudent(ArrayList<Student> studentList) {
        ArrayList<Integer> res = new ArrayList<>();
        int index = -1;
        ArrayList<Student> curStudent = StudentDao.getStudentList();
        for (Student Student : studentList) {
            index++;
            if (curStudent.contains(Student)) {
                res.add(index);
                break;
            }
        }
        return res;
    }

    public static void addStudent(ArrayList<Student> studentList) {
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("INSERT INTO students VALUES (?, ?, ?, ?)");
            for (Student Student : studentList) {
                sta.setString(1, Student.getIdNumber());
                sta.setString(2, Student.getPassword());
                sta.setString(3, Student.getName());
                sta.setString(4, Student.getClassID());
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

    public static void delStudent(ArrayList<Student> studentList) {
        PreparedStatement sta1 = null;
        PreparedStatement sta2 = null;
        Connection con = ConnectDao.getConection();
        try {
            sta1 = con.prepareStatement("DELETE FROM stucougra where idnumber=?");
            sta2 = con.prepareStatement("DELETE FROM students WHERE idnumber=? ");
            for (Student Student : studentList) {
                sta1.setString(1, Student.getIdNumber());
                sta1.addBatch();
                sta2.setString(1, Student.getIdNumber());
                sta2.addBatch();
            }
            sta1.executeBatch();
            sta2.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sta1 != null) sta1.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (sta2 != null) sta2.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
//            ConnectDao.closeConnection();
        }
    }

    public static void delStudent(int... ids){
        ArrayList<Student> students = new ArrayList<>();
        for (int id : ids) {
            students.add(new Student(String.valueOf(id), "", "", ""));
        }
        delStudent(students);
    }

    public static void updateStudent(ArrayList<Student> studentList) {
        PreparedStatement sta1 = null;
        PreparedStatement sta2 = null;
        PreparedStatement sta3 = null;
        Connection con = ConnectDao.getConection();
        try {
            sta1 = con.prepareStatement(
                    "UPDATE students set passWord=?, name=?, classid=? where idnumber=?"
            );
//            sta2 = con.prepareStatement("UPDATE students set name=? where idnumber=?");
//            sta3 = con.prepareStatement("UPDATE students set classid=? where idnumber=?");
            for (Student student : studentList) {
                sta1.setString(1, student.getPassword());
                sta1.setString(2, student.getName());
                sta1.setString(3, student.getClassID());
                sta1.setString(4, student.getIdNumber());
                sta1.addBatch();
//                sta2.setString(1, student.getName());
//                sta2.setString(2, student.getIdNumber());
//                sta2.addBatch();
//                sta3.setString(1, student.getClassID());
//                sta3.setString(2, student.getIdNumber());
//                sta3.addBatch();
            }
            sta1.executeBatch();
//            sta2.executeBatch();
//            sta3.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sta1 != null) sta1.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
//            ConnectDao.closeConnection();
        }
    }

    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        {
            //query
            System.out.println(StudentDao.getStudentList());
            students.clear();
        }
        {
            //add
//            students.add(new Student("S20192002", "654321", "zhangsan", null));
//            StudentDao.addStudent(students);
        }
        {
            //update
//            students.add(new Student(
//                    "S20192002", "654321", "zhangsan", "C20192002"
//            ));
//            StudentDao.updateStudent(students);
        }
        {
            //del
//            students.add(
//                    new Student("S20192001", "123456", "lisi", "C20192001")
//            );
//            StudentDao.delStudent(students);
        }
    }
}
