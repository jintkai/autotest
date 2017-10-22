package com.autotest.controller;

import com.autotest.model.SuitCaseResultHis;
import com.autotest.service.SuitCaseResultHisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ying.zhang on 17/10/15.
 */
@RestController
@RequestMapping("/suit/resultHis")
public class SuitCaseResultHisAction {

    @Autowired
    SuitCaseResultHisService suitCaseResultHisService;

    @RequestMapping("/delete")
    int deleteSuitHisById(Integer id){
        return suitCaseResultHisService.deleteSuitHisById(id);
    }

    @RequestMapping("/insert")
    int insertSuitHis(SuitCaseResultHis record){
        if(record != null){
            return suitCaseResultHisService.insertSuitHis(record);
        }
        return 0;
    }

    @RequestMapping("/selectById")
    SuitCaseResultHis selectSuitHisById(Integer id){
        return suitCaseResultHisService.selectSuitHisById(id);
    }

    @RequestMapping("/update")
    int updateSuitHisById(SuitCaseResultHis record){
        if (record != null){
            return suitCaseResultHisService.updateSuitHis(record);
        }
        return 0;
    }

    @RequestMapping("/selectByIds")
    List<SuitCaseResultHis> selectBySuitIdbuildIdCaseId(Integer suitcaseid, Integer suitid, Integer buildid){
        if ( suitcaseid ==null && suitid == null && buildid == null){
            return null;
        }
        return suitCaseResultHisService.selectBySuitIdbuildIdCaseId(suitcaseid,suitid,buildid);
    }



}
