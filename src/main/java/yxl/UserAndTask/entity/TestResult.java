package yxl.UserAndTask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestResult {
    private String tid;//任务id
    private String tstate;//任务状态
    private Integer tnum;//参与测试人数
    private Long tcost;//共测试次数
    private Double success;//成功率
    private List<TestDetails> more;//详细信息
}
