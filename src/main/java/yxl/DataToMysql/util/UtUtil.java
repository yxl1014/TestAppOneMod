package yxl.DataToMysql.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yxl.DataToMysql.read.SelectU_t;
import yxl.DataToMysql.write.UpdateU_t;
import yxl.UserAndTask.entity.Ut;


import java.util.List;

@Service
public class UtUtil {
    @Autowired
    private UpdateU_t update;
    @Autowired
    private SelectU_t select;

    public boolean containsUt(String utid) {
        return select.containsUt(utid);
    }

    public Ut findutbyId(String utid) {
        return select.findutbyId(utid);
    }

    public List<Ut> findTasks() {
        return select.findTasks();
    }

    public boolean insertut(Ut ut) {
        return update.insertut(ut);
    }

    public boolean updateState(String state, String utid) {
        return update.updateState(state, utid);
    }

    public boolean updateResult(int result, String utid) {
        return update.updateResult(result, utid);
    }

    public List<Ut> findutbyTid(String tid) {
        return select.findutbyTid(tid);
    }

    public Ut findutbyTidAndUid(String tid, String u_id) {
        return select.findutbyTidAndUid(tid,u_id);
    }

    public List<Ut> findutbyUid(String uid) {
        return select.findutbyUid(uid);
    }
}
