package yxl.DataToMysql.write;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import yxl.UserAndTask.entity.Ut_working;
import yxl.UserAndTask.util.LogUtil;


import java.sql.Timestamp;
import java.util.Date;

@Repository
public class UpdateUt_working {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //用户接受任务
    public boolean insertut(Ut_working utw){
        String sql="insert ut_working(utw_utid,utw_stime) values(?,?)";
        int ok;
        try {
            ok=jdbcTemplate.update(sql,utw.getUtw_utid(),utw.getUtw_stime());
        }catch (Exception ex){
            LogUtil.error(ex.toString());
            ok=0;
        }
        return ok==1;
    }

    //修改状态
    public boolean updateState(String utid,int result){
        String sql="update ut_working set utw_result=?,utw_etime=? where utw_utid=?";
        int ok;
        try {
            ok=jdbcTemplate.update(sql,result,new Timestamp(new Date().getTime()),utid);
        }catch (Exception ex){
            LogUtil.error(ex.toString());
            ok=0;
        }
        return ok==1;
    }
}
