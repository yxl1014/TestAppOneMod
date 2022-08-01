package yxl.utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yxl.utils.IpUtil;
import yxl.utils.PosUtil;
import yxl.utils.UnicodeUtil;

/**
 * @Author: yxl
 * @Date: 2022/7/26 15:43
 */

@Configuration
public class UtilsConfig {

    @Bean
    public IpUtil getIpUtil() {
        return new IpUtil();
    }

    @Bean
    public PosUtil getPosUtil() {
        return new PosUtil();
    }

    @Bean
    public UnicodeUtil getUnicodeUtil() {
        return new UnicodeUtil();
    }
}
