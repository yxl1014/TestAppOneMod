package yxl.utils;


import org.springframework.beans.factory.annotation.Autowired;
import yxl.UserAndTask.entity.Position.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @Author: yxl
 * @Date: 2022/7/26 15:41
 */

//        ————————————————
//        版权声明：本文为CSDN博主「Mario♔」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/semial/article/details/77944497

public class PosUtil {

    private static final String ak = "tqfF0vePvOowSxtSRPl9190aPIlHN0ca";

    @Autowired
    private UnicodeUtil unicodeUtil;

    public Position getPosByIp(String ip) {
        return getAddresses(ip, "utf-8");
    }

    /**
     * @param ip             请求的参数 ip
     * @param encodingString 服务器端请求编码。如GBK,UTF-8等
     * @return
     * @throws UnsupportedEncodingException
     */
    private Position getAddresses(String ip, String encodingString) {
        // 这里调用百度API
        String urlStr = "https://api.map.baidu.com/location/ip?ak=" + ak + "&ip=" + ip;
        // 从http://whois.pconline.com.cn取得IP所在的省市区信息
        String returnStr = getResult(urlStr, encodingString);
        if (returnStr != null) {
            // 处理返回的省市区信息
            String[] temp = returnStr.split(",");
            if (temp.length < 3) {
                return null;//无效IP，局域网测试
            }
            return GsonUtil.fromJson(unicodeUtil.unicodeDecode(returnStr), Position.class);
        }
        return null;
    }

    /**
     * @param urlStr   请求的地址
     * @param encoding 服务器端请求编码。如GBK,UTF-8等
     * @return
     */
    private String getResult(String urlStr, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();// 新建连接实例
            connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
            connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
            connection.setDoInput(true);// 是否打开输入流true|false
            connection.setRequestMethod("GET");// 提交方法POST|GET
            connection.setUseCaches(false);// 是否缓存true|false
            connection.connect();// 打开连接端口


            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据

            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();// 关闭连接
            }
        }
        return null;
    }
}
