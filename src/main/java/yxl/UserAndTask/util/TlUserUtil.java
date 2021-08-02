package yxl.UserAndTask.util;


import yxl.UserAndTask.entity.User;

public class TlUserUtil {
    private static ThreadLocal<User> threadLocal=new ThreadLocal<>();

    public static void setThreadLocal(User data){
        threadLocal.set(data);
    }

    public static User getThreadLocal(){
        return threadLocal.get();
    }

    public static void removeThreadLocal(){
        threadLocal.remove();
    }
}
