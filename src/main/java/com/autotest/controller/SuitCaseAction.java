package com.autotest.controller;

import com.autotest.model.Suit;
import com.autotest.model.SuitCase;
import com.autotest.service.SuitCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/suitcase")
public class SuitCaseAction {

    @Autowired
    SuitCaseService suitCaseService;


    @RequestMapping("/{id}")
    public Map<String,Object> getSuitCases(@PathVariable( name = "id") Integer id){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        SuitCase o = suitCaseService.selectSuitCaseById(id);
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



    @RequestMapping(value = "/run/" ,method = RequestMethod.POST)
    public Map<String,Object> runSuitCase( Integer id,Integer buildid){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        SuitCase o = suitCaseService.selectSuitCaseById(id);
        resultMap.put("result",suitCaseService.suitCaseRun(o,buildid));
        return resultMap;
    }

    @RequestMapping(value = "/runsuit/" ,method = RequestMethod.POST)
    public Map<String,Object> runSuit( Integer id,Integer buildid){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<SuitCase> lists = suitCaseService.selectBySuitID(id);
        for (SuitCase suitCase : lists) {
            runSuitCase(suitCase.getId(),buildid);
        }
        resultMap.put("result",lists);
        return resultMap;
    }


    @RequestMapping("/delete")
    int deleteSuitCaseById(Integer id){
        return suitCaseService.deleteSuitCaseById(id);
    }

    @RequestMapping("/insert")
    int insertSuitCase(SuitCase record){
        if (record != null){
            return suitCaseService.insertSuitCase(record);
        }
        return 0;
    }


    @RequestMapping("/selectBySuitIdCaseId")
    List<SuitCase> selectBySuitIdCaseId(Integer suitid,Integer caseid){
        if (suitid == null && caseid == null){
            return null;
        }
        return suitCaseService.selectBySuitIdCaseId(suitid,caseid);
    }

    @RequestMapping("/update")
    int updateSuitCase(SuitCase record){
        return suitCaseService.updateSuitCase(record);
    }
}
