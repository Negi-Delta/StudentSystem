package model;

import java.util.Objects;

/**
 * @author Delta
 * Created in 2021-07-04 17:51
 */
public abstract class Users {
    String idNumber;
    String password;
    String name;

    public Users(String idNumber, String password, String name) {
        this.idNumber = idNumber;
        this.password = password;
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(idNumber, users.idNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNumber);
    }

    @Override
    public String toString() {
        return "Users{" +
                "idNumber='" + idNumber + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
