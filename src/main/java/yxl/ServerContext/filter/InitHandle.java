package yxl.ServerContext.filter;

import org.apache.hadoop.util.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import yxl.UserAndTask.entity.Position.Position;
import yxl.UserAndTask.entity.User;
import yxl.dto.ServerProto;
import yxl.utils.IpUtil;
import yxl.utils.PosUtil;
import yxl.utils.TlUserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @Author: yxl
 * @Date: 2022/8/1 15:25
 */
@Component
public class InitHandle implements HandlerInterceptor {

    @Autowired
    private IpUtil ipUtil;

    @Autowired
    private PosUtil posUtil;

    @Autowired
    private HashMap<String, ServerProto.UserIP> userMap;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = ipUtil.getIpAddress(request);
        Position pos = posUtil.getPosByIp(ip);
        User user = TlUserUtil.getThreadLocal();
        if (user == null) {
            response.sendError(1014, "用户登录已过期");
            return false;
        }
        String u_id = user.getU_id();

        return true;
    }
}
