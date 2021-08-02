package yxl.UserAndTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yxl.DataToMysql.util.TaskUtil;
import yxl.DataToMysql.util.UtUtil;
import yxl.DataToMysql.util.UtwUtil;
import yxl.DataToRedis.util.RedisUtil;
import yxl.UserAndTask.entity.Task;
import yxl.UserAndTask.entity.User;
import yxl.UserAndTask.entity.Ut;
import yxl.UserAndTask.util.IdsUtil;
import yxl.UserAndTask.util.LocalTable;
import yxl.UserAndTask.util.TlUserUtil;


import java.sql.Timestamp;
import java.util.Date;

@Service
public class UtServiceImpl {

    @Autowired
    private UtUtil ut;

    @Autowired
    private UtwUtil utw;

    @Autowired
    private TaskUtil taskUtil;

    @Autowired
    private LocalTable localTable;

    @Autowired
    private RedisUtil redisUtil;


    public Task getTask(String tid, Integer ok) {
        Task t = localTable.getLocal(tid);
        if (t == null) {
            t = (Task) redisUtil.get(tid);
        }
        if (t == null) {
            t = taskUtil.findTaskbyId(tid);
        }
        if (t == null) {//3:任务不存在
            ok = 3;
            return null;
        }
        User u = TlUserUtil.getThreadLocal();
        boolean ok1 = ut.insertut(new Ut(IdsUtil.getUtid(u.getU_id().substring(14), tid.substring(20)),
                u.getU_id(), tid, new Timestamp(new Date().getTime()), "暂停", 0));
        ok = 0;
        if (ok1)
            ok = -1;
        return t;
    }
}
