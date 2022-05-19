package yxl.DataHandle.hadoop.mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FileMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, LongWritable>.Context context) throws IOException, InterruptedException {
        String[] rows = value.toString().split(",");
        for (String r : rows) {
            String[] collctions = r.split(" ");
            for (String c : collctions) {
                context.write(new Text(c), new LongWritable(1));
            }
        }
    }
}
