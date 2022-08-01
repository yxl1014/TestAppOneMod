package yxl.utils;

import com.google.gson.Gson;


public class GsonUtil {
    private static Gson gson=new Gson();

    public static<T> String toJson(T t){
        return gson.toJson(t);
    }
    public static<T> T fromJson(String json,Class<T> clazz){
        return gson.fromJson(json,clazz);
    }
}
