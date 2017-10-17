package com.autotest.service;

import com.autotest.model.SuitCase;

import java.util.List;
import java.util.Map;

public interface SuitCaseService {

    SuitCase selectById(Integer id);
    List<SuitCase> selectBySuitID(Integer suitID);

    Map<String, Object> suitCaseRun(SuitCase suitCase);


}
