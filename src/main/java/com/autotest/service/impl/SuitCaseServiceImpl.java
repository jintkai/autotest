package com.autotest.service.impl;

import com.autotest.dao.SuitCaseMapper;
import com.autotest.model.HttpInfo;
import com.autotest.model.SuitCase;
import com.autotest.model.SuitCaseResult;
import com.autotest.model.Variable;
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
    SuitCaseResultServiceImpl suitCaseResultService;
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
    public Map<String, Object> suitCaseRun(SuitCase suitCase,int buildid) {

        //拷贝用例变量到result表
        variableResultService.copyVariable(suitCase.getSuitid(),buildid);
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

        HttpInfo httpInfo = httpClientService.sentRequest(Integer.valueOf(suitCase.getRequesttype()),(String) urlMap.get("result"),suitCase.getRequestheader(),(String) bodyMap.get("result"));

        //发送请求后，根据caseid，实例化参数；
        if (httpInfo.getIsSuccess() ==1 ) {
            /**
             * 变量解析逻辑
             */
            List<Variable> caseVariables = variableService.selectBySuitCaseId(suitCase.getId());
            for (Variable variable : caseVariables) {
                variableService.resolveExpress(variable, buildid, httpInfo);
            }
            /**
             * 断言逻辑
             */

            if(Integer.valueOf(httpInfo.getResponseCode()) >=400)
            {
                httpInfo.setIsSuccess(0);
                httpInfo.setResponseLog("请求地址错误！");
            }

        }
        /**
         * 存储结果逻辑
         */

        SuitCaseResult suitCaseResult = new SuitCaseResult();
        suitCaseResult.setSuitcaseid(suitCase.getCaseid());
        suitCaseResult.setBuildid(buildid);
        List<SuitCaseResult> lists = suitCaseResultService.selectList(suitCaseResult);
        suitCaseResult.setSuitid(suitCase.getSuitid());
        suitCaseResult.setResponsecode(httpInfo.getResponseCode());
        suitCaseResult.setResponsebody(httpInfo.getResponseBody());
        suitCaseResult.setResponseheader(httpInfo.getResponseHeader());
        suitCaseResult.setAssertlog(httpInfo.getResponseLog());
        suitCaseResult.setResponsetime(Integer.valueOf(String.valueOf(httpInfo.getResponseTime())));
        suitCaseResult.setStatus(httpInfo.getIsSuccess());
        if (lists.size() == 1){
            suitCaseResult.setId(lists.get(0).getId());
            suitCaseResultService.updateSuitResult(suitCaseResult);
        }else {
            suitCaseResultService.insertSuitResult(suitCaseResult);
        }
        result.put("runLog",httpInfo);

        /**
         * 后置变量处理
         *
         */
        return result;
    }
}
