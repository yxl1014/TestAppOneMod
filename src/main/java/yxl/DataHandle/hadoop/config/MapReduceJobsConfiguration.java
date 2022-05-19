package yxl.DataHandle.hadoop.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import yxl.DataHandle.hadoop.mapreduce.FileMapper;
import yxl.DataHandle.hadoop.mapreduce.FileReduce;
import yxl.MainApplication;

@Component
public class MapReduceJobsConfiguration implements Tool {

    @Value("${hadoop.name-node}")
    private String hdfsUrl;

    public Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", hdfsUrl);
        configuration.set("mapred.job.tracker", hdfsUrl);
        return configuration;
    }

    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = getConfiguration();
        Job job = Job.getInstance(conf, strings[0]);

        job.setMapperClass(FileMapper.class);
        job.setCombinerClass(FileReduce.class);
        job.setJarByClass(MainApplication.class);
        job.setReducerClass(FileReduce.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        FileInputFormat.addInputPath(job, new Path(strings[1]));
        FileOutputFormat.setOutputPath(job, new Path(strings[2]));
        return job.waitForCompletion(true) ? 0 : 1;
    }

    @Override
    public void setConf(Configuration configuration) {

    }

    @Override
    public Configuration getConf() {
        return null;
    }
}
