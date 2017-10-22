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
    public int deleteSuitCaseById(Integer id){
        return suitCaseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSuitCase(SuitCase record){
        return suitCaseMapper.insertSelective(record);
    }

    @Override
    public SuitCase selectSuitCaseById(Integer id){
        return suitCaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SuitCase> selectBySuitIdCaseId(Integer suitid,Integer caseid){
        return suitCaseMapper.selectBySuitIdCaseId(suitid,caseid);
    }

    @Override
    public int updateSuitCase(SuitCase record){
        return suitCaseMapper.updateByPrimaryKeySelective(record);
    }


    @Override
    public Map<String, String> suitCaseRun(SuitCase suitCase) {
        return httpClientService.sentRequest(Integer.valueOf(suitCase.getRequesttype()),suitCase.getRequesturl(),suitCase.getRequestheader(),suitCase.getRequestbody());
    }
}
