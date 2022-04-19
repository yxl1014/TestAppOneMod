package yxl.UserAndTask.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CheckUserHandle checkUserHandle;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> list=new ArrayList<>();
        list.add("/users/**");
        list.add("/tasks/**");
        registry.addInterceptor(checkUserHandle).addPathPatterns(list);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 匹配所有的路径
                .allowCredentials(true) // 设置允许凭证
                .allowedHeaders("*")   // 设置请求头
                .allowedMethods("GET","POST","PUT","DELETE") // 设置允许的方式
                .allowedOriginPatterns("*");
    }
}
