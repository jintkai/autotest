package com.autotest.service;

import com.autotest.model.SuitCaseResultHis;

import java.util.List;

/**
 * Created by ying.zhang on 17/10/15.
 */
public interface SuitCaseResultHisService {

    int deleteSuitHisById(Integer id);

    int insertSuitHis(SuitCaseResultHis record);

    SuitCaseResultHis selectSuitHisById(Integer id);

    int updateSuitHis(SuitCaseResultHis record);

    List<SuitCaseResultHis> selectBySuitIdbuildIdCaseId(Integer suitcaseid, Integer suitid, Integer buildid);

}
