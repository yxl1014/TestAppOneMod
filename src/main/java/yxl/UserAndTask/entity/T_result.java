package yxl.UserAndTask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class T_result {
    private int tr_id;
    private int tr_utwid;
    private String tr_uip;
    private String tr_uid;
    private int tr_code;
    private boolean tr_isSuccess;
    private String tr_target;
    private String tr_value;
    private String tr_message;
    private Timestamp tr_reqtime;
    private Timestamp tr_resptime;
}
