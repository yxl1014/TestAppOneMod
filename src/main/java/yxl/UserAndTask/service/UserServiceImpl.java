package yxl.UserAndTask.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import yxl.DataToMysql.util.UserUtil;
import yxl.UserAndTask.entity.Producer;
import yxl.UserAndTask.entity.User;
import yxl.utils.IdsUtil;
import yxl.utils.LogUtil;
import yxl.utils.TlUserUtil;


@Service
public class UserServiceImpl {

    @Autowired
    private UserUtil util;


    public User login(@NonNull User user) {
        if (user.getU_tel() == null || user.getU_password() == null)
            return null;
        String tel = user.getU_tel();

        String password = DigestUtils.md5DigestAsHex(user.getU_password().getBytes());
        return util.findbyTelAndPassword(tel, password);
    }

    public int logon(@NonNull User user) {//1：用户存在 0：成功 -1:失败
        if (util.findUserbyTel(user.getU_tel()))
            return 1;
        user.setU_id(IdsUtil.getUid());
        user.setU_bag(0);
        user.setU_isProd(false);
        user.setU_vip(false);
        user.setU_password(DigestUtils.md5DigestAsHex(user.getU_password().getBytes()));
        return util.insertUser(user) ? 0 : -1;
    }

    public int update(@NonNull User user) {
        User old = TlUserUtil.getThreadLocal();
        if (user.getU_tel() != null)
            old.setU_tel(user.getU_tel());
        if (user.getU_email() != null)
            old.setU_email(user.getU_email());
        if (user.getU_name() != null)
            old.setU_name(user.getU_name());
        if (user.getU_password() != null)
            old.setU_password(DigestUtils.md5DigestAsHex(user.getU_password().getBytes()));
        boolean b = util.updateUser(old);
        return b ? 0 : -1;
    }

    public boolean uplevel(@NonNull Producer producer) {
        User user = TlUserUtil.getThreadLocal();
        boolean ok1 = util.uplevel(user.getU_id());
        if (!ok1) {
            LogUtil.error("user表修改失败");
        }
        producer.setP_uid(user.getU_id());
        boolean ok2 = util.insertProducer(producer);
        if (!ok2) {
            LogUtil.error("producer插入失败");
        }
        return ok1 && ok2;
    }

    public boolean tovip() {
        User user = TlUserUtil.getThreadLocal();
        return util.toVIP(user.getU_id());
    }

    public boolean payIn(float cost) {
        User user = TlUserUtil.getThreadLocal();
        return util.payIn(user.getU_bag() + cost, user.getU_id());
    }

    public int payOut(float cost,String cid) {//1:账户余额不足 0:成功  -1：失败
        User user = TlUserUtil.getThreadLocal();
        if (user.getU_bag() < cost)
            return 2;
        boolean ok = util.payIn(user.getU_bag() - cost, user.getU_id());
        return ok ? 0 : -1;
    }
}
