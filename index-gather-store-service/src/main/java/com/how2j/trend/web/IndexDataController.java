package com.how2j.trend.web;

import com.how2j.trend.pojo.IndexData;
import com.how2j.trend.service.IndexDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: tyk
 * @Date: 2019/10/12 17:39
 * @Description:
 */
@RestController
public class IndexDataController {
    @Autowired
    private IndexDataService indexDataService;

    @GetMapping("/getIndexData/{code}")
    public List<IndexData> getIndexData(@PathVariable String code) {
        return indexDataService.get(code);
    }

    @GetMapping("/freshIndexData/{code}")
    public List<IndexData> freshIndexData(@PathVariable String code) {
        return indexDataService.fresh(code);
    }

    @GetMapping("/removeIndexData/{code}")
    public String removeIndexData(@PathVariable String code) {
        indexDataService.remove(code);
        return "remove indexData successfully";
    }

}
