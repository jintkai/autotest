package com.autotest.controller;

import com.autotest.model.SuitCase;
import com.autotest.service.SuitCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/suitcases")
public class SuitCaseActionAction {

    @Autowired
    SuitCaseService suitCaseService;


    @RequestMapping("/{id}")
    public Map<String,Object> getSuitCases(@PathVariable( name = "id") Integer id){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        SuitCase o = suitCaseService.selectById(id);
        resultMap.put("results",o);
        return resultMap;
    }

    @RequestMapping("/")
    public Map<String,Object> getSuitCasesBySuitID(Integer suitID){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<SuitCase> o = suitCaseService.selectBySuitID(suitID);
        resultMap.put("results",o);
        return resultMap;
    }

    @RequestMapping(value = "/run/{id}" ,method = RequestMethod.POST)
    public Map<String,Object> runSuitCase(@PathVariable Integer id){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        SuitCase o = suitCaseService.selectById(id);
        resultMap.put("result",suitCaseService.suitCaseRun(o));
        return resultMap;
    }
}
