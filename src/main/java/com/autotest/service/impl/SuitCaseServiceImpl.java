package com.autotest.service.impl;

import com.autotest.dao.SuitCaseMapper;
import com.autotest.model.*;
import com.autotest.service.SuitCaseService;
import com.autotest.service.impl.common.HttpClientServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        BaseResp urlRequest = new BaseResp();
        BaseResp headerRequest = new BaseResp();
        BaseResp bodyRequest = new BaseResp();

        //拷贝用例自定义变量到每次只需的自定义result表
        variableResultService.copyVariable(suitCase.getSuitid(),buildid);

        Map<String,Object> result = new HashMap<String,Object>();
        Map<String,Object> urlMap = resolverServer.resolver(suitCase.getSuitid(),buildid,suitCase.getRequesturl() == null?"":suitCase.getRequesturl());
        Map<String,Object> headerMap = resolverServer.resolver(suitCase.getSuitid(),buildid,suitCase.getRequestheader() == null?"":suitCase.getRequestheader());
        Map<String,Object> bodyMap = resolverServer.resolver(suitCase.getSuitid(),buildid,suitCase.getRequestbody() == null?"":suitCase.getRequestbody());
        String message = "";
        int success = 1;
        List<AssertModel> assertModels = new ArrayList<>();
        //判断请求头、请求体、url解析是否正确。
        AssertModel urlAssert = new AssertModel();
        AssertModel headerAssert = new AssertModel();
        AssertModel bodyAssert = new AssertModel();
        urlAssert.setAssertType("url");
        headerAssert.setAssertType("header");
        bodyAssert.setAssertType("body");

        if ((Integer) urlMap.get("success") == 0 ) {
            urlAssert.setSuccess(0);
            urlAssert.setMessage((String ) urlMap.get("message"));
            success = 0;
            urlRequest.setMsg( (String ) urlMap.get("message"));
            urlRequest.setCode(500);
        }else{
            urlRequest.setMsg( (String ) urlMap.get("result"));
            urlRequest.setCode(200);
            urlAssert.setSuccess(1);
        }

        if ((Integer) headerMap.get("success")  == 0){
            headerAssert.setSuccess(0);
            headerAssert.setMessage((String ) headerMap.get("message"));
            success = 0;
            headerRequest.setMsg((String )headerMap.get("message"));
            headerRequest.setCode(500);
        }else{
            headerRequest.setMsg( (String ) headerMap.get("result"));
            headerRequest.setCode(200);
            headerAssert.setSuccess(1);
        }

        if ((Integer) bodyMap.get("success")  == 0){
            bodyAssert.setSuccess(0);
            bodyAssert.setMessage((String ) bodyMap.get("message"));
            success = 0;
            bodyRequest.setMsg((String )bodyMap.get("message"));
            bodyRequest.setCode(500);
        }else{
            bodyRequest.setMsg( (String ) bodyMap.get("result"));
            bodyRequest.setCode(200);
            bodyAssert.setSuccess(1);
        }

        if(success == 0){
            message ="Case解析错误!";
        }

        assertModels.add(urlAssert);
        assertModels.add(headerAssert);
        assertModels.add(bodyAssert);

        SuitCaseResult suitCaseResult = new SuitCaseResult();
        //参数解析失败，直接退出程序。保存执行结果
        if (success == 0){
            suitCaseResult.setResponsecode("");
            ObjectMapper mapper = new ObjectMapper();
            String jsonStr = "";
            try {
                jsonStr = mapper.writeValueAsString(assertModels);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            suitCaseResult.setAssertlog(jsonStr);
            suitCaseResult.setSuitcaseid(suitCase.getCaseid());
            suitCaseResult.setBuildid(buildid);
            List<SuitCaseResult> lists = suitCaseResultService.selectList(suitCaseResult);
            suitCaseResult.setSuitid(suitCase.getSuitid());
            suitCaseResult.setStatus(0);
            if (lists.size() == 1){
                suitCaseResult.setId(lists.get(0).getId());
                suitCaseResultService.updateSuitResult(suitCaseResult);
            }else {
                suitCaseResultService.insertSuitResult(suitCaseResult);
            }
            //判断执行用例结果；若为0，则执行失败；
            result.put("success",success);
            result.put("message",message);
            result.put("urlFormat",urlRequest);
            result.put("headerFormat",headerRequest);
            result.put("bodyFormat",bodyRequest);
            return result;
        }
        //解析成功，继续执行
        HttpInfo httpInfo = new HttpInfo();

        httpInfo = httpClientService.sentRequest(Integer.valueOf(suitCase.getRequesttype()), (String) urlMap.get("result"), suitCase.getRequestheader(), (String) bodyMap.get("result"));
        //发送请求后，根据caseid，实例化参数；

        if (httpInfo.getIsSuccess() ==1 ) {
            /**
             * 变量解析逻辑,变量不与case绑定，与suit绑定
             */
//            List<Variable> caseVariables = variableService.selectBySuitCaseId(suitCase.getId());
//            for (Variable variable : caseVariables) {
//                variableService.resolveExpress(variable, buildid, httpInfo);
//            }
            /**
             * 断言逻辑，断言解析，断言判断
             */
        }else{
            //http执行失败
            success = 0 ;
            message = httpInfo.getResponseLog();
            AssertModel httpAssert = new AssertModel();
            httpAssert.setAssertType("http");
            httpAssert.setSuccess(0);
            httpAssert.setMessage(message);
            assertModels.add(httpAssert);
        }

        result.put("success",success);
        result.put("message",message);
        result.put("urlFormat",urlRequest);
        result.put("headerFormat",headerRequest);
        result.put("bodyFormat",bodyRequest);
        /**
         * 存储结果逻辑
         */
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(assertModels);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        suitCaseResult.setSuitcaseid(suitCase.getCaseid());
        suitCaseResult.setBuildid(buildid);
        List<SuitCaseResult> lists = suitCaseResultService.selectList(suitCaseResult);
        suitCaseResult.setSuitid(suitCase.getSuitid());
        suitCaseResult.setResponsecode(httpInfo.getResponseCode());
        suitCaseResult.setResponsebody(httpInfo.getResponseBody());
        suitCaseResult.setResponseheader(httpInfo.getResponseHeader());
        suitCaseResult.setAssertlog(jsonStr);
        suitCaseResult.setResponsetime(Integer.valueOf(String.valueOf(httpInfo.getResponseTime())));
        suitCaseResult.setStatus(httpInfo.getIsSuccess());
        if (lists.size() == 1){
            suitCaseResult.setId(lists.get(0).getId());
            suitCaseResultService.updateSuitResult(suitCaseResult);
        }else {
            suitCaseResultService.insertSuitResult(suitCaseResult);
        }
        result.put("runLog",httpInfo);

        return result;
    }

}
