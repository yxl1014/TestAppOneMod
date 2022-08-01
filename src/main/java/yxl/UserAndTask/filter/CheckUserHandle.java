package yxl.UserAndTask.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import yxl.DataToMysql.util.UserUtil;
import yxl.UserAndTask.annotation.LogWeb;
import yxl.UserAndTask.annotation.NoToken;
import yxl.UserAndTask.entity.User;
import yxl.utils.JWTUtil;
import yxl.utils.LogUtil;
import yxl.utils.TlUserUtil;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


@Component
public class CheckUserHandle implements HandlerInterceptor {

    @Autowired
    private UserUtil userUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            response.sendError(1014, "非本系统接口");
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (!method.isAnnotationPresent(LogWeb.class)) {
            response.sendError(1014, "非本系统接口");
            return false;
        }

        LogWeb log = method.getAnnotation(LogWeb.class);
        LogUtil.info("url:" + log.url() + "\t;op:" + log.op() + ";\ttype:" + log.type());

        if (!method.isAnnotationPresent(NoToken.class)) {//获取这个方法是否有这个注释
            String token = request.getHeader("token");
            // 执行认证
            if (token == null) {
                response.sendError(1014, "访问未携带token");
                return false;
            }
            User user = JWTUtil.unsign(token, User.class);
            if (user == null) {
                response.sendError(1014, "用户登录已过期");
                return false;
            }
            User u = userUtil.findbyTelAndPassword(user.getU_tel(), user.getU_password());
            if (u == null) {
                response.sendError(1014, "该用户不存在，非法访问或已修改密码");
                return false;
            }
            TlUserUtil.setThreadLocal(user);
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        TlUserUtil.removeThreadLocal();
    }
}
