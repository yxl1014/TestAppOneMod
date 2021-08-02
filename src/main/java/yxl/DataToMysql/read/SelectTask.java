package yxl.DataToMysql.read;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import yxl.UserAndTask.entity.Task;


import java.util.List;

@Repository
public class SelectTask {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //查询是否存在
    public boolean containsTid(String tid) {
        String sql = "select count(1) from tasks where t_id=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, tid) == 1;
    }

    //查询任务
    public Task findTaskbyId(String tid) {
        String sql = "select * from tasks where t_id=?";
        return jdbcTemplate.queryForObject(sql, Task.class, tid);
    }

    //查询所有任务
    public List<Task> findTasks(){
        String sql="select * from tasks";
        return jdbcTemplate.queryForList(sql,Task.class);
    }
}
