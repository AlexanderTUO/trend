package com.how2j.trend.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.how2j.trend.pojo.IndexData;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: tyk
 * @Date: 2019/10/12 17:05
 * @Description:
 */
@Service
@CacheConfig(cacheNames = "indexData")
public class IndexDataService {
    private Map<String, List<IndexData>> indexDatas = new HashMap<>();

    @Autowired
    IndexDataService indexDataService;
    @Autowired
    private RestTemplate restTemplate;

    @CacheEvict(key = "'indexData-code-'+#p0")
    public void remove(String code) {
    }

    @Cacheable(key = "'indexData-code-'+#p0")
    public List<IndexData> get(String code) {
        return CollectionUtil.toList();
    }

    @CachePut(key = "'indexData-code-'+#p0")
    public List<IndexData> store(String code) {
        return indexDatas.get(code);
    }

    @HystrixCommand(fallbackMethod = "thirdPartNotConnected")
    public List<IndexData> fresh(String code) {
        indexDataService.remove(code);
        List<IndexData> indexDataList = fetchIndexDataFromThirdPart(code);
        indexDatas.put(code, indexDataList);
        indexDataService.store(code);
        return indexDatas.get(code);
    }

    private List<IndexData> map2IndexData(List<Map> maps) {
        List<IndexData> indexDataList = new ArrayList<>();
        for (Map map : maps) {
            IndexData indexData = new IndexData();
            indexData.setDate(map.get("date").toString());
            indexData.setClosePoint(Convert.toFloat(map.get("closePoint")));
            indexDataList.add(indexData);
        }
        return indexDataList;
    }


    public List<IndexData> fetchIndexDataFromThirdPart(String code) {
        List<Map> temp = restTemplate.getForObject("http://127.0.0.1:8090/indexes/" + code + ".json", List.class);
        return map2IndexData(temp);
    }

    public List<IndexData> thirdPartNotConnected(String code) {
        IndexData indexData = new IndexData();
        indexData.setDate("n/a");
        indexData.setClosePoint(0.00f);
        return CollectionUtil.toList(indexData);
    }
}
