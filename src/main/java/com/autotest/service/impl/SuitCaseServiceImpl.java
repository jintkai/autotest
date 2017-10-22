package com.autotest.service.impl;

import com.autotest.dao.SuitCaseMapper;
import com.autotest.model.SuitCase;
import com.autotest.service.SuitCaseService;
import com.autotest.service.impl.common.HttpClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SuitCaseServiceImpl implements SuitCaseService {

    @Autowired
    SuitCaseMapper suitCaseMapper;
    @Autowired
    HttpClientServiceImpl httpClientService;
    @Autowired
    VariableServiceImpl variableService;
    @Autowired
    VariableResultServiceImpl variableResultService;
    @Autowired
    VariableResolverServerImpl resolverServer;

    @Override
    public SuitCase selectById(Integer id) {
        return suitCaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SuitCase> selectBySuitID(Integer suitID) {
        return suitCaseMapper.selectBySuitID(suitID);
    }

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
    public Map<String, Object> suitCaseRun(SuitCase suitCase) {
        Map<String,Object> result = new HashMap<String,Object>();
        Map<String,Object> urlMap = resolverServer.resolver(suitCase.getSuitid(),10,suitCase.getRequesturl() == null?"":suitCase.getRequesturl());
        Map<String,Object> headerMap = resolverServer.resolver(suitCase.getSuitid(),10,suitCase.getRequestheader() == null?"":suitCase.getRequestheader());
        Map<String,Object> bodyMap = resolverServer.resolver(suitCase.getSuitid(),10,suitCase.getRequestbody() == null?"":suitCase.getRequestbody());
        String message = "";
        int success = 1;
        if ((Integer)urlMap.get("success") == 0 ) {
            message = "URL实例化错误，请检查url是否正确";
            success = 0;
        }
        if ((Integer) headerMap.get("success")  == 0){
            message = message + "Header实例化错误，请检查header是否正确";
            success = 0;
        }
        if ((Integer) bodyMap.get("success")  == 0){
            message = message + "Body实例化错误，请检查Body是否正确";
            success = 0;
        }
        result.put("success",success);
        result.put("message",message);
        result.put("urlMap",urlMap);
        result.put("headerMap",headerMap);
        result.put("bodyMap",bodyMap);

        Map<String,Object> response = httpClientService.sentRequest(Integer.valueOf(suitCase.getRequesttype()),(String) urlMap.get("result"),suitCase.getRequestheader(),(String) bodyMap.get("result"));
        result.put("runLog",response);


        return result;
    }
}
