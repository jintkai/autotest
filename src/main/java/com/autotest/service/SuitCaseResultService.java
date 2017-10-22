package com.autotest.service;

import com.autotest.model.SuitCaseResult;

import java.util.List;

/**
 * Created by ying.zhang on 17/10/15.
 */
public interface SuitCaseResultService {

    int deleteSuitResultById(Integer id);

    int insertSuitResult(SuitCaseResult record);

    SuitCaseResult selectSuitResultById(Integer id);

    int updateSuitResult(SuitCaseResult record);

    List<SuitCaseResult> selectBySuitIdbuildIdSuitCaseId(Integer suitcaseid, Integer suitid, Integer buildid);

}
