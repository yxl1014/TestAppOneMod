package yxl.DataToMysql.read;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import yxl.UserAndTask.entity.Ut_working;


import java.util.ArrayList;
import java.util.List;

@Repository
public class SelectUt_working {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //查询是否存在
    public boolean containsUtw(String id) {
        String sql = "select count(1) from ut_working where utw_id=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) == 1;
    }

    //查询Ut_workig
    public Ut_working findutwbyId(int id) {
        String sql = "select * from ut_working where utw_id=?";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Ut_working.class), id);
    }

    //查询所有任务
    public List<Ut_working> findTasks() {
        //String sql = "select utw_id,utw_utid,utw_stime,utw_etime,utw_result from ut_working";
        String sql = "select utw_id from ut_working";
        List<Integer> listint=jdbcTemplate.queryForList(sql, Integer.class);

        List<Ut_working> list=new ArrayList<>();
        for (Integer utwid:listint){
            list.add(findutwbyId(utwid));
        }
        return list;
    }

    //查询所有没有完成的任务
    public List<Ut_working> findNookTasks(String utid) {
        //String sql = "select utw_id,utw_utid,utw_stime,utw_etime,utw_result from ut_working where utw_etime is null and utw_utid=?";
        String sql = "select utw_id from ut_working where utw_etime is null and utw_utid=?";
        List<Integer> listint=jdbcTemplate.queryForList(sql, Integer.class);

        List<Ut_working> list=new ArrayList<>();
        for (Integer utwid:listint){
            list.add(findutwbyId(utwid));
        }
        return list;
    }
}
