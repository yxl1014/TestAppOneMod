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
}
