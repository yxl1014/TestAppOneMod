package yxl.DataHandle.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import yxl.DataHandle.fileio.Service.FileService;
import yxl.DataHandle.hadoop.HadoopTemplate;
import yxl.DataToMysql.util.UtwUtil;
import yxl.UserAndTask.entity.T_result;
import yxl.UserAndTask.util.LogUtil;

import javax.annotation.PreDestroy;
import java.io.File;


@Service
public class DirData {
    @Autowired
    private UtwUtil utwUtil;

    @Autowired
    private FileService fileService;

   /* @Autowired
    private HadoopTemplate hadoopTemplate;*/


    public void doData(T_result tr) {
        if (!tr.isTr_isSuccess()) //判断请求是否成功
            LogUtil.error("请求未成功！");
        utwUtil.updateResultbyUtwid(tr.getTr_utwid(), 1);
        fileService.insertResult(tr);
    }

    @PreDestroy
    public void pushData() {
        LogUtil.info("Spring boot 程序结束执行 将所有数据上传到hadoop");
    }
}
