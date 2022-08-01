package yxl.ServerContext.context;

import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import yxl.UserAndTask.entity.User;
import yxl.utils.TlUserUtil;
import yxl.dto.ServerProto;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yxl
 * @Date: 2022/7/25 11:13
 */

@Component
public class ServerContext {


    @Autowired
    @Qualifier(value = "taskTimerMap")
    private HashMap<String, ServerProto.TaskTimer> map;

    public byte[] ping(byte[] data) {
        try {
            ServerProto.Ping ping = ServerProto.Ping.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            return ServerProto.Pong.newBuilder().setStatus(-1).setMsg("协议错误").build().toByteArray();
        }


        User u = TlUserUtil.getThreadLocal();

        if (u == null) {
            return ServerProto.Pong.newBuilder().setStatus(-1).setMsg("登陆已过期，请重新登陆").build().toByteArray();
        }


        ServerProto.Pong.Builder builder = ServerProto.Pong.newBuilder();
        ServerProto.TaskTimer taskTimer = map.get(u.getU_id());
        builder.setStatus(1);
        if (taskTimer != null) {
            builder.setMsg("接受任务");
            builder.addTask(taskTimer);
        } else {
            builder.setMsg("没有接受任务");
        }

        return builder.build().toByteArray();
    }
}
