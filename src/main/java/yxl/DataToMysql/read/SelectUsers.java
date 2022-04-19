package yxl.DataToMysql.read;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import yxl.UserAndTask.entity.User;

@Repository
public class SelectUsers {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //登录用的查询
    //返回一个User类，传给前端决定显示哪些内容
    public User findbyTelAndPassword(String tel, String password) {
        String sql = "select u_id,u_tel,u_email,u_name,u_password,u_bag,u_vip,u_isProd from users where u_tel=? and u_password=?";
        //return jdbcTemplate.queryForObject(sql, User.class, tel, password);
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class), tel, password);
    }

    //判断电话是否存在
    //查重||判断用户是否存在
    public Boolean findUserbyTel(String tel) {
        String sql = "select count(1) from users where u_tel=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, tel) == 1;
    }
}
