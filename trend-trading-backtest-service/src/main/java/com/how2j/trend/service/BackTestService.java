package com.how2j.trend.service;

import com.how2j.trend.client.IndexDataClient;
import com.how2j.trend.pojo.IndexData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @Author: tyk
 * @Date: 2019/10/14 14:43
 * @Description:
 */
@Service
public class BackTestService {
    @Autowired
    private IndexDataClient indexDataClient;

    public List<IndexData> getIndexData(String code) {
        List<IndexData> result = indexDataClient.listIndexData(code);
        Collections.reverse(result);

        for (IndexData indexData : result) {
            System.out.println(indexData.getDate());
        }
        return result;
    }
}
