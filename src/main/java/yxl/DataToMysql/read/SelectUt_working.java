package yxl.DataToMysql.read;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import yxl.UserAndTask.entity.Ut_working;


import java.util.List;

@Repository
public class SelectUt_working {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //查询是否存在
    public boolean containsUt(String utid) {
        String sql = "select count(1) from ut_working where utw_utid=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, utid) == 1;
    }

    //查询Ut_workig
    public Ut_working findutbyId(String utid) {
        String sql = "select * from ut_working where utw_utid=?";
        return jdbcTemplate.queryForObject(sql, Ut_working.class, utid);
    }

    //查询所有任务
    public List<Ut_working> findTasks(){
        String sql="select * from ut_working";
        return jdbcTemplate.queryForList(sql, Ut_working.class);
    }
}
