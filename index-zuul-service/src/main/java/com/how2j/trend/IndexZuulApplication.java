package com.how2j.trend;

import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author: tyk
 * @Date: 2019/10/14 09:56
 * @Description:
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
public class IndexZuulApplication {
    public static void main(String[] args){
        int port = 8031;

        if (!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用，无法启动本服务，故退出%n", port);
            System.exit(1);
        }

        new SpringApplicationBuilder(IndexZuulApplication.class).properties("server.port=" + port)
                .run(args);
    }
}
