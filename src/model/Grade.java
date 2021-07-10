package model;

import java.util.Objects;

/**
 * @author Delta
 * Created in 2021-07-07 17:48
 */
public class Grade {
    private String idNumber;
    private String courseID;
    private String grade;

    public Grade(String idNumber, String courseID, String grade) {
        this.idNumber = idNumber;
        this.courseID = courseID;
        this.grade = grade;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return Objects.equals(idNumber, grade.idNumber) &&
                Objects.equals(courseID, grade.courseID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNumber, courseID);
    }

    @Override
    public String toString() {
        return "Grade{" +
                "idNumber='" + idNumber + '\'' +
                ", courseID='" + courseID + '\'' +
                ", grade=" + grade +
                '}';
    }
}
