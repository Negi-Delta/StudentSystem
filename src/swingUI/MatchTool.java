package swingUI;

/**
 * @author Delta
 * Created in 2021-07-09 18:21
 * 匹配格式工具
 */
public class MatchTool {
    public static boolean isIdNumber(String id){
        return id.matches("S\\d{8}");
    }

    public static boolean isClassID(String classid) {
        return classid.matches("C\\d{8}");
    }

    public static boolean isCourseID(String courseid) {
        return courseid.matches("D\\d{8}");
    }

    public static boolean isTeacherID(String teacherid){
        return teacherid.matches("T\\d{8}");
    }
}
