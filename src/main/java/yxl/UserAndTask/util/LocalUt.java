package yxl.UserAndTask.util;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import yxl.UserAndTask.entity.Task;
import yxl.UserAndTask.entity.Ut;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LocalUt {

    private final ConcurrentHashMap<String, Ut> uts=new ConcurrentHashMap<>();

    public Ut addLocal(@NonNull Ut ut){
        return uts.put(ut.getUt_id(),ut);
    }

    public Ut getLocal(@NonNull String utid){
        if(!uts.containsKey(utid))
            return null;
        return uts.get(utid);
    }

    public void addLocal(@NonNull List<Ut> ut){
        for (Ut t:ut){
            uts.put(t.getUt_id(),t);
        }
    }
}
