package dao;

import model.Grade;

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
            res = sta.executeQuery("SELECT * FROM grades");
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
            sta = con.prepareStatement("SELECT * FROM grades WHERE idNumber=?");
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
        for (Grade grade : gradeList) {
            index++;
            if (curGrade.contains(grade)) {
                res.add(index);
                break;
            }
        }
        return res;
    }

    public static void addGrades(ArrayList<Grade> gradeList) {
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("INSERT INTO grades VALUES (?, ?, ?)");
            for (Grade grade : gradeList) {
                sta.setString(1, grade.getIdNumber());
                sta.setString(2, grade.getCourseID());
                sta.setString(3, grade.getGrade());
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

    public static void addElective(String idNumber, String course) {
        ArrayList<Grade> gradeList = new ArrayList<>();
        System.out.println(course);
        gradeList.add(new Grade(idNumber, course, null));
        addGrades(gradeList);
    }

    public static void delElective(ArrayList<Grade> gradeList){
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("DELETE FROM grades WHERE idNumber=? AND CourseID=?");
            for (Grade grade : gradeList) {
                sta.setString(1, grade.getIdNumber());
                sta.setString(2, grade.getCourseID());
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

    public static void delElective(String idNumber, String courseID){
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("DELETE FROM grades WHERE idNumber=? AND CourseID=?");
            sta.setString(1, idNumber);
            sta.setString(2, courseID);
            sta.execute();
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

    public static void delGrade(String idNumber, String courseID){
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("UPDATE grades set Grade=null WHERE idNumber=? AND CourseID=?");
            sta.setString(1, idNumber);
            sta.setString(2, courseID);
            sta.execute();
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

    public static void updateGrades(ArrayList<Grade> gradeList) {
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("UPDATE grades set Grade=? where idNumber=? AND courseID=?");
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
        }
    }

    public static void updateGrade(String idNumber, String courseID, String grade) {
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement(
                    "UPDATE grades set Grade=? where idnumber=? AND courseID=?"
            );
            sta.setString(1, grade);
            sta.setString(2, idNumber);
            sta.setString(3, courseID);
            sta.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sta != null) sta.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
