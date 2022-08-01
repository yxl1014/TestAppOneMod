package yxl.DataToMysql.write;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import yxl.UserAndTask.entity.Ut;
import yxl.utils.LogUtil;

@Repository
public class UpdateU_t {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //用户接受任务
    public boolean insertut(Ut ut){
        String sql="insert u_ts(ut_id,ut_uid,ut_tid,ut_time,ut_state,ut_allresult) values(?,?,?,?,?,?)";
        int ok;
        try {
            ok=jdbcTemplate.update(sql,ut.getUt_id(),ut.getUt_uid(),ut.getUt_tid(),ut.getUt_time(),ut.getUt_state(),ut.getUt_allresult());
        }catch (Exception ex){
            LogUtil.error(ex.toString());
            ok=0;
        }
        return ok==1;
    }

    //修改完成数量
    public boolean updateResult(int result,String utid){
        String sql="update u_ts set ut_allresult=? where ut_id=?";
        int ok;
        try {
            ok=jdbcTemplate.update(sql,result,utid);
        }catch (Exception ex){
            LogUtil.error(ex.toString());
            ok=0;
        }
        return ok==1;
    }

    //修改状态
    public boolean updateState(String state,String utid){
        String sql="update u_ts set ut_state=? where ut_id=?";
        int ok;
        try {
            ok=jdbcTemplate.update(sql,state,utid);
        }catch (Exception ex){
            LogUtil.error(ex.toString());
            ok=0;
        }
        return ok==1;
    }
}