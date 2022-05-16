package yxl.DataHandle.fileio.util;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import yxl.UserAndTask.util.LogUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class FileUtil {

    private final String dirUrl = "/home/yxl/IdeaProjects/TestAppOneMod/hadoopFile/";

    @Async("fileExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void writeFile(String filename, String data) {
        LogUtil.info(data + "is in file");
        File directory = new File(dirUrl);
        if (!directory.exists()) {
            directory.mkdir();
        }
        FileWriter fw = null;
        try {
            File file = new File(dirUrl + filename);
            if (!file.exists())
                file.createNewFile();
            fw = new FileWriter(file,true);
            LogUtil.info("每次写入文件的数据量是---------------------------------" + data.length());
            fw.write(data);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
