package com.how2j.trend.client;

import cn.hutool.core.collection.CollectionUtil;
import com.how2j.trend.pojo.IndexData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: tyk
 * @Date: 2019/10/14 14:54
 * @Description:
 */
@Component
public class IndexDataClientFeignHystrix implements IndexDataClient {
    @Override
    public List<IndexData> listIndexData(String code) {
        IndexData indexData = new IndexData();
        indexData.setDate("0000-00-00");
        indexData.setClosePoint(0);
        return CollectionUtil.toList(indexData);
    }
}
