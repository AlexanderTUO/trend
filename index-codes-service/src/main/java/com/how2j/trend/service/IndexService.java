package com.how2j.trend.service;

import cn.hutool.core.collection.CollectionUtil;
import com.how2j.trend.pojo.Index;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "indexes")
public class IndexService {
    @Cacheable(key = "'all_codes'")
    public List<Index> get() {
        Index index = new Index();
        index.setName("无效的指数代码");
        index.setCode("000000");
        return CollectionUtil.toList(index);
    }
}
