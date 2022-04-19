package yxl.UserAndTask.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String code="200";
    private String message="ok";
    private String data;
    private String token;
    public Result(String data,String token){
        this.data=data;
        this.token=token;
    }
}
