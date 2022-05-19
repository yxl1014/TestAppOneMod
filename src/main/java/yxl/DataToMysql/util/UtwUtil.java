package yxl.DataToMysql.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yxl.DataToMysql.read.SelectUt_working;
import yxl.DataToMysql.write.UpdateUt_working;
import yxl.UserAndTask.entity.T_result;
import yxl.UserAndTask.entity.Ut_working;

import java.util.List;
import java.util.Map;

@Service
public class UtwUtil {
    @Autowired
    private SelectUt_working select;
    @Autowired
    private UpdateUt_working update;

    public boolean insertut(Ut_working utw) {
        return update.insertut(utw);
    }

    public boolean inserttr(T_result t) {
        return update.inserttr(t);
    }

    public boolean updateState(int utwid, int result) {
        return update.updateState(utwid, result);
    }

    public boolean updateResultbyUtwid(int utwid, int result) {
        return update.updateResultbyUtwid(utwid, result);
    }

    @Transactional//回滚
    public void pushData(Map<Integer, Integer> maps) {

        for (Integer id : maps.keySet()) {
            update.updateResultbyUtwid(id, maps.get(id));
        }
    }

    public boolean containsUtw(String utid) {
        return select.containsUtw(utid);
    }

    public Ut_working findutwbyId(int id) {
        return select.findutwbyId(id);
    }

    public Ut_working findutwbyNew(Ut_working utw) {
        return select.findutwbyNew(utw);
    }

    public List<Ut_working> findTasks() {
        return select.findTasks();
    }

    public List<Ut_working> findNookTasks(String utid) {
        return select.findNookTasks(utid);
    }

    public List<Ut_working> findOkTasks(String utid) {
        return select.findOkTasks(utid);
    }
}
