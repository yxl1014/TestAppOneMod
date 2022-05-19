package yxl.UserAndTask.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestDetails {
    private String uid;//测试者id
    private Timestamp gettime;//接受任务时间
    private Integer cost;//测试次数
    private String statu;//当前任务状态
    private Long nums;//发送请求次数
    private Double success;//成功率
}
