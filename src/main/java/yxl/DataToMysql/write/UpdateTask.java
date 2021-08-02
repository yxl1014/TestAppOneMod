package yxl.DataToMysql.write;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import yxl.UserAndTask.entity.Task;
import yxl.UserAndTask.util.LogUtil;


@Repository
public class UpdateTask {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //新建任务
    public boolean insertTask(Task t){
        String sql="insert into tasks(t_id,t_name,t_type,t_uid,t_serverip,t_serverport,t_cost,t_protocol,t_context,t_target,t_colonymsg,t_state,t_stime) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int ok;
        try {
            ok=jdbcTemplate.update(sql,t.getT_id(),t, t.getT_name(),t.getT_type(),t.getT_uid(),t.getT_serverip(),t.getT_serverport(),
                    t.getT_cost(),t.getT_protocol(),t.getT_context(),t.getT_target(),t.getT_colonymsg(),t.getT_state(),t.getT_stime());
        }catch (Exception ex){
            LogUtil.error(ex.toString());
            ok=0;
        }
        return ok==1;
    }

    //修改任务状态
    public boolean updateTask(String tid,String state){
        String sql="update tasks set t_state=? where t_id=?";
        int ok;
        try{
            ok=jdbcTemplate.update(sql,state,tid);
        }catch (Exception ex){
            LogUtil.error(ex.toString());
            ok=0;
        }
        return ok==1;
    }
}
