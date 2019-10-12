package com.how2j.trend.service;

import cn.hutool.core.collection.CollectionUtil;
import com.how2j.trend.pojo.Index;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = "indexes")
public class IndexService {

    private List<Index> indexes;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    IndexService indexService;


    public List<Index> fetchDataFromThirdPart() {
        List<Map> temp = restTemplate.getForObject("http://127.0.0.1:8090/indexes/codes.json", List.class);
        return map2index(temp);
    }

    @CacheEvict(allEntries = true)
    public void remove() {

    }

    @Cacheable(key = "'all_codes'")
    public List<Index> get() {
        return CollectionUtil.toList();
    }


    @Cacheable(key = "'all_codes'")
    public List<Index> store() {
        System.out.println(this);
        return indexes;
    }

    @HystrixCommand(fallbackMethod = "thirdPartNotConnect")
    public List<Index> fresh() {
        indexService.remove();
        indexes = fetchDataFromThirdPart();
        indexService.store();
        return indexes;
    }

    private List<Index> map2index(List<Map> temp) {
        indexes = new ArrayList<>();
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

    public List<Index> thirdPartNotConnect() {
        System.out.println("第三方服务未连接");
        Index index = new Index();
        index.setCode("000000");
        index.setName("无效的指数代码");
        return CollectionUtil.toList(index);
    }
}
