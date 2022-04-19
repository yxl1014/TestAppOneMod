package yxl.DataHandle.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yxl.DataToMysql.util.UtwUtil;
import yxl.UserAndTask.entity.T_result;
import yxl.UserAndTask.util.LogUtil;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class DirData {
    //private final Executor poolExecutor= Executors.newScheduledThreadPool(100);
    //private final Executor poolExecutor = Executors.newFixedThreadPool(100);
    //private final Executor poolExecutor = Executors.newCachedThreadPool();
    //private final Executor oneThread = Executors.newSingleThreadExecutor();

    //private final static ConcurrentHashMap<Integer, Integer> oks = new ConcurrentHashMap<>();

    @Autowired
    private UtwUtil utwUtil;

    public void doData(T_result tr) {
        if (!tr.isTr_isSuccess()) //判断请求是否成功
            LogUtil.error("请求未成功！");
        utwUtil.updateResultbyUtwid(tr.getTr_utwid(), 1);
        utwUtil.inserttr(tr);
    }

    public void pushData() {
    }
}
