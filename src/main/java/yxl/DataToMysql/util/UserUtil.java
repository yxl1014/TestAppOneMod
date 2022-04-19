package yxl.DataToMysql.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import yxl.DataToMysql.read.SelectUsers;
import yxl.DataToMysql.write.UpdateUser;
import yxl.UserAndTask.entity.Producer;
import yxl.UserAndTask.entity.User;


@Service
public class UserUtil {
    @Autowired
    private SelectUsers select;

    @Autowired
    private UpdateUser update;

    public User findbyTelAndPassword(String tel, String password){
        return select.findbyTelAndPassword(tel,password);
    }

    public Boolean findUserbyTel(String tel){
        return select.findUserbyTel(tel);
    }

    public boolean insertUser(User user){
        return update.insertUser(user);
    }

    public boolean insertUserNoemail(User user){
        return update.insertUserNoemail(user);
    }

    public boolean updateUser(User user){
        return update.updateUser(user);
    }

    public boolean insertProducer(Producer pro){
        return update.insertProducer(pro);
    }

    public boolean uplevel(String uid){
        return update.uplevel(uid);
    }

    public boolean toVIP(String uid){
        return update.toVIP(uid);
    }

    public boolean payIn(float u_cost,String uid){
        return update.payIn(u_cost,uid);
    }
}
