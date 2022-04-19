package yxl.UserAndTask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User{
    private String u_id;
    private String u_tel;
    private String u_email;
    private String u_name;
    private String u_password;
    private float u_bag=0;
    private boolean u_vip=false;
    private boolean u_isProd=false;
}
