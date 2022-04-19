package yxl.DataHandle.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class HadoopTests {

    @Value("${hadoop.name-node}")
    private String nameNode;

    @Value("${hadoop.name-node}")
    private String filePath;

    @Test
    public void init() throws URISyntaxException, IOException {
        //指定刚才解压缩hadoop文件地址
        System.setProperty("hadoop.home.dir", "/opt2/hadoop-3.1.3/");
        //1.获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI(nameNode), configuration);
        //2.执行操作 创建hdfs文件夹
        Path path = new Path(filePath);
        if (!fs.exists(path)) {
            fs.mkdirs(path);
        }
        //关闭资源
        fs.close();
        System.out.println("结束！");
    }

    @Test
    public void upload() throws URISyntaxException, IOException {
        System.setProperty("hadoop.home.dir", "/opt2/hadoop-3.1.3/");
        //1.获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI(nameNode), configuration);
        //2.执行操作 上传文件
        fs.copyFromLocalFile(false,true, new Path("/opt/tar.gz/hadoop3/hadoop3.0安装手顺"), new Path(filePath));
        //关闭资源
        fs.close();
        System.out.println("结束！");
    }

    @Test
    public void download() throws URISyntaxException, IOException {
        System.setProperty("hadoop.home.dir", "/opt2/hadoop-3.1.3/");
        //1.获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI(nameNode), configuration);
        //2.执行操作 下载文件
        fs.copyToLocalFile(false, new Path(filePath+"/hadoop3.0安装手顺"), new Path("/data"), true);
        //关闭资源
        fs.close();
        System.out.println("结束！");
    }
}
