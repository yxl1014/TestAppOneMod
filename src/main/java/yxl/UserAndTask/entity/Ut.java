package yxl.UserAndTask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ut {
    private String ut_id;
    private String ut_uid;
    private String ut_tid;
    private Timestamp ut_time;
    private String ut_state="暂停";
    private int ut_allresult=0;
}
