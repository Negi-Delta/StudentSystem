package model;

/**
 * @author Delta
 * Created in 2021-07-04 17:51
 */
public class Teacher extends Users{
    public Teacher(String accound, String password, String nickName) {
        super(accound, password, nickName, "Teacher");
    }
}
