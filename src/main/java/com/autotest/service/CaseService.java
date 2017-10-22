package com.autotest.service;

import com.autotest.model.Case;

import java.util.List;

/**
 * Created by ying.zhang on 17/10/15.
 */
public interface CaseService {

    int deleteCaseById(Integer caseid);

    int insertCase(Case record);

    Case selectCaseById(Integer caseid);

    int updateCase(Case record);

    List<Case> selectCasesByCaseName(String casename);
}
