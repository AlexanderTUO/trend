package com.how2j.trend.web;

import com.how2j.trend.config.Ipconfiguration;
import com.how2j.trend.pojo.Index;
import com.how2j.trend.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private Ipconfiguration ipconfiguration;

    @GetMapping("/codes")
    private List<Index> get() {
        System.out.println("Current instance port is :"+ipconfiguration.getPort());
        return indexService.get();
    }

}
