package model;

import java.util.Objects;

/**
 * @author Delta
 * Created in 2021-07-06 13:55
 */
public class SClass {
    private String classID;
    private String className;

    public SClass(String classID, String className) {
        this.classID = classID;
        this.className = className;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SClass sClass = (SClass) o;
        return classID.equals(sClass.classID)/* &&
                className.equals(sClass.className)*/;
    }

    @Override
    public int hashCode() {
        return Objects.hash(classID/*, className*/);
    }

    @Override
    public String toString() {
        return "SClass{" +
                "classID='" + classID + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
