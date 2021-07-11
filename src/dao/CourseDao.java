package dao;

import model.Course;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.SplittableRandom;

/**
 * @author Delta
 * Created in 2021-07-07 13:24
 */
public class CourseDao {
    public static ArrayList<Course> getCourseList() {
        ArrayList<Course> courseList = new ArrayList<>();
        Statement sta = null;
        ResultSet res = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.createStatement();
            res = sta.executeQuery("SELECT * FROM course");
            while (res.next()) {
                courseList.add(new Course(
                        res.getString("courseID"),
                        res.getString("courseName")
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
            return courseList;
        }
    }

    public static String getCourseName(String courseID) {
        String courseName = null;
        PreparedStatement sta = null;
        ResultSet res = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("SELECT courseName FROM course WHERE courseID=?");
            sta.setString(1, courseID);
            res = sta.executeQuery();
            if (res.next()) {
                courseName = res.getString("courseName");
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
            return courseName;
        }
    }

    public static ArrayList<Integer> containCourses(ArrayList<Course> courseList) {
        ArrayList<Integer> res = new ArrayList<>();
        int index = -1;
        ArrayList<Course> curCourse = CourseDao.getCourseList();
        for (Course sCourse : courseList) {
            index++;
            if (curCourse.contains(sCourse)) {
                res.add(index);
                break;
            }
        }
        return res;
    }

    public static ArrayList<Integer> containCourses(String... ids) {
        ArrayList<Course> res = new ArrayList<>();
        for (String id : ids) {
            res.add(new Course(id, ""));
        }
        return containCourses(res);
    }

    public static boolean containCourse(String courseID) {
        boolean res = false;
        if (containCourses(courseID).size() > 0) {
            res = true;
        }
        return res;
    }

    public static void addCourse(ArrayList<Course> courseList) {
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("INSERT INTO course VALUES (?, ?)");
            for (Course Course : courseList) {
                sta.setString(1, Course.getCourseID());
                sta.setString(2, Course.getCourseName());
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

    public static void delCourse(ArrayList<Course> courseList) {
        PreparedStatement sta1 = null;
        PreparedStatement sta2 = null;
        Connection con = ConnectDao.getConection();
        try {
            sta1 = con.prepareStatement("DELETE FROM stucougra where courseID=?");
            sta2 = con.prepareStatement("DELETE FROM course WHERE courseID=? ");
            for (Course Course : courseList) {
                sta1.setString(1, Course.getCourseID());
                sta1.addBatch();
                sta2.setString(1, Course.getCourseID());
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

    public static void delCourse(int... ids) {
        ArrayList<Course> courses = new ArrayList<>();
        for (int id : ids) {
            courses.add(new Course(String.valueOf(id), ""));
        }
        delCourse(courses);
    }

    public static void updateCourse(ArrayList<Course> courseList) {
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("UPDATE course set courseName=? where courseID=?");
            for (Course Course : courseList) {
                sta.setString(1, Course.getCourseName());
                sta.setString(2, Course.getCourseID());
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
}
