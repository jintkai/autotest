package com.autotest.controller;

import com.autotest.model.SuitCaseReport;
import com.autotest.service.SuitCaseReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/suit/buildreports")
public class SuitCaseReportAction {

    public final static Logger LOG = LoggerFactory.getLogger(SuitCaseReportAction.class);
    @Autowired
    SuitCaseReportService suitCaseReportService;

    @RequestMapping
    public Map<String,Object> getBuildReports(){
        LOG.debug("查询套件下构建历史报表:");
        Map<String,Object> resultMap = new HashMap<String,Object>();
        return resultMap;
    }

    @RequestMapping(value = "/{suitid}")
    public Map<String,Object> getSuitCaseBuildReport(@PathVariable  Integer suitid) {
        LOG.debug("查询套件下构建历史报表:" + suitid);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<SuitCaseReport> lists = suitCaseReportService.selectBySuitIdbuildId(suitid,null);
        resultMap.put("results", lists);
        return resultMap;
    }

    @RequestMapping("/delete")
    int deleteSuitCaseReportById(Integer id){
        return suitCaseReportService.deleteSuitCaseReportById(id);
    }

    @RequestMapping("/insert")
    int insertSuitCaseReport(SuitCaseReport record){
        if (record != null){
            return suitCaseReportService.insertSuitCaseReport(record);
        }
        return 0;
    }

    @RequestMapping("/selectById")
    SuitCaseReport selectSuitCaseReportById(Integer id){
        return suitCaseReportService.selectSuitCaseReportById(id);
    }

    @RequestMapping("/update")
    int updateBSuitCaseReport(SuitCaseReport record){
        if(record!= null){
            return suitCaseReportService.updateBSuitCaseReport(record);
        }
        return 0;
    }

    @RequestMapping("/selectByIds")
    List<SuitCaseReport> selectBySuitIdbuildId(Integer suitid, Integer buildid){
        if (suitid == null && buildid == null){
            return null;
        }
        else {
            return suitCaseReportService.selectBySuitIdbuildId(suitid,buildid);
        }
    }

    @RequestMapping("/selectAll")
    List<SuitCaseReport> selectSuitCaseReportAll(){
        return suitCaseReportService.selectSuitCaseReportAll();
    }
}
