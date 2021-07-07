package dao;

import model.SClass;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * @author Delta
 * Created in 2021-07-07 13:24
 */
public class ClassDao {
    public static ArrayList<SClass> getClassList() {
        ArrayList<SClass> classList = new ArrayList<>();
        Statement sta = null;
        ResultSet res = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.createStatement();
            res = sta.executeQuery("SELECT * FROM class");
            while (res.next()) {
                classList.add(new SClass(
                        res.getString("classID"),
                        res.getString("className")
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
            return classList;
        }
    }

    /**
     *
     * @param classList
     * @return ArrayList<Integer> index
     */
    public static ArrayList<Integer> containClass(ArrayList<SClass> classList){
        ArrayList<Integer> res = new ArrayList<>();
        int index = -1;
        ArrayList<SClass> curSClass = ClassDao.getClassList();
        for (SClass sClass : classList) {
            index++;
            if (curSClass.contains(sClass)) {
                res.add(index);
                break;
            }
        }
        return res;
    }

    public static void addClass(ArrayList<SClass> classList) {
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("INSERT INTO class VALUES (?, ?)");
            for (SClass sClass : classList) {
                sta.setString(1, sClass.getClassID());
                sta.setString(2, sClass.getClassName());
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

    public static void delClass(ArrayList<SClass> classList){
        PreparedStatement sta1 = null;
        PreparedStatement sta2 = null;
        Connection con = ConnectDao.getConection();
        try {
            sta1 = con.prepareStatement("UPDATE students set classID=null where classID=?");
            sta2 = con.prepareStatement("DELETE FROM class WHERE classID=? ");
            for (SClass sClass : classList) {
                sta1.setString(1, sClass.getClassID());
                sta1.addBatch();
                sta2.setString(1, sClass.getClassID());
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

    public static void delClass(int... ids){
        ArrayList<SClass> classes = new ArrayList<>();
        for (int id : ids) {
            classes.add(new SClass(String.valueOf(id), ""));
        }
        delClass(classes);
    }

    public static void updateClass(ArrayList<SClass> classList) {
        PreparedStatement sta = null;
        Connection con = ConnectDao.getConection();
        try {
            sta = con.prepareStatement("UPDATE class set className=? where classID=?");
            for (SClass sClass : classList) {
                sta.setString(1, sClass.getClassName());
                sta.setString(2, sClass.getClassID());
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
        ArrayList<SClass> classList = new ArrayList<>();
        {
            //query
            System.out.println(ClassDao.getClassList());
            classList.clear();
        }

        {
            //add
            classList.add(new SClass("C20192002", "虚拟现实192班"));
            classList.add(new SClass("C20192003", "虚拟现实193班"));
            classList.add(new SClass("C20192004", "虚拟现实194班"));
            //对于参数集，若已存在课程则返回下标

            if (ClassDao.containClass(classList).size()==0) {
                ClassDao.addClass(classList);
                System.out.println("add: "+classList);
            } else {
                System.out.println("exist");
            }
            classList.clear();
        }
        {
            //update
            classList.add(new SClass("C20192002", "虚拟现实193班"));
            ArrayList<Integer> containList = ClassDao.containClass(classList);
            if(containList.size()==1) {
                System.out.println("containList.size(): "+containList.size());
                ClassDao.updateClass(classList);
            } else {
                System.out.println("not exist");
            }
            classList.clear();
        }
        {
            //delete
            classList.add(new SClass("C20192004", "虚拟现实193班"));
            ArrayList<Integer> containList = ClassDao.containClass(classList);
            if (containList.size()==0){
                System.out.println("not exist");
            } else {
                ClassDao.delClass(classList);
                System.out.println("del" + containList);
            }
        }

        System.out.println("Finish");
        ConnectDao.closeConnection();
    }
}
