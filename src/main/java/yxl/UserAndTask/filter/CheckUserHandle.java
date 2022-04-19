package yxl.UserAndTask.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import yxl.UserAndTask.annotation.LogWeb;
import yxl.UserAndTask.annotation.NoToken;
import yxl.UserAndTask.entity.User;
import yxl.UserAndTask.util.JWTUtil;
import yxl.UserAndTask.util.LogUtil;
import yxl.UserAndTask.util.TlUserUtil;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


@Component
public class CheckUserHandle implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");


        if (!(handler instanceof HandlerMethod))
            return false;

        User user = JWTUtil.unsign(token, User.class);

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (!method.isAnnotationPresent(LogWeb.class)) {
            return false;
        }

        LogWeb log = method.getAnnotation(LogWeb.class);
        LogUtil.info("url:" + log.url() + "\t;op:" + log.op() + ";\ttype:" + log.type());


        if (!method.isAnnotationPresent(NoToken.class)) {//获取这个方法是否有这个注释
            // 执行认证
            if (token == null) {
                return false;
                //throw new RuntimeException("无token，请重新登录");
            }
            // 获取 token 中的 user
            //throw new RuntimeException("用户不存在，请重新登录");
            TlUserUtil.setThreadLocal(user);
            return user != null;
        }
        TlUserUtil.setThreadLocal(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TlUserUtil.removeThreadLocal();
    }
}
