package model;

/**
 * @author Delta
 * Created in 2021-07-04 17:51
 */
public class Student extends Users{
    String classID;

    public Student(String idNumber, String password, String name, String classID) {
        super(idNumber, password, name);
        this.classID = classID;
    }

    public Student(String idNumber, String name) {
        super(idNumber, null, name);
    }



    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    @Override
    public String toString() {
        return "Student{" +
                "idNumber='" + idNumber + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", classID='" + classID + '\'' +
                '}'+"\n";
    }
}
