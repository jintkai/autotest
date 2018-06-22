package com.autotest.controller;

import com.autotest.model.Case;
import com.autotest.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ying.zhang on 17/10/15.
 */
@RestController
@RequestMapping("/case")
public class CaseAction {

    @Autowired
    CaseService caseService;

    @RequestMapping("/delete")
    int deleteCaseById(Integer caseid) {
        return caseService.deleteCaseById(caseid);
    }

    @RequestMapping("/insert")
    int insertCase(Case record) {
        if (record != null) {
            if(record.getAssertexp()==null || record.getAssertexp().equals("")){
                record.setAssertexp("[]");
            }
            return caseService.insertCase(record);
        }
        return 0;
    }

    @RequestMapping("/selectById")
    Case selectCaseById(Integer caseid) {
        return caseService.selectCaseById(caseid);
    }

    @RequestMapping("/update")
    int updateCaseBy(Case record) {
        if (record != null) {
            if(record.getAssertexp()==null || record.getAssertexp().equals("")){
                record.setAssertexp("[]");
            }
            return caseService.updateCase(record);
        }
        return 0;
    }

    @RequestMapping("/selectByName")
    List<Case> selectCasesByCaseName(String casename) {
        return caseService.selectCasesByCaseName(casename);
    }
}
