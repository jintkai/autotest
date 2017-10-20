package com.autotest.service.impl;

import com.autotest.dao.SuitCaseResultMapper;
import com.autotest.model.SuitCaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuitCaseResultServiceImpl {

    @Autowired
    SuitCaseResultMapper suitCaseResultMapper;

    public List<SuitCaseResult> selectList(SuitCaseResult suitCaseResult){
        return suitCaseResultMapper.selectList(suitCaseResult);
    }
}
