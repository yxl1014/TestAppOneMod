package yxl.DataHandle.hadoop.mapreduce.service;

public interface MapReduceService {
    void fileCount(String jobName, String inputPath, String outputPath) throws Exception;
}
