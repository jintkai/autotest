package com.autotest.service.impl;

import com.autotest.dao.SuitCaseMapper;
import com.autotest.model.SuitCase;
import com.autotest.service.SuitCaseService;
import com.autotest.service.impl.common.HttpClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SuitCaseServiceImpl implements SuitCaseService {

    @Autowired
    SuitCaseMapper suitCaseMapper;
    @Autowired
    HttpClientServiceImpl httpClientService;
    @Override
    public SuitCase selectById(Integer id) {
        return suitCaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SuitCase> selectBySuitID(Integer suitID) {
        return suitCaseMapper.selectBySuitID(suitID);
    }

    @Override
    public Map<String, String> suitCaseRun(SuitCase suitCase) {
        return httpClientService.sentRequest(Integer.valueOf(suitCase.getRequesttype()),suitCase.getRequesturl(),suitCase.getRequestheader(),suitCase.getRequestbody());
    }
}
