package yxl.DataHandle.fileio.Service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yxl.DataHandle.fileio.util.FileUtil;
import yxl.DataToMysql.util.UtUtil;
import yxl.DataToMysql.util.UtwUtil;
import yxl.UserAndTask.entity.T_result;
import yxl.UserAndTask.entity.Ut;
import yxl.UserAndTask.entity.Ut_working;
import yxl.UserAndTask.util.LogUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private UtwUtil utwUtil;

    @Autowired
    private UtUtil utUtil;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private Set<String> filmsSet;

    @AllArgsConstructor
    private static class HadoopClass {
        public String tid;//被测试的任务id
        public String uid;//测试者id
        public String ip;//测试端ip
        public int code;//返回报文code
        public boolean isSuccess;//是否成功
        public String target;//目标报文
        public String value;//返回报文
        public long usingTime;//用了多久时间 秒

        @Override
        public String toString() {
            return "被测试的任务id " +
                    this.tid + "," +
                    "测试者id " +
                    this.uid + "," +
                    "测试端ip " +
                    this.ip + "," +
                    "返回报文code " +
                    this.code + "," +
                    "是否成功 " +
                    this.isSuccess + "," +
                    "目标报文 " +
                    this.target + "," +
                    "返回报文 " +
                    this.value + "," +
                    "使用的时间 " +
                    this.usingTime +
                    "\n";
        }
    }


    @Override
    public boolean insertResult(T_result tResult) {
        long t = getTestTime(tResult.getTr_resptime(), tResult.getTr_reqtime());
        Ut_working utw = utwUtil.findutwbyId(tResult.getTr_utwid());
        if (utw == null) {
            LogUtil.error("utw is null utwid is " + tResult.getTr_utwid());
            return false;
        }
        Ut ut = utUtil.findutbyId(utw.getUtw_utid());
        if (ut == null) {
            LogUtil.error("ut is null utid is " + utw.getUtw_utid());
            return false;
        }
        HadoopClass hcls = new HadoopClass(ut.getUt_tid(), tResult.getTr_uid(), tResult.getTr_uip(),
                tResult.getTr_code(), tResult.isTr_isSuccess(), tResult.getTr_target(), tResult.getTr_value(), t);
        //LogUtil.trace(hcls.toString());
        String filename = utw.getUtw_utid() + "_" + utw.getUtw_id() + "_result";
        filmsSet.add(filename);
        fileUtil.writeFile(filename, hcls.toString());
        return true;
    }

    public long getTestTime(Timestamp end, Timestamp start) {//返回毫秒级差距
        Date e = new java.sql.Date(end.getTime());
        Date s = new java.sql.Date(start.getTime());
        long date = e.getTime() - s.getTime();
        return date/* / 1000*/;
    }
}
