package yxl.DataHandle.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yxl.DataHandle.fileio.Service.FileService;
import yxl.DataHandle.fileio.util.FileUtil;
import yxl.DataHandle.hadoop.HadoopTemplate;
import yxl.DataToMysql.util.UtwUtil;
import yxl.UserAndTask.entity.T_result;
import yxl.UserAndTask.entity.Ut_working;
import yxl.UserAndTask.util.LogUtil;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.File;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;


@Service
public class DirData {
    @Autowired
    private UtwUtil utwUtil;

    @Autowired
    private FileService fileService;

    @Autowired
    private HadoopTemplate hadoopTemplate;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private Set<String> filesSet;

    @Value("${local.srcUrl}")
    private String srcUrl;

    @Value("${hadoop.namespace}")
    private String destUrl;


    public void doData(T_result tr) {
        if (!tr.isTr_isSuccess()) //判断请求是否成功
            LogUtil.error("请求未成功！");
        utwUtil.updateResultbyUtwid(tr.getTr_utwid(), 1);
        fileService.insertResult(tr);
    }


    @Async("filePushExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void pushData(Ut_working utw) {
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String fname = utw.getUtw_utid() + "_" + utw.getUtw_id() + "_result";
        if (filesSet.contains(fname)) {

            hadoopTemplate.copyFileToHDFS(false, true, srcUrl + fname, destUrl + fname);
            if (!fileUtil.rmFile(fname))
                LogUtil.error(fname + "is delete fail!");
            filesSet.remove(fname);
        } else
            LogUtil.warn(fname + "in Set is not exist");
    }
}
