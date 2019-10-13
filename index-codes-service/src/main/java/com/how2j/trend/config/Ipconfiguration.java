package com.how2j.trend.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Ipconfiguration implements ApplicationListener<WebServerInitializedEvent> {
    private int serverPort = 0;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        this.serverPort = webServerInitializedEvent.getSource().getPort();
    }

    public int getPort() {
        return this.serverPort;
    }
}
