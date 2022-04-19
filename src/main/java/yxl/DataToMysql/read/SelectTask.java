package yxl.DataToMysql.read;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import yxl.UserAndTask.entity.Task;


import java.util.ArrayList;
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
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Task.class), tid);
    }

    //查询所有任务
    public List<Task> findTasks(){
        String sql="select t_id from tasks";
        List<String> lint=jdbcTemplate.queryForList(sql, String.class);
        List<Task> list=new ArrayList<>();
        for (String utid:lint){
            list.add(findTaskbyId(utid));
        }
        return list;
    }

    //查询某位用户发布的所有任务
    public List<Task> findTasksbyUid(String uid){
        String sql="select t_id from tasks where t_uid=?";
        List<String> lint=jdbcTemplate.queryForList(sql, String.class,uid);
        List<Task> list=new ArrayList<>();
        for (String utid:lint){
            list.add(findTaskbyId(utid));
        }
        return list;
    }
}
