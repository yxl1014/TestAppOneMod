package yxl.DataToMysql.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yxl.DataToMysql.read.SelectUt_working;
import yxl.DataToMysql.write.UpdateUt_working;
import yxl.UserAndTask.entity.Ut_working;

import java.util.List;

@Service
public class UtwUtil {
    @Autowired
    private SelectUt_working select;
    @Autowired
    private UpdateUt_working update;

    public boolean insertut(Ut_working utw) {
        return update.insertut(utw);
    }

    public boolean updateState(String utid, int result) {
        return update.updateState(utid, result);
    }

    public boolean containsUt(String utid) {
        return select.containsUt(utid);
    }

    public Ut_working findutbyId(String utid) {
        return select.findutbyId(utid);
    }

    public List<Ut_working> findTasks() {
        return select.findTasks();
    }

    public List<Ut_working> findNookTasks() {
        return select.findNookTasks();
    }
}
