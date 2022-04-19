package yxl.UserAndTask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ut_working{
    private int utw_id;
    private String utw_utid;
    private Timestamp utw_stime;
    private Timestamp utw_etime;
    private int utw_result;

    public Ut_working(String utw_utid,Timestamp utw_stime){
        this.utw_utid=utw_utid;
        this.utw_stime=utw_stime;
        this.utw_etime=null;
        this.utw_result=0;
    }
}
