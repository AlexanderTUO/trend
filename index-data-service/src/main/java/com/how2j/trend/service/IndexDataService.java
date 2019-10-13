package com.how2j.trend.service;

import cn.hutool.core.collection.CollectionUtil;
import com.how2j.trend.pojo.IndexData;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "indexData")
public class IndexDataService {
    @Cacheable(key = "'indexData-code-'+#p0")
    public List<IndexData> get(String code) {
        return CollectionUtil.toList();
    }
}
