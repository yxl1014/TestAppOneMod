package yxl.DataHandle.fileio.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import yxl.UserAndTask.util.LogUtil;

import java.io.*;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileUtil {

    @Value("${local.srcUrl}")
    private String dirUrl;

    @Value("${download.srcUrl}")
    private String downUrl;

    @Async("fileExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void writeFile(String filename, String data) {
        File directory = new File(dirUrl);
        if (!directory.exists()) {
            directory.mkdir();
        }
        FileWriter fw = null;
        try {
            File file = new File(dirUrl + filename);
            if (!file.exists())
                file.createNewFile();
            fw = new FileWriter(file, true);
            //LogUtil.info("写入文件的数据量是--->" + data.length());
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

    public Map<String, Integer> readMrFile(String filename) {
        Map<String, Integer> datas = new HashMap<>();
        File directory = new File(downUrl);
        if (!directory.exists()) {
            directory.mkdir();
        }

        BufferedReader br = null;
        FileReader fr = null;
        try {
            File file = new File(downUrl + filename);
            if (!file.exists()) {
                LogUtil.warn(filename + "is not exists");
                return null;
            }
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String buff;
            while ((buff = br.readLine()) != null) {
                String[] tb = buff.split("\t");
                datas.put(tb[0], Integer.valueOf(tb[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return datas;
    }

    public boolean rmFile(String filename) {
        File file = new File(dirUrl + filename);
        if (!file.exists())
            return true;
        return file.delete();
    }
}
