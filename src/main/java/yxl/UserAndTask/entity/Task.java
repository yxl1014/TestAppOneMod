package yxl.UserAndTask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task{
    private String t_id;
    private String t_name;
    private String t_type;
    private String t_uid;
    private String t_serverip;
    private String t_serverport;
    private float t_cost;
    private String t_protocol;
    private String t_context;
    private String t_target;
    private String t_colonymsg;
    private String t_state="准备中";
    private Timestamp t_stime;
}
