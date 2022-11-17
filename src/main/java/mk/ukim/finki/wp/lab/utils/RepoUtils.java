package mk.ukim.finki.wp.lab.utils;

public class RepoUtils {
    public static Long generatePrimaryKey() {
        return (long) (Math.random() * 1000);
    }
}
