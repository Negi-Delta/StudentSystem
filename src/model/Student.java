package model;

/**
 * @author Delta
 * Created in 2021-07-04 17:51
 */
public class Student extends Users{
    public Student(String accound, String password, String nickName) {
        super(accound, password, nickName, "Student");
    }
}
