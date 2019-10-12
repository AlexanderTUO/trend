package com.how2j.trend;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author: tyk
 * @Date: 2019/10/12 09:53
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
public class ThirdPartIndexDataApplication {
    public static void main(String[] args){
        int eurekaPort = 8761;
        int port = 8090;
        if (NetUtil.isUsableLocalPort(eurekaPort)) {
            System.err.printf("端口%d被占用，判断eureka服务未启动%n", eurekaPort);
            System.exit(1);
        }
        if (null != args && args.length != 0) {
            for (String arg : args) {
                if (arg.startsWith("port=")) {
                    String portStr = StrUtil.subAfter(arg, "port=", true);
                    if (NumberUtil.isNumber(portStr)) {
                        port = Convert.toInt(portStr);
                    }
                }
            }
        }

        if (!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用，无法启动%n", port);
            System.exit(1);
        }

        new SpringApplicationBuilder(ThirdPartIndexDataApplication.class)
                .properties("server.port=" + port)
                .run(args);
    }
}
