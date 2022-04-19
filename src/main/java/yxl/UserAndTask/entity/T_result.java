package yxl.UserAndTask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class T_result {
    //{"tr_id":"20211120081617Gi01Yg23",
    // "tr_utwid":0,
    // "tr_uip":"127.0.1.1",
    // "tr_uid":"20211119121400Yk48",
    // "tr_code":11,
    // "tr_isSuccess":true,
    // "tr_target":"yyyyyy",
    // "tr_value":"Hello World",
    // "tr_message":"Hello World",
    // "tr_reqtime":"Feb 10, 2022 9:40:29 PM",
    // "tr_resptime":"Feb 10, 2022 9:40:29 PM"}
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
