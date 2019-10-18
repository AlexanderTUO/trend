package com.how2j.trend.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.how2j.trend.pojo.IndexData;
import com.how2j.trend.service.BackTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Author: tyk
 * @Date: 2019/10/14 15:02
 * @Description:
 */
@RestController
public class BackTestController {
    @Autowired
    private BackTestService backTestService;

    @GetMapping("/simulate/{code}/{startDate}/{endDate}")
    @CrossOrigin
    public Map getIndexData(@PathVariable("code") String code,
                            @PathVariable("startDate") String startDate,
                            @PathVariable("endDate") String endDate) throws Exception{
        List<IndexData> allIndexDatas = backTestService.getIndexData(code);
        List<IndexData> indexDatas = filterByFlushDate(startDate, endDate, allIndexDatas);
        String indexStartDate = indexDatas.get(0).getDate();
        String indexEndDate = indexDatas.get(indexDatas.size() - 1).getDate();

        Map<String, Object> map = new HashMap<>();
        map.put("indexDatas", indexDatas);
        map.put("indexStartDate", indexStartDate);
        map.put("indexEndDate", indexEndDate);
        return map;
    }

    private List<IndexData> filterByFlushDate(String startDateStr, String endDateStr, List<IndexData> indexDatas) {
        if (StrUtil.isBlankOrUndefined(startDateStr)
                || StrUtil.isBlankOrUndefined(endDateStr)) {
            return indexDatas;
        }
        List<IndexData> result = new ArrayList<>();
        Date startDate = DateUtil.parseDate(startDateStr);
        Date endDate = DateUtil.parseDate(endDateStr);

        for (IndexData indexData : indexDatas) {
            Date date = DateUtil.parseDate(indexData.getDate());
            if (date.after(startDate) && date.before(endDate)) {
                result.add(indexData);
            }
        }
        return result;
    }
}
