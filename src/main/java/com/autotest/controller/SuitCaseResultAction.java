package com.autotest.controller;

import com.autotest.model.BaseResp;
import com.autotest.model.SuitCaseResult;
import com.autotest.service.SuitCaseResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ying.zhang on 17/10/15.
 */
@RestController
@RequestMapping("/suit/result")
public class SuitCaseResultAction {

    @Autowired
    SuitCaseResultService suitCaseResultService;

    @RequestMapping("/delete")
    int deleteSuitResultById(Integer id){
        return suitCaseResultService.deleteSuitResultById(id);
    }

    @RequestMapping("/insert")
    int insertSuitResult(SuitCaseResult record){
        if (record != null){
            return suitCaseResultService.insertSuitResult(record);
        }
        return 0;
    }

    @RequestMapping("/selectById")
    SuitCaseResult selectSuitResultById(Integer id){
        return suitCaseResultService.selectSuitResultById(id);
    }

    @RequestMapping("/update")
    int updateSuitResult(SuitCaseResult record){
        if (record != null){
            return suitCaseResultService.updateSuitResult(record);
        }
        return 0;
    }

    @RequestMapping("/selectByIds")
    List<SuitCaseResult> selectBySuitIdbuildIdSuitCaseId(Integer suitcaseid, Integer suitid, Integer buildid){
        return suitCaseResultService.selectBySuitIdbuildIdSuitCaseId(suitcaseid,suitid,buildid);
    }

    @RequestMapping(value="/getSuitResult",method = RequestMethod.POST)
    public BaseResp getSuitResult(Integer suitcaseid, Integer buildid){
        BaseResp baseResp = new BaseResp();

        SuitCaseResult caseResult = suitCaseResultService.selectSuitResult(suitcaseid,buildid);
        if(caseResult == null){
            baseResp.setCode(500);
            baseResp.setMsg("查询结果空!");
        }else{
            baseResp.setCode(200);
            baseResp.setData(caseResult);
        }

        return baseResp;
    }



}
