package com.how2j.trend;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.awt.*;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author: tyk
 * @Date: 2019/10/14 14:00
 * @Description:
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class TrendTradingBackTestApplication {
    public static void main(String[] args){
        int port = 0;
        int defaultPort = 8051;
        int eurekaPort = 8761;

        if (NetUtil.isUsableLocalPort(eurekaPort)) {
            System.err.printf("端口%d未启动，判断eureka服务未启用，无法启动本服务，故退出%n", eurekaPort);
            System.exit(1);
        }

        if (null != args && args.length != 0) {
            for (String arg : args) {
                if (arg.startsWith("port=")) {
                    String strPort = StrUtil.subAfter(arg, "port=", true);
                    if (NumberUtil.isNumber(strPort)) {
                        port = Convert.toInt(strPort);
                    }
                }
            }
        }

        if (0 == port) {
            Future<Integer> future = ThreadUtil.execAsync(() ->{
                int p = 0;
                System.out.printf("请在5秒内输入端口，推荐端口%d,不输入则使用默认端口%d%n",defaultPort,defaultPort);
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String strPort = scanner.nextLine();
                    if (!NumberUtil.isNumber(strPort)) {
                        System.out.println("只能是数字！！！");
                        continue;
                    } else {
                        p = Convert.toInt(strPort);
                        scanner.close();
                        break;
                    }
                }
                return p;
            });

            try {
                port = future.get(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                port = defaultPort;
            } catch (ExecutionException e) {
                e.printStackTrace();
                port = defaultPort;
            } catch (TimeoutException e) {
                e.printStackTrace();
                port = defaultPort;
            }
        }

        if (!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用，无法启动本服务%n", port);
            System.exit(1);
        }

        new SpringApplicationBuilder(TrendTradingBackTestApplication.class)
                .properties("server.port=" + port)
                .run(args);
    }
}
