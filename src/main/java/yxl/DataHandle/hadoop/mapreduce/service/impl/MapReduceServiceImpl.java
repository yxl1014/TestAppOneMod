package yxl.DataHandle.hadoop.mapreduce.service.impl;

import org.apache.hadoop.util.ToolRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import yxl.DataHandle.hadoop.config.MapReduceJobsConfiguration;
import yxl.DataHandle.hadoop.hdfs.HadoopTemplate;
import yxl.DataHandle.hadoop.mapreduce.service.MapReduceService;

@Service
public class MapReduceServiceImpl implements MapReduceService {

    @Autowired
    private HadoopTemplate hadoopTemplate;

    @Autowired
    private MapReduceJobsConfiguration mapReduceJobsConfiguration;

    @Value("${hadoop.name-node}")
    private String hadoopPath;

    @Value("${hadoop.namespace}")
    private String inUrl;

    @Value("${hadoop.out}")
    private String outUrl;

    @Override
    public void fileCount(String jobName, String inputPath, String outputPath) throws Exception {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return;
        }
        // 输出目录 = output/当前Job,如果输出路径存在则删除，保证每次都是最新的
        if (hadoopTemplate.existDir(hadoopPath + outUrl + outputPath, false)) {
            hadoopTemplate.delFile(hadoopPath + outUrl + outputPath);
        }
        ToolRunner.run(mapReduceJobsConfiguration, new String[]{jobName, hadoopPath + inUrl + inputPath, hadoopPath + outUrl + outputPath});
    }
}
