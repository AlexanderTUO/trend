package com.how2j.trend.client;

import com.how2j.trend.pojo.IndexData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: tyk
 * @Date: 2019/10/14 14:48
 * @Description:
 */
@FeignClient(value = "index-data-service",fallback = IndexDataClientFeignHystrix.class)
public interface IndexDataClient {
    @GetMapping("/data/{code}")
    List<IndexData> listIndexData(@PathVariable("code") String code);
}