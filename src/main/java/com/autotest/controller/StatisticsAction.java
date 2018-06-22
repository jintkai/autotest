package com.autotest.controller;

import com.autotest.model.BaseResp;
import com.autotest.service.CaseService;
import com.autotest.service.SuitCaseService;
import com.autotest.service.SuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsAction {

    @Autowired
    private SuitCaseService suitCaseService;
    @Autowired
    private SuitService suitService;

    @RequestMapping("/")
    public BaseResp getStatistics(){
        long now = System.currentTimeMillis() / 1000l;
        long daySecond = 60 * 60 * 24;
        long dayTime = now - (now + 8 * 3600) % daySecond;
        BaseResp baseResp = new BaseResp();
        Map<String, Object> result = new HashMap<>();
        result.put("newCase",suitCaseService.countCaseByTime(dayTime));
        result.put("totalCase",suitCaseService.countCaseByTime(0l));
        result.put("newSuit",suitService.countSuitByCreateTime(dayTime));
        result.put("totalSuit",suitService.countSuitByCreateTime(0l));
        result.put("newTest",10000);
        result.put("totalTest",200000);
        baseResp.setData(result);
        return baseResp;
    }
}
