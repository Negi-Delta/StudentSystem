package model;

/**
 * @author Delta
 * Created in 2021-07-04 17:51
 */
public abstract class Users {
    String accound;
    String password;
    String NickName;
    String Identity;

    public Users(String accound, String password, String nickName, String identity) {
        this.accound = accound;
        this.password = password;
        NickName = nickName;
        Identity = identity;
    }

}
