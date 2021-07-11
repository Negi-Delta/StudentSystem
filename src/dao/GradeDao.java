package dao;

import model.Grade;
import model.Student;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Delta
 * Created in 2021-07-07 13:24
 */
public class GradeDao {
    public static ArrayList<Grade> getGradeList() {
        ArrayList<Grade> gradeList = new ArrayList<>();
        Statement sta = null;
        ResultSet res = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.createStatement();
            res = sta.executeQuery("SELECT * FROM stucougra");
            while (res.next()) {
                gradeList.add(new Grade(
                        res.getString("idNumber"),
                        res.getString("CourseID"),
                        res.getString("Grade")
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
            return gradeList;
        }
    }

    public static ArrayList<Grade> getStudentGrade(String idNumber) {
        ArrayList<Grade> gradeList = new ArrayList<>();
        PreparedStatement sta = null;
        ResultSet res = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("SELECT * FROM stucougra WHERE idNumber=?");
            sta.setString(1, idNumber);
            res = sta.executeQuery();
            while (res.next()) {
                gradeList.add(new Grade(
                        res.getString("idNumber"),
                        res.getString("CourseID"),
                        res.getString("Grade")
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
            return gradeList;
        }
    }

    public static ArrayList<Integer> containGrade(ArrayList<Grade> gradeList){
        ArrayList<Integer> res = new ArrayList<>();
        int index = -1;
        ArrayList<Grade> curGrade = GradeDao.getGradeList();
        for (Grade Grade : gradeList) {
            index++;
            if (curGrade.contains(Grade)) {
                res.add(index);
                break;
            }
        }
        return res;
    }

    public static void addGrade(ArrayList<Grade> gradeList) {
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("INSERT INTO stucougra VALUES (?, ?, ?)");
            for (Grade Grade : gradeList) {
                sta.setString(1, Grade.getIdNumber());
                sta.setString(2, Grade.getCourseID());
                sta.setString(3, Grade.getGrade());
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

    public static void delEletive(ArrayList<Grade> gradeList){
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("DELETE FROM stucougra WHERE idNumber=? AND CourseID=?");
            for (Grade Grade : gradeList) {
                sta.setString(1, Grade.getIdNumber());
                sta.setString(2, Grade.getCourseID());
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

    public static void delGrade(){

    }

    public static void updateGrade(ArrayList<Grade> gradeList) {
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("UPDATE stucougra set Grade=? where idNumber=? AND courseID=?");
            for (Grade grade : gradeList) {
                sta.setString(1, grade.getGrade());
                sta.setString(2, grade.getIdNumber());
                sta.setString(3, grade.getCourseID());
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

    public static void main(String[] args) {
        ArrayList<Grade> grades = new ArrayList<>();
        {
            //query
            System.out.println(GradeDao.getGradeList());
            grades.clear();
        }
        {
            //add
//            grades.add(new Grade("S20192002", "D20192001", 100));
//            grades.add(new Grade("S20192002", "D20192002", 99));
//            GradeDao.addGrade(grades);
        }
        {
            //update
//            grades.add(new Grade("S20192002", "D20192001", 60));
//            GradeDao.updateGrade(grades);
        }
        {
            //del
//            grades.add(
//                    new Grade("S20192002", "D20192001", 20)
//            );
//            GradeDao.delGrade(grades);
        }
    }
}
