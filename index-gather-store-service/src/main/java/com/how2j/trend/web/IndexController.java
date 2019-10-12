package com.how2j.trend.web;


import com.how2j.trend.pojo.Index;
import com.how2j.trend.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: tyk
 * @Date: 2019/10/12 10:53
 * @Description:
 */
@RestController
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/getCodes")
    public List<Index> getCodes() throws Exception{
        List<Index> indexes = indexService.fetchDataFromThirdPart();
        return indexes;
    }
}
