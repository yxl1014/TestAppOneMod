package yxl.DataToMysql.read;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import yxl.UserAndTask.entity.Ut;

import java.util.ArrayList;
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
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Ut.class), utid);
    }

    public List<Ut> findutbyTid(String tid) {
        String sql = "select ut_id from u_ts where ut_tid=?";
        List<String> lint=jdbcTemplate.queryForList(sql, String.class, tid);
        List<Ut> list=new ArrayList<>();
        for (String utid:lint){
            list.add(findutbyId(utid));
        }
        return list;
    }

    //查询所有任务
    public List<Ut> findTasks() {
        String sql = "select ut_id from u_ts";
        List<String> lint=jdbcTemplate.queryForList(sql, String.class);
        List<Ut> list=new ArrayList<>();
        for (String utid:lint){
            list.add(findutbyId(utid));
        }
        return list;
    }

    public Ut findutbyTidAndUid(String tid, String u_id) {
        String sql = "select * from u_ts where ut_tid=? and ut_uid=?";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Ut.class), tid, u_id);
    }

    public List<Ut> findutbyUid(String uid) {
        String sql = "select ut_id from u_ts where ut_uid=?";
        List<String> lint=jdbcTemplate.queryForList(sql, String.class, uid);
        List<Ut> list=new ArrayList<>();
        for (String utid:lint){
            list.add(findutbyId(utid));
        }
        return list;
    }
}
