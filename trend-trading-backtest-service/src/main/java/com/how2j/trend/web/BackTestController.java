package com.how2j.trend.web;

import com.how2j.trend.pojo.IndexData;
import com.how2j.trend.service.BackTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: tyk
 * @Date: 2019/10/14 15:02
 * @Description:
 */
@RestController
public class BackTestController {
    @Autowired
    private BackTestService backTestService;

    @GetMapping("/simulate/{code}")
    @CrossOrigin
    public Map getIndexData(@PathVariable String code) {
        List<IndexData> indexDatas = backTestService.getIndexData(code);
        Map<String, List> map = new HashMap<>();
        map.put("indexDatas", indexDatas);
        return map;
    }
}
