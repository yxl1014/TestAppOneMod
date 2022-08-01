package yxl.DataToMysql.write;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import yxl.UserAndTask.entity.Producer;
import yxl.UserAndTask.entity.User;
import yxl.utils.LogUtil;


@Repository
public class UpdateUser {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //有邮箱的用户注册
    public boolean insertUser(User user) {
        String sql = "insert into users(u_id,u_tel,u_email,u_name,u_password) values(?,?,?,?,?)";
        int ok;
        try {
            ok = jdbcTemplate.update(sql, user.getU_id(), user.getU_tel(), user.getU_email(), user.getU_name(), user.getU_password());
        } catch (Exception ex) {
            LogUtil.error(ex.toString());
            ok = 0;
        }
        return ok == 1;
    }

    //无邮箱的用户注册
    public boolean insertUserNoemail(User user) {
        String sql = "insert into users(u_id,u_tel,u_name,u_password) values(?,?,?,?)";
        int ok;
        try {
            ok = jdbcTemplate.update(sql, user.getU_id(), user.getU_tel(), user.getU_name(), user.getU_password());
        } catch (Exception ex) {
            LogUtil.error(ex.toString());
            ok = 0;
        }
        return ok == 1;
    }

    //修改用户，先删除后添加
    public boolean updateUser(User user) {
        String dsql = "delete from users where u_id=?";
        String isql = "insert into users(u_id,u_tel,u_email,u_name,u_password,u_bag,u_vip,u_isProd) values(?,?,?,?,?,?,?,?)";
        int dok;
        int iok;
        try {
            dok = jdbcTemplate.update(dsql, user.getU_id());
            iok = jdbcTemplate.update(isql, user.getU_id(), user.getU_tel(), user.getU_name(), user.getU_password(), user.getU_bag(), user.isU_vip(), user.isU_isProd());
        } catch (Exception ex) {
            LogUtil.error(ex.toString());
            dok = 0;
            iok = 0;
        }
        return dok + iok == 2;
    }

    //增加提交者信息
    public boolean insertProducer(Producer pro) {
        String sql = "insert into producers(p_uid,p_company,p_address) values(?,?,?)";
        int ok;
        try {
            ok = jdbcTemplate.update(sql, pro.getP_uid(), pro.getP_company(), pro.getP_address());
        } catch (Exception ex) {
            LogUtil.error(ex.toString());
            ok = 0;
        }
        return ok == 1;
    }

    //修改用户权限
    public boolean uplevel(String uid) {
        String sql = "update users set u_isProd=1 where u_id=?";
        int ok;
        try {
            ok = jdbcTemplate.update(sql, uid);
        } catch (Exception ex) {
            LogUtil.error(ex.toString());
            ok = 0;
        }
        return ok == 1;
    }

    //成为VIP
    public boolean toVIP(String uid) {
        String sql = "update users set u_vip=1 where u_id=?";
        int ok;
        try {
            ok = jdbcTemplate.update(sql, uid);
        } catch (Exception ex) {
            LogUtil.error(ex.toString());
            ok = 0;
        }
        return ok == 1;
    }

    //金额变动
    public boolean payIn(float u_cost,String uid){
        String sql = "update users set u_bag=? where u_id=?";
        int ok;
        try {
            ok = jdbcTemplate.update(sql, u_cost,uid);
        } catch (Exception ex) {
            LogUtil.error(ex.toString());
            ok = 0;
        }
        return ok == 1;
    }

}
