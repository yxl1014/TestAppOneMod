package yxl.DataToMysql.read;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import yxl.UserAndTask.entity.Ut;

import java.util.List;

@Repository
public class SelectU_t {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    //查询是否存在
    public boolean containsUt(String utid) {
        String sql = "select count(1) from u_ts where ut_id=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, utid) == 1;
    }

    //查询Ut
    public Ut findutbyId(String utid) {
        String sql = "select * from u_ts where ut_id=?";
        return jdbcTemplate.queryForObject(sql, Ut.class, utid);
    }

    //查询所有任务
    public List<Ut> findTasks(){
        String sql="select * from u_ts";
        return jdbcTemplate.queryForList(sql, Ut.class);
    }
}
