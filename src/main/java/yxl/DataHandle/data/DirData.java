package yxl.DataHandle.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yxl.DataToMysql.util.UtwUtil;
import yxl.UserAndTask.entity.T_result;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DirData {
    //private final Executor poolExecutor= Executors.newFixedThreadPool(100);
    private final Executor poolExecutor = Executors.newCachedThreadPool();
    private final Executor oneThread = Executors.newSingleThreadExecutor();

    private final AtomicInteger size = new AtomicInteger(0);
    private final ConcurrentHashMap<Integer, Integer> oks = new ConcurrentHashMap<>();

    @Autowired
    private UtwUtil utwUtil;

    public void doData(T_result tr) {
        if (size.get() == 100) {//若积累条数到达100，则将数据存入数据库
            pushData();
            size.set(0);//并将size清0
        }
        size.getAndAdd(1);//未达到则自增
        poolExecutor.execute(new Runnable() {//从线程池取线程执行操作
            @Override
            public void run() {
                if (tr.isTr_isSuccess()) {//判断请求是否成功
                    if (oks.containsKey(tr.getTr_utwid())) {//判断该任务是否存在
                        oks.put(tr.getTr_utwid(), oks.get(tr.getTr_utwid()) + 1);//存在则在原有的基础上+1
                    } else {
                        oks.put(tr.getTr_utwid(), 1);//否则设1
                    }
                }
                //具体内容提交数据处理接口
            }
        });
    }

    public void pushData() {
        HashMap<Integer,Integer> temp=new HashMap<>(oks);//新建一个缓存
        oks.clear();//清空实际数组
        oneThread.execute(new Runnable() {
            @Override
            public void run() {
                utwUtil.pushData(temp);//push数据
            }
        });
    }
}
