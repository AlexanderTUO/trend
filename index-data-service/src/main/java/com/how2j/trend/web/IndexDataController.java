package com.how2j.trend.web;

import com.how2j.trend.config.Ipconfiguration;
import com.how2j.trend.pojo.IndexData;
import com.how2j.trend.service.IndexDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexDataController {
    @Autowired
    private IndexDataService indexDataService;
    @Autowired
    private Ipconfiguration ipconfiguration;

    @GetMapping("/data/{code}")
    public List<IndexData> get(@PathVariable String code) {
        System.out.println("Current instance port is :"+ipconfiguration.getPort());
        return indexDataService.get(code);
    }
}
