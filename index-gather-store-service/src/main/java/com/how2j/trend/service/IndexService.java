package com.how2j.trend.service;

import com.how2j.trend.pojo.Index;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: tyk
 * @Date: 2019/10/12 10:44
 * @Description:
 */
@Service
public class IndexService {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "thirdPartNotConnect")
    public List<Index> fetchDataFromThirdPart() {
        List<Map> temp = restTemplate.getForObject("http://127.0.0.1:8090/indexes/codes.json", List.class);
        return map2index(temp);
    }

    private List<Index> map2index(List<Map> temp) {
        List<Index> indexes = new ArrayList<>();
        for (Map map : temp) {
            String code = map.get("code").toString();
            String name = map.get("name").toString();
            Index index = new Index();
            index.setCode(code);
            index.setName(name);
            indexes.add(index);
        }
        return indexes;
    }

    private List<Index> thirdPartNotConnect() {
        System.out.println("第三方服务未连接");
        Index index = new Index();
        index.setCode("000000");
        index.setName("无效的指数代码");
        List<Index> indices = new ArrayList<>();
        indices.add(index);
        return indices;
    }
}
