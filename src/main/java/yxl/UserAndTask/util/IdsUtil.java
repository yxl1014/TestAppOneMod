package yxl.UserAndTask.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IdsUtil {
    private final static Date date = new Date();
    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

    private final static Random r=new Random();

    public static String getUid() {
        StringBuffer sb = new StringBuffer();
        sb.append(formatter.format(date));
        sb.append(RandomB());
        sb.append(RandomS());
        sb.append(r.nextInt(10));
        sb.append(r.nextInt(10));
        return sb.toString();
    }

    public static String getTid(String type,String uid3){
        StringBuffer sb = new StringBuffer();
        sb.append(formatter.format(date));
        sb.append(type);
        sb.append(uid3);
        sb.append(RandomB());
        sb.append(RandomS());
        sb.append(r.nextInt(10));
        sb.append(r.nextInt(10));
        return sb.toString();
    }

    public static String getUtid(String uid4,String tid4){
        StringBuffer sb = new StringBuffer();
        sb.append(formatter.format(date));
        sb.append(uid4);
        sb.append(tid4);
        return sb.toString();
    }

    private static char RandomB(){
        return (char) (int) (Math.random() * 26 + 65);
    }

    private static char RandomS(){
        return (char) (int) (Math.random() * 26 + 97);
    }

}
