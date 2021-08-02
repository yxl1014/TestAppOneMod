package yxl.UserAndTask.annotation;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER,ElementType.METHOD }) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented//生成文档
public @interface LogWeb {

    String url() default "/";
    String op() default  " ";
    String type();
}
