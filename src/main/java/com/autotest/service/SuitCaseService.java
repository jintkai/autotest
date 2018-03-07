package com.autotest.service;

import com.autotest.model.SuitCase;
import com.autotest.model.SuitCaseResult;

import java.util.List;
import java.util.Map;

public interface SuitCaseService {

    SuitCase selectById(Integer id);
    List<SuitCase> selectBySuitID(Integer suitID);
    List<SuitCase> selectMainCaseBySuitID(Integer suitID);


    SuitCaseResult suitCaseRun(SuitCase suitCase, int buildid,boolean skip);

    int deleteSuitCaseById(Integer id);

    int insertSuitCase(SuitCase record);

    SuitCase selectSuitCaseById(Integer id);



    List<SuitCase> selectBySuitIdCaseId(Integer suitid,Integer caseid);

    List<SuitCase> selectSubCase(Integer caseid);

    int updateSuitCase(SuitCase record);

    int countCaseByTime(Long time);


}
