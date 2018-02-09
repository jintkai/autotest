package com.autotest.service;

import com.autotest.model.SuitCase;
import com.autotest.model.SuitCaseResult;

import java.util.List;
import java.util.Map;

public interface SuitCaseService {

    SuitCase selectById(Integer id);
    List<SuitCase> selectBySuitID(Integer suitID);

    SuitCaseResult suitCaseRun(SuitCase suitCase, int buildid);

    int deleteSuitCaseById(Integer id);

    int insertSuitCase(SuitCase record);

    SuitCase selectSuitCaseById(Integer id);


    List<SuitCase> selectBySuitIdCaseId(Integer suitid,Integer caseid);

    int updateSuitCase(SuitCase record);


}
